package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.CapPlanningData;
import com.bwd.nms.service.mapper.CapPlanningMapper;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.executor.OResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Spring Data JPA repository for the Site entity.
 */
@Repository
public class CapacityPlanningRepository {

    private final Logger log = LoggerFactory.getLogger(CapacityPlanningRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    public CapPlanningMapper capPlanningMapper = new CapPlanningMapper();

    private static final String FIND_ALL = " SELECT EXPAND( $e ) "+
        "			  LET $a = ( select  name , frequency , capacity , portstatus , route , segment , customerserviceid, in('HasPort').out('HasDeviceSegment').name as segmentnm ," +
        "             in('HasCustomerServicePatch').name as service ,   in('HasPort').model as vendors ,  in('HasPort').out('HasDeviceSegment').networktype as networktype,    in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().in().in().name as site ," +
        "             in('HasCustomerServicePatch').in('HasService').name as  customer ,  out('HasLSIODFPort').name as lsiodf " +
        "             from port where not name like '%ODF%' and name is not null  order by portstatus desc ,  name  )  , $b = ( SELECT FROM DACPORT ) , " +
        "			  $c = ( select out('HasOffnetService').in('HasService').name as customer , vendorname as  vendor , status as portstatus , name AS cardtype , out('HasOffnetService').name as service , out('HasOffnetAEnd').name as site ,  capacity , aenddetails +\"_OFF-NET\" as name , "+
        "			  frequency , out('HasOffnetSegment').name as segmentnm ,  out('HasOffnetSegment').networktype as networktype from offnet ) ,  "+
        "			  $d = ( select out('HasOffnetService').in('HasService').name as customer , vendorname as  vendor , status as portstatus ,  name AS cardtype , out('HasOffnetService').name as service , out('HasOffnetBEnd').name as site ,  capacity , benddetails +\"_OFF-NET\" as name , "+
        "			  frequency , out('HasOffnetSegment').name as segmentnm ,  out('HasOffnetSegment').networktype as networktype from offnet ) , "+
        "			  $e = UNIONALL( $a, $b , $c, $d)";

    public Flux<CapPlanningData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(FIND_ALL, args);
    }

    public Flux<CapPlanningData> select(String query, Map<Object, Object> args) {
        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Flux.usingWhen(
                Mono.fromCallable(() -> databasePool.acquire()),
                db -> {
                    return Flux.fromStream(db.query(query, args).stream())
                        .map(OResult::toElement)
                        .cast(ODocument.class)
                        .map(capPlanningMapper::fromDocument);
                },
                db -> Mono.fromRunnable(db::close)
            )

            .subscribeOn(Schedulers.boundedElastic());
    }

}
