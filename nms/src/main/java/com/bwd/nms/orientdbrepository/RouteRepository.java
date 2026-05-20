package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Backhaul;
import com.bwd.nms.orientdbdomain.Offnet;
import com.bwd.nms.orientdbdomain.Route;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.BackhaulMapper;
import com.bwd.nms.service.mapper.RouteMapper;
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
public class RouteRepository {
    private final Logger log = LoggerFactory.getLogger(RouteRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public RouteMapper routeMapper = new RouteMapper();

    private static final String FIND_ALL = "SELECT  OUT('HasSegment').out('HasSite').latitude as lat, OUT('HasSegment').out('HasSite').longitude as long , out('HasSegment').name as segmentnames , * FROM ROUTE";
    private static final String FIND_BYNAME = "SELECT out('HasSegment').name as segmentnames , * FROM ROUTE WHERE name =:name";
    private static final String FIND_BYID = "SELECT  out('HasSegment').name as segmentnames , * FROM ROUTE WHERE @rid =:id";
    private static final String INSERT = "INSERT INTO ROUTE ( name , labelname ,  comment , createdby , datecreated, segments , aend , zend) "
        + "  VALUES ( :name , :labelname , :comment , :createdby, :datecreated, :segments , :aend , :zend)";

    private static final String INSERT_SEGMENTEDGES = "begin;\n"
        + "let a = SELECT FROM SEGMENT WHERE name=:segmentname;\n"
        + "let b = SELECT FROM ROUTE WHERE @rid=:routeid;\n"
        + "let c = CREATE EDGE HasSegment FROM $b to $a set name = :routesegmentedge;\n"
        + "COMMIT RETRY 5;\n"
        + "return $b;";

    private static final String DELETE_SEGMENTEDGES = "begin;\n"
        + "let a = SELECT FROM SEGMENT WHERE name=:segmentname;\n"
        + "let b = SELECT FROM ROUTE WHERE @rid=:routeid;\n"
        + "let c = DELETE EDGE HasSegment FROM $b to $a;\n"
        + "COMMIT RETRY 5;\n"
        + "return $b;";


    private static final String UPDATE = "UPDATE ROUTE SET name = :name , labelname = :labelname , "
        + "comment = :comment , updatedby = :updatedby , dateupdated = :dateupdated  , segments = :segments , aend = :aend , zend = :zend"
        + " where @rid = :id";


    public Flux<Route> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectRoute(FIND_ALL, args);
    }

    public Mono<Void> update(Route route) {
        Map<Object, Object> args = route.routeMap();
        args.put("id", route.getId());

        return updateRoute(UPDATE, args)
            .then(findByName(FIND_BYNAME, route.getRoutename()))
            .flatMap(oldRoute -> {
                Mono<Void> deleteOldSegments = (oldRoute.getSegments() != null)
                    ? Flux.fromIterable(oldRoute.getSegments())
                    .flatMap(segment -> {
                        args.put("segmentname", segment);
                        args.put("routeid", oldRoute.getId());
                        return updateRoute(DELETE_SEGMENTEDGES, args);
                    })
                    .then()
                    : Mono.empty();

                Mono<Void> insertNewSegments = (route.getSegments() != null)
                    ? Flux.fromIterable(route.getSegments())
                    .flatMap(segment -> {
                        args.put("segmentname", segment);
                        args.put("routeid", route.getId());
                        args.put("routesegmentedge", route.getRoutename() + "-->" + segment);
                        return updateRoute(INSERT_SEGMENTEDGES, args);
                    })
                    .then()
                    : Mono.empty();

                return deleteOldSegments.then(insertNewSegments)
                    .doOnSuccess(v -> route.setId(route.getId()));
            });
    }

    public Mono<Route> findByName(String query, String name) {
        Map<Object, Object> args = new HashMap<>();
        args.put("name", name);
        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query, args)
                        .stream()
                        .map(routeMapper::fromResult)
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(Flux::fromIterable)
            .next()
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> save(Route route) {
        Map<Object, Object> args = route.routeMap();

        return insertRoute(INSERT, args)
            .flatMap(updatedRoute ->
                Flux.fromIterable(route.getSegments())
                    .flatMap(segment -> {
                        args.put("segmentname", segment);
                        args.put("routeid", updatedRoute.getId());
                        args.put("routesegmentedge", updatedRoute.getRoutename() + "-->" + segment);

                        return updateRoute(INSERT_SEGMENTEDGES, args)
                            .then();
                    })
                    .then()
            );
    }

    public Mono<Route> insertRoute(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = routeMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                });
            });
    }

    public Mono<Void> updateRoute(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = routeMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Flux<Route> selectRoute(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> routeMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

}
