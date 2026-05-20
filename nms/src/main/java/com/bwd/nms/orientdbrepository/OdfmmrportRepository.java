package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.OdfmmrportData;
import com.bwd.nms.orientdbdomain.Site;
import com.bwd.nms.service.mapper.OdfmmrportMapper;
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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OdfmmrportRepository {
    private final Logger log = LoggerFactory.getLogger(OdfmmrportRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    @Autowired
    public OdfmmrportMapper mapper;

    private static final String FIND_ALLPREPATCHINGPORTS = "select In('HasODFServicePatch').In('HasLSIODFPort').In('HasCustomerServicePatch').name as service , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site"
        + ", if(eval(\"In('HasODFServicePatch').name == []\"), \"FREE\", \"ALLOCATED\") as portstatus ,  name.asList() as patch1, comment.asList() as patch1_comment, thirdparty.asList() as patch1_thirdparty, "
        + " In('HasPort').cardtype as patch1_cardtype , In('HasPort').in().in().labelname as  patch1_roomlocation , In('HasPort').in('HasDevice').shelf as patch1_shelf , In('HasPort').in('HasDevice').position as patch1_position , labelname.asList() as patch1_port , connector.asList() as patch1_connector ,"
        + " out('HasInternalPortPatch').name as patch2,  out('HasInternalPortPatch').comment as patch2_comment, "
        + " out('HasInternalPortPatch').In('HasPort').cardtype as patch2_cardtype , out('HasInternalPortPatch').In('HasPort').in().in().labelname as  patch2_roomlocation , out('HasInternalPortPatch').In('HasPort').in('HasDevice').shelf as patch2_shelf , out('HasInternalPortPatch').In('HasPort').in('HasDevice').position as patch2_position , out('HasInternalPortPatch').labelname as patch2_port , out('HasInternalPortPatch').connector as patch2_connector ,"
        + " out('HasInternalPortPatch').thirdparty as patch2_thirdparty, out('HasInternalPortPatch').out('HasInternalPortPatch').name as patch3, "
        + " out('HasInternalPortPatch').out('HasInternalPortPatch').comment as patch3_comment, out('HasInternalPortPatch').out('HasInternalPortPatch').thirdparty as patch3_thirdparty, "
        +" out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').name as patch4, out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').comment as patch4_comment, "
        +" out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').thirdparty as patch4_thirdparty from port where  name like '%ODF%'  "
        +" and In('HasLSIODFPort').name == []  and In('HasInternalPortPatch').name == [] order by portstatus desc,  patch1";

    public Flux<OdfmmrportData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(FIND_ALLPREPATCHINGPORTS, args);
    }

    public Flux<OdfmmrportData> select(String query, Map<Object, Object> args) {

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
