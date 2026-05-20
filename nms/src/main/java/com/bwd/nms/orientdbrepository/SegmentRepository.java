package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.SegmentMapper;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.impl.ODocument;
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

/**
 * Spring Data JPA repository for the Segment entity.
 */
@Repository
public class SegmentRepository {
    private final Logger log = LoggerFactory.getLogger(SegmentRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public SegmentMapper segmentMapper = new SegmentMapper();

    @Autowired
    SiteRepository siteRepository;

    private static final String FIND_ALL = "SELECT out('hasSite').latitude as lat , out('hasSite').longitude as long , out('hasSite').labelname as sitename , * FROM SEGMENT order by directionorder , dls";

    private static final String INSERT_BATCH = "BEGIN;\n"
        + "LET a = INSERT INTO SEGMENT ( name, labelname, createdby, datecreated, dls, comment, aend, bend, aendfiber, bendfiber, directionorder, networktype )\n"
        + "VALUES ( :name, :labelname, :createdby, :datecreated, :dls, :comment, :aend, :bend, :aendfiber, :bendfiber, :directionorder, :networktype );\n"
        + "CREATE EDGE HasAEndSegment FROM $a TO :rlida SET name = :edgenamea;\n"
        + "CREATE EDGE HasBEndSegment FROM $a TO :rlidb SET name = :edgenameb;\n"
        + "CREATE EDGE HasSite FROM $a TO :rlida SET name = :edgenameasite;\n"
        + "CREATE EDGE HasSite FROM $a TO :rlidb SET name = :edgenamebsite;\n"
        + "COMMIT RETRY 5;\n"
        + "RETURN $a;";

    private static final String UPDATE = "UPDATE SEGMENT SET name = :name , labelname = :labelname , "
        + "updateby = :updateby , dateupdated = :dateupdated  , comment = :comment , "
        + "latitude = :latitude , longitude = :longitude  ,aend = :aend , bend = :bend ,  aendfiber = :aendfiber ,"
        + " bendfiber = :bendfiber , directionorder = :directionorder, networktype = :networktype  where @rid = :id";

    private static final String FIND_BYID = "SELECT FROM SEGMENT WHERE @rid =:id";

    public Flux<Segment> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectSegment(FIND_ALL, args);
    }

    public Mono<Segment> findById(String id) {
        Map<Object, Object> args = new HashMap<>();
        args.put("id", id);
        return selectSegment(FIND_BYID, args).next();
    }

    public Mono<Void> update(Segment segment) {
        Map<Object, Object> args = segment.segmentMap();
        args.put("id", segment.getId());
        return updateSegment(UPDATE, args);
    }

    public Mono<Void> save(Segment segment) {

        segment.setSegmentname(segment.getSegmentname().toUpperCase());
        segment.setLabelname(segment.getSegmentname().toUpperCase());

        Map<Object, Object> args = segment.segmentMap();

        Mono<Site> siteAMono = siteRepository.findByName(segment.getAend());
        Mono<Site> siteBMono = siteRepository.findByName(segment.getBend());

        return Mono.zip(siteAMono, siteBMono)
            .flatMap(tuple -> {

                Site sitea = tuple.getT1();
                Site siteb = tuple.getT2();

                args.put("rlida", sitea.getId());
                args.put("rlidb", siteb.getId());

                args.put("edgenamea", segment.getSegmentname() + "-->" + sitea.getLabelname());
                args.put("edgenameb", segment.getSegmentname() + "-->" + siteb.getLabelname());

                args.put("edgenameasite", segment.getSegmentname() + "-->" + sitea.getLabelname() + ":site");
                args.put("edgenamebsite", segment.getSegmentname() + "-->" + siteb.getLabelname() + ":site");

                return insertSegment(INSERT_BATCH, args);
            });
    }

    public Flux<Segment> selectSegment(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> segmentMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> updateSegment(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = segmentMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.command(query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Mono<Void> insertSegment(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = segmentMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Mono<Segment> findByDLS(String dls){

        Map<Object, Object> args = new HashMap<>();
        args.put("dls", dls);

        return selectSegment(FIND_ALL, args)
            .filter(s -> dls.equals(s.getDls()))
            .next();

    }
}
