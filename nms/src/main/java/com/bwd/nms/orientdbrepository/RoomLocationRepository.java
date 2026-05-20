package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbdomain.Route;
import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.RoomLocationMapper;
import com.bwd.nms.web.rest.errors.BadRequestAlertException;
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
public class RoomLocationRepository {
    private final Logger log = LoggerFactory.getLogger(RoomLocationRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public RoomLocationMapper roomLocationMapper = new RoomLocationMapper();

    @Autowired
    SiteRepository siteRepository;

    private static final String FIND_ALL = "SELECT IN('HasRoomLocation').name as site , *  FROM ROOMLOCATION";
    private static final String FIND_ALL_SITE = "SELECT IN('HasRoomLocation').name as site , *  FROM ROOMLOCATION where name like '%:site%'";
    private static final String FIND_BYNAME = "SELECT IN('HasRoomLocation').name as site , *  FROM ROOMLOCATION WHERE name =:name";
    private static final String FIND_BYID = "SELECT IN('HasRoomLocation').name as site , * FROM ROOMLOCATION WHERE @rid =:id";
    private static final String INSERT = "INSERT INTO ROOMLOCATION ( name , labelname , createdby ,  comment )   VALUES ( :name , :labelname , :createdbyuser , :comment)";
    private static final String INSERT_BATCH = "begin;\n"
        + "let a = INSERT INTO ROOMLOCATION ( name , labelname , createdby ,  comment )   VALUES ( :name , :labelname , :createdbyuser , :comment);\n"
        + "let b = SELECT FROM SITE WHERE @rid=:siteid;\n"
        + "let c = CREATE EDGE HASROOMLOCATION FROM $b to $a set name = :edgename;\n"
        + "COMMIT RETRY 5;\n"
        + "return $a;";


    private static final String UPDATE = "UPDATE ROOMLOCATION SET name = :name , labelname = :labelname , createdbyuser = :createdbyuser , datecreated = :datecreated  , comment = :comment where @rid = :id";


    private static final String DELETE = "DELETE VERTEX ROOMLOCATION WHERE @rid =:id";
    private static final String DELETE_BATCH = "begin;\n"
        + "let a = SELECT FROM RoomLocation WHERE @rid = :roomlocationid;\n"
        + "let b = SELECT FROM SITE WHERE @rid=:siteid;\n"
        + "let c = DELETE EDGE HASROOMLOCATION FROM $b to $a;\n"
        + "let d = DELETE VERTEX ROOMLOCATION WHERE @rid =:roomlocationid;\n"
        + "COMMIT RETRY 5;";

    private static final String RR_DEVICE_COUNT = "select * , Out('HasNode').asList().size() as rrcount from roomlocation where name = :rrname";

    public Flux<RoomLocation> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectRoomLocation(FIND_ALL, args);
    }

    public Mono<Void> save(RoomLocation roomLocation) {
        roomLocation.setRrname(roomLocation.getSite()+"/"+roomLocation.getLabelname().toUpperCase());
        Map<Object, Object> args = roomLocation.roomLocationMap();
        if(roomLocation.getSite() == null)
            throw new BadRequestAlertException("Please select site.",
                "nameexists", "nameexists");

        return siteRepository.findByName(roomLocation.getSite())
            .flatMap(site -> {
                args.put("edgename", site.getSitename() + "-->" + roomLocation.getRrname());
                args.put("siteid", site.getId());

                return updateRoomLocation(INSERT_BATCH, args);
            });
    }

    public Mono<Void> update(RoomLocation roomLocation) {
        roomLocation.setRrname(roomLocation.getSite()+"/"+roomLocation.getLabelname().toUpperCase());
        Map<Object, Object> args = roomLocation.roomLocationMap();
        args.put("id", roomLocation.getId());

        return updateRoomLocation(UPDATE, args);
    }

    public Mono<Void> updateRoomLocation(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = roomLocationMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Flux<RoomLocation> selectRoomLocation(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> roomLocationMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<RoomLocation> findRoomLocationName(String name) {
        if (name == null) {
            return Flux.empty();
        }

        Map<Object, Object> args = new HashMap<>();
        return selectRoomLocation(FIND_ALL, args)
            .filter(r -> name.equals(r.getLabelname()));
    }

    public Mono<RoomLocation> saveForCard(RoomLocation roomLocation) {

        Map<Object, Object> args = roomLocation.roomLocationMap();
        String label = roomLocation.getLabelname();
        if (label != null) {
            roomLocation.setRrname(roomLocation.getSite() + "/" + label.toUpperCase());
        } else {
            roomLocation.setRrname(roomLocation.getSite() + "/");
        }

        if (roomLocation.getSite() == null) {
            throw new BadRequestAlertException("Please select site.", "nameexists", "nameexists");
        }

        return siteRepository.findByName(roomLocation.getSite())
            .flatMap(site -> {
                args.put("edgename", site.getSitename() + "-->" + roomLocation.getRrname());
                args.put("siteid", site.getId());

                return updateRoomLocation(INSERT_BATCH, args)
                    .then(Mono.just(roomLocation));
            });
    }

}
