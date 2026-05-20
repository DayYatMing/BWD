package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Services;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.BackhaulMapper;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class BackhaulRepository {
    private final Logger log = LoggerFactory.getLogger(BackhaulRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public BackhaulMapper backhaulMapper = new BackhaulMapper();

    @Autowired
    ServiceRepository serviceRepository;

    private static final String FIND_ALL = "select out('HasBackhaulAend').name as aend , out('HasBackhaulAend').latitude as aendlat , out('HasBackhaulAend').longitude as aendlong ,  out('HasBackhaulBend').name as bend,  out('HasBackhaulBend').latitude as bendlat , out('HasBackhaulBend').longitude as bendlong ,   out('HasBackhaulService').name as service ,   out('HasBackhaulService').in('HasService').name as customer, out('HasBackhaulPort').name as ports, * from backhaul";
    private static final String FIND_BYPROVIDERID = "SELECT out('HasBackhaulAend').name as aend , out('HasBackhaulAend').latitude as aendlat , out('HasBackhaulAend').longitude as aendlong ,  out('HasBackhaulBend').name as bend,  out('HasBackhaulBend').latitude as bendlat , out('HasBackhaulBend').longitude as bendlong ,   out('HasBackhaulService').name as service ,   out('HasBackhaulService').in('HasService').name as customer, out('HasBackhaulPort').name as ports, * FROM Backhaul WHERE name =:name";
    private static final String FIND_ALL_BY_SERVICEID = "select out('HasBackhaulAend').name as aend , out('HasBackhaulAend').latitude as aendlat , out('HasBackhaulAend').longitude as aendlong ,  out('HasBackhaulBend').name as bend,  out('HasBackhaulBend').latitude as bendlat , out('HasBackhaulBend').longitude as bendlong ,   out('HasBackhaulService').name as service ,   out('HasBackhaulService').in('HasService').name as customer, out('HasBackhaulPort').name as ports, * from backhaul where out('HasBackhaulService').name == [:serviceid]";

    private static final String FIND_ALL_BY_SERVICEID_UPDATED = "select out('HasBackhaulAend').name as aend , out('HasBackhaulAend').latitude as aendlat , out('HasBackhaulAend').longitude as aendlong ,  out('HasBackhaulBend').name as bend,  out('HasBackhaulBend').latitude as bendlat , out('HasBackhaulBend').longitude as bendlong ,   out('HasBackhaulService').name as service ,   out('HasBackhaulService').in('HasService').name as customer, out('HasBackhaulPort').name as ports, * from backhaul where :serviceid in out('HasBackhaulService').name";


    private static final String INSERT_BATCH = "begin;\n"
        + "let a = INSERT INTO Backhaul ( name , labelname , createdby ,   capacity , comment , protectedcircuit , providerName ,  servicedetails , status , segment )   VALUES ( :name , :labelname , :createdby , :capacity , :comment , :protectedcircuit , :providerName ,  :servicedetails , :status  , :segment);\n"
        + "let b = SELECT FROM SITE WHERE name=:sitea;\n"
        + "let b1 = SELECT FROM SITE WHERE name=:siteb;\n"
        + "let c =  CREATE EDGE HasBackhaulAend FROM $a to $b set name = :edgenamea;\n"
        + "let c2 = CREATE EDGE HasBackhaulBend FROM $a to $b1 set name = :edgenameb;\n"
        + "if(:service != NULL){\n" + "let d = SELECT FROM SERVICE WHERE name=:service;\n"
        + "let d1 = CREATE EDGE HasBackhaulService FROM $a to $d set name = :edgenameservice;\n" + "}\n"
        + "COMMIT RETRY 5;\n" + "return $a;";

    private static final String INSERT_SEGMENTSERVICE = "begin;\n"
        + "let a =  SELECT FROM BACKHAUL WHERE name = :name;\n"
        + "let b = SELECT FROM SERVICE WHERE name= :service;\n"
        + "let d1 = CREATE EDGE HasBackhaulService FROM $a to $b set name = :edgenameservice;\n"
        + "COMMIT RETRY 2;\n" + "return $a;";

    private static final String DELETE_SEGMENTSERVICE = "begin;\n"
        + "let a = SELECT FROM BACKHAUL WHERE name = :name;\n"
        + "let b = SELECT FROM SERVICE WHERE name = :servicename;\n"
        + "let c = DELETE EDGE  HasBackhaulService FROM $a to $b;\n" + "COMMIT RETRY 2;\n" + "return $a;";

    private static final String DELETE_BATCH = "begin;\n" + "let a = SELECT FROM BACKHAUL WHERE name = :name;\n"
        + "if(:servicename != NULL){\n" + "let b = SELECT FROM SERVICE WHERE name = :servicename;\n"
        + "let c = DELETE EDGE  HasBackhaulService FROM $a to $b;\n" + "}\n"
        + "let d = SELECT FROM SITE WHERE name = :sitea;\n" + "let e = SELECT FROM SITE WHERE name = :siteb;\n"
        + "let f = DELETE EDGE HasBackhaulAend FROM $a to $d;\n"
        + "let g = DELETE EDGE HasBEndSegment FROM $a to $e;\n"
        + "let h = DELETE VERTEX BACKHAUL WHERE name = :name;\n" + "COMMIT RETRY 5;";

    public Flux<Backhaul> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectBackhaul(FIND_ALL, args);
    }

    public Mono<Backhaul> findById(String id) {
        return findByProviderID(FIND_BYPROVIDERID, id).next();
    }

    public Mono<Void> update(Backhaul backhaul) {

        return findByProviderID(FIND_BYPROVIDERID, backhaul.getName())
            .next()
            .flatMap(existingBackhaul -> {

                Map<Object, Object> args = new HashMap<>();
                args.put("name", existingBackhaul.getName());
                args.put("servicename", existingBackhaul.getService());
                args.put("sitea", existingBackhaul.getAend());
                args.put("siteb", existingBackhaul.getBend());

                if (existingBackhaul.getSegment() != null) {
                    return updateBackhaul(DELETE_BATCH, args)
                        .then(save(backhaul));
                }

                return serviceRepository.findAllForSegment(existingBackhaul.getSegment())
                    .flatMap(service -> {
                        Map<Object, Object> serviceArgs = new HashMap<>(args);
                        serviceArgs.put("servicename", service.getServicename());
                        serviceArgs.put("name", existingBackhaul.getName());
                        return updateBackhaul(DELETE_SEGMENTSERVICE, serviceArgs)
                            .onErrorResume(e -> {
                                log.error("Error deleting old backhaul {} for service {}: {}",
                                    existingBackhaul.getName(), service.getServicename(), e.toString());
                                return Mono.empty();
                            });
                    })
                    .then(Mono.fromRunnable(() -> args.put("servicename", null)))
                    .then(updateBackhaul(DELETE_BATCH, args))
                    .then(save(backhaul));
            });
    }

    Flux<Backhaul> findByProviderID(String query, String backhaulId){

        log.debug("Select Query: {} \nArgs: {}", query, backhaulId);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query, backhaulId)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> backhaulMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> save(Backhaul backhaul) {
        Map<Object, Object> args = backhaul.backhaulMap();
        args.put("sitea", backhaul.getAend());
        args.put("siteb", backhaul.getBend());
        args.put("edgenamea", backhaul.getName() + "-->" + backhaul.getAend());
        args.put("edgenameb", backhaul.getName() + "-->" + backhaul.getBend());

        if (backhaul.getSegment() != null) {
            Flux<Services> services = serviceRepository.findAllForSegment(backhaul.getSegment());
            args.put("status", "ALLOCATED");

            return updateBackhaul(INSERT_BATCH, args)
                .thenMany(
                    services
                )
                .flatMap(service -> {
                    Map<Object, Object> serviceArgs = new HashMap<>(args);
                    serviceArgs.put("service", service.getLabelname());
                    serviceArgs.put("name", backhaul.getName());
                    serviceArgs.put("edgenameservice", backhaul.getName() + "-->" + service.getLabelname());

                    return updateBackhaul(INSERT_SEGMENTSERVICE, serviceArgs)
                        .onErrorResume(e -> {
                            log.error("Error inserting backhaul {} for service {}: {}",
                                backhaul.getName(), service.getServicename(), e.toString());
                            return Mono.empty();
                        });
                })
                .then();
        }

        if (backhaul.getService() != null) {
            args.put("edgenameservice", backhaul.getName() + "-->" + backhaul.getService());
        }

        return updateBackhaul(INSERT_BATCH, args);
    }

    public Flux<Backhaul> selectBackhaul(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> backhaulMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> updateBackhaul(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = backhaulMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }
}
