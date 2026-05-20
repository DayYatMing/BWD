package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.SiteMapper;
import java.util.*;
import java.util.stream.Collectors;

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

/**
 * Spring Data JPA repository for the Site entity.
 */
@Repository
public class SiteRepository {
    private final Logger log = LoggerFactory.getLogger(SiteRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public SiteMapper siteMapper = new SiteMapper();

    private static final String FIND_ALL = "SELECT FROM SITE";
    private static final String FIND_BYNAME = "SELECT FROM SITE WHERE name =:name";
    private static final String FIND_BYID = "SELECT FROM SITE WHERE @rid =:id";
    private static final String INSERT =
        "INSERT INTO SITE ( name , labelname , createdby , datecreated , comment " +
        ", latitude , longitude)  " +
        " VALUES ( :name , :labelname , :createdby , :datecreated , :comment , :latitude , :longitude)";
    private static final String UPDATE =
        "UPDATE SITE SET name = :name , labelname = :labelname , " +
        "updatedby = :updatedby , dateupdated = :dateupdated  , comment = :comment ," +
        "latitude = :latitude , longitude = :longitude where @rid = :id";

    public Flux<Site> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectSite(FIND_ALL, args);
    }

    public Mono<Site> findById(String id) {
        Map<Object, Object> args = new HashMap<>();
        args.put("id", id);
        return selectSite(FIND_BYID, args).next();
    }

    public Mono<Void> save(Site site) {
        site.setSitename(site.getLabelname().toUpperCase());
        site.setLabelname(site.getSitename());
        Map<Object, Object> args = site.siteMap();

        return executeSite(INSERT, args);
    }

    public Mono<Void> update(Site site) {
        site.setSitename(site.getLabelname().toUpperCase());
        site.setLabelname(site.getSitename());
        Map<Object, Object> args = site.siteMap();
        args.put("id", site.getId());
        return executeSite(UPDATE, args);
    }

    public Mono<Site> findByName(String name){
        Map<Object, Object> args = new HashMap<>();
        args.put("name", name);
        return selectSite(FIND_BYNAME, args).next();
    }

    public Flux<Site> selectSite(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db
                        .query(query, args)
                        .elementStream()
                        .map(e -> (ODocument) e.getRecord())
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(docs -> Flux.fromIterable(docs).map(doc -> siteMapper.fromDocument(doc)))
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> executeSite(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = siteMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.command(query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }
}
