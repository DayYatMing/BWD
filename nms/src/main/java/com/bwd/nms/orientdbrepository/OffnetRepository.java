package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Offnet;
import com.bwd.nms.orientdbdomain.Services;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.BackhaulMapper;
import com.bwd.nms.service.mapper.OffnetMapper;
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
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class OffnetRepository {
    private final Logger log = LoggerFactory.getLogger(OffnetRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public OffnetMapper offnetMapper = new OffnetMapper();

    private static final String FIND_ALL = "select out('HasOffnetAend').name as aend , out('HasOffnetAend').latitude as aendlat , out('HasOffnetAend').longitude as aendlong ,  out('HasOffnetBend').name as bend,  out('HasOffnetBend').latitude as bendlat , out('HasOffnetBend').longitude as bendlong ,   out('HasOffnetService').name as service ,   out('HasOffnetService').in('HasService').name as customer, out('HasOffnetSegment').name as segments, * from offnet";
    private static final String FIND_BYPROVIDERID = "select out('HasOffnetAend').name as aend , out('HasOffnetAend').latitude as aendlat , out('HasOffnetAend').longitude as aendlong ,  out('HasOffnetBend').name as bend,  out('HasOffnetBend').latitude as bendlat , out('HasOffnetBend').longitude as bendlong ,   out('HasOffnetService').name as service ,   out('HasOffnetService').in('HasService').name as customer, out('HasOffnetSegment').name as segments, * from offnet WHERE name =:name";
    private static final String FIND_ALL_BY_SERVICEID = "select  out('HasOffnetAEnd').name as aend , out('HasOffnetAEnd').latitude as aendlat ,  out('HasOffnetAEnd').longitude as aendlong ,  out('HasOffnetBEnd').name as bend,  out('HasOffnetBEnd').latitude as bendlat , out('HasOffnetBEnd').longitude as bendlong ,   out('HasOffnetService').name as service ,   out('HasOffnetService').in('HasService').name as customer , *  from offnet where out('HasOffnetService').name == [':serviceid']";


    private static final String INSERT_BATCH =
        "begin;\n"
            + "let a = INSERT INTO Offnet ( vendorname , name , labelname , createdby ,   capacity , comment , protectedcircuit ,  aenddetails ,  benddetails, status , frequency )   VALUES ( :vendorname ,  :name , :labelname , :createdby , :capacity , :comment , :protectedcircuit , :aenddetails , :benddetails , :status , :frequency  );\n"
            + "let b = SELECT FROM SITE WHERE name=:aend;\n"
            + "let b1 = SELECT FROM SITE WHERE name=:bend;\n"
            + "let b2 = SELECT FROM SEGMENT WHERE name=:segment;\n"
            + "let c =  CREATE EDGE HasOffnetAend FROM $a to $b set name = :edgenamea;\n"
            + "let c2 = CREATE EDGE HasOffnetBend FROM $a to $b1 set name = :edgenameb;\n"
            + "let c3 = CREATE EDGE HasOffnetSegment FROM $a to $b2 set name = :edgenamesegment;\n"
            + "if(:service != NULL){\n"
            + "let d = SELECT FROM SERVICE WHERE name=:service;\n"
            + "let d1 = CREATE EDGE HasOffnetService FROM $a to $d set name = :edgenameservice;\n"
            + "}\n"
            + "COMMIT RETRY 5;\n"
            + "return $a;";

    private static final String INSERT_SEGMENTSERVICE = "begin;\n"
        + "let a =  SELECT FROM BACKHAUL WHERE name = :name;\n"
        + "let b = SELECT FROM SERVICE WHERE name=:service;\n"
        + "let d1 = CREATE EDGE HasBackhaulService FROM $a to $b set name = :edgenameservice;\n"
        + "COMMIT RETRY 2;\n"
        + "return $a;";

    private static final String DELETE_SEGMENTSERVICE = "begin;\n"
        + "let a = SELECT FROM BACKHAUL WHERE name = :name;\n"
        + "let b = SELECT FROM SERVICE WHERE name = :servicename;\n"
        + "let c = DELETE EDGE  HasBackhaulService FROM $a to $b;\n"
        + "COMMIT RETRY 2;\n"
        + "return $a;";


    private static final String DELETE_BATCH = "begin;\n" + "let a = SELECT FROM Offnet WHERE name = :name;\n"
        + "if(:servicename != NULL){\n" + "let b = SELECT FROM SERVICE WHERE name = :servicename;\n"
        + "let c = DELETE EDGE  HasOffnetService FROM $a to $b;\n" + "}\n"
        + "let d = SELECT FROM SITE WHERE name = :sitea;\n"
        + "let e = SELECT FROM SITE WHERE name = :siteb;\n"
        + "let e1 = SELECT FROM SEGMENT WHERE name = :segment;\n"
        + "let f = DELETE EDGE HasOffnetAend FROM $a to $d;\n"
        + "let g = DELETE EDGE HasOffnetBend FROM $a to $e;\n"
        + "let g1 = DELETE EDGE HasOffnetSegment FROM $a to $e1;\n"
        + "let h = DELETE VERTEX Offnet WHERE name = :name;\n" + "COMMIT RETRY 5;";

    public Flux<Offnet> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectOffnet(FIND_ALL, args);
    }

    public Mono<Offnet> findById(String id) {
        return findByProviderID(FIND_BYPROVIDERID, id).next();
    }

    public Flux<Offnet> selectOffnet(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> offnetMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> update(Offnet offnet) {

        return findByProviderID(FIND_BYPROVIDERID, offnet.getName())
            .next()
            .flatMap(existingOffnet -> {
                Map<Object, Object> args = new HashMap<>();
                args.put("name", existingOffnet.getName());
                args.put("servicename", existingOffnet.getService());
                args.put("sitea", existingOffnet.getAend());
                args.put("siteb", existingOffnet.getBend());
                args.put("segment", existingOffnet.getSegment());

                return updateOffnet(DELETE_BATCH, args)
                    .then(save(offnet));
            });
    }

    Flux<Offnet> findByProviderID(String query, String offnetId){

        log.debug("Select Query: {} \nArgs: {}", query, offnetId);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query, offnetId)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> offnetMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> save(Offnet offnet) {
        Map<Object, Object> args = offnet.offnetMap();
        args.put("edgenamea", offnet.getName() + "-->" + offnet.getAend());
        args.put("edgenameb", offnet.getName() + "-->" + offnet.getBend());
        args.put("edgenamesegment", offnet.getName() + "-->" + offnet.getSegment());

        if (offnet.getService() != null) {
            args.put("edgenameservice", offnet.getName() + "-->" + offnet.getService());
        }

        return updateOffnet(INSERT_BATCH, args);
    }

    public Mono<Void> updateOffnet(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = offnetMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

}
