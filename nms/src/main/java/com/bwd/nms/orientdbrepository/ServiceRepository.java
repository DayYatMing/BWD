package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Services;
import com.bwd.nms.service.mapper.ServicesMapper;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class ServiceRepository {
    private final Logger log = LoggerFactory.getLogger(ServiceRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    public ServicesMapper servicesMapper = new ServicesMapper();

    private static final String FIND_ALL_BY_SEGMENT =
        "SELECT FROM service WHERE out('HasCustomerServicePatch').segment CONTAINS :segmentname ORDER BY name";


    public Flux<Services> findAllForSegment(String segmentName){
        Map<Object, Object> args = new HashMap<>();
        args.put("segmentname", segmentName);
        return selectService(FIND_ALL_BY_SEGMENT, args);

    }

    public Flux<Services> selectService(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query, args)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> servicesMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

}
