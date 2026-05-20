package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.orientdbdomain.OdfmmrportData;
import com.bwd.nms.service.mapper.LsiodfportMapper;
import com.bwd.nms.service.mapper.OdfmmrportMapper;
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
public class LsiodfportRepository {
    private final Logger log = LoggerFactory.getLogger(LsiodfportRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    @Autowired
    public LsiodfportMapper mapper;

    private static final String FIND_FREEODFPORTS = "SELECT EXPAND( $d ) "+
        " LET $a = ( select comment , if(eval(\"portstatus == 'ALLOCATED'\"), \"PROVISIONED\", \"CONNECTED\")  as portstatus , name.asList() as lsiodf , connector.asList() as lsi_connector , labelname.asList() as lsi_port ,   " +
        "in('HasPort').cardtype as lsi_odfcardtype , in('HasPort').in().in().labelname as  lsi_roomlocation  ,  in('HasPort').in('HasDevice').position as lsi_position " +
        ", in('HasPort').in('HasDevice').shelf as lsi_shelf , in('HasLSIODFPort').in('HasCustomerServicePatch').name as service , " +
        "in('HasLSIODFPort').in('HasCustomerServicePatch').in('HasService').name as  customer , in('HasPort').in().in().in().name as site , " +
        "in('HasLSIODFPort').name as patch1 , in('HasLSIODFPort').connector as patch1_connector ,  " +
        "in('HasLSIODFPort').in('HasPort').in().in().labelname as  patch1_roomlocation , in('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as patch1_shelf," +
        "in('HasLSIODFPort').in('HasPort').in('HasDevice').position as patch1_position, " +
        "in('HasLSIODFPort').in('HasPort').cardtype as patch1cardtype , " +
        "in('HasLSIODFPort').in('HasPort').in().in().labelname as patch1roomlocation," +
        "in('HasLSIODFPort').labelname as patch1_port," +
        "in('HasLSIODFPort').connector as patch1_connector," +
        "in('HasLSIODFPort').comment as patch1_comment " +
        "from port where In('HasLSIODFPort').name != [] )," +
        "  $b = (select comment , portstatus , name.asList() as lsiodf , connector.asList() as lsi_connector , labelname.asList() as lsi_port ,   " +
        "in('HasPort').cardtype as lsi_odfcardtype , in('HasPort').in().in().labelname as  lsi_roomlocation  ,  in('HasPort').in('HasDevice').position as lsi_position " +
        ", in('HasPort').in('HasDevice').shelf as lsi_shelf , in('HasLSIODFPort').in('HasCustomerServicePatch').name as service , " +
        "in('HasLSIODFPort').in('HasCustomerServicePatch').in('HasService').name as  customer , in('HasPort').in().in().in().name as site , " +
        "in('HasLSIODFPort').name as patch1 , in('HasLSIODFPort').connector as patch1_connector ,  " +
        "in('HasLSIODFPort').in('HasPort').in().in().labelname as  patch1_roomlocation , in('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as patch1_shelf," +
        "in('HasLSIODFPort').in('HasPort').in('HasDevice').position as patch1_position, " +
        "in('HasLSIODFPort').in('HasPort').cardtype as patch1cardtype , " +
        "in('HasLSIODFPort').in('HasPort').in().in().labelname as patch1roomlocation," +
        "in('HasLSIODFPort').labelname as patch1_port," +
        "in('HasLSIODFPort').connector as patch1_connector," +
        "in('HasLSIODFPort').comment as patch1_comment " +
        "from port where name like '%ODF_TRX%' and comment like '%LSI ODF%' and portstatus = 'NOT_CONNECTED'  ) , " +
        "$d = UNIONALL( $b , $a )";

    public Flux<LsiodfportData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(FIND_FREEODFPORTS, args);
    }

    public Flux<LsiodfportData> select(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db
                        .query(query, args)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results ->
                Flux.fromIterable(results)
                    .map(result -> mapper.fromResult(result)))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
