package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.DaccorrelationData;
import com.bwd.nms.orientdbdomain.LsiodfportData;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.DaccorrelationMapper;
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
public class DaccorrelationRepository {
    private final Logger log = LoggerFactory.getLogger(DaccorrelationRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    @Autowired
    public DaccorrelationMapper mapper;

    private static final String FIND_ALL = "select @rid as fromtblid, serviceid as fromserviceid, site as fromsite , dls as fromdls , device as fromdevice, connectortype as fromconnectortype,"
        + "frequency as fromfrequency , rr as fromrr , shelf as fromshelf, slot as fromslot , name as fromname  , port as fromport ,"
        + "comment as fromcomment,out().@rid as totblid, out().site as tosites , out().dls as todlss , out().frequency as tofrequencys , "
        + "out().rr as torrs , out().device as todevices ,  out().shelf as toshelfs, out().slot as toslots , out().connectortype as toconnectortypes,"
        + "out().port as toports  ,   out().name as tonames  ,  out().serviceid as toserviceid,"
        + "out().comment as tocomments from dacport where  out().name.size() > 0 order by name";

    private static final String insertData1 = "INSERT INTO dacport SET " +
        "serviceid = :fromserviceid, " +
        "site = :fromsite, " +
        "dls = :fromdls, " +
        "device = :fromdevice, " +
        "connectortype = :fromconnectortype, " +
        "frequency = :fromfrequency, " +
        "rr = :fromrr, " +
        "shelf = :fromshelf, " +
        "slot = :fromslot, " +
        "name = :fromname, " +
        "port = :fromport, " +
        "portstatus = :fromportstatus, "+
        "comment = :fromcomment ;";


    private static final String insertData2 = "INSERT INTO dacport SET " +
        "serviceid = :toserviceid, " +
        "site = :tosite, " +
        "dls = :todls, " +
        "device = :todevice, " +
        "connectortype = :toconnectortype, " +
        "frequency = :tofrequency, " +
        "rr = :torr, " +
        "shelf = :toshelf, " +
        "slot = :toslot, " +
        "name = :toname, " +
        "port = :toport, " +
        "portstatus = :toportstatus, "+
        "comment = :tocomment ;";

    private static final String insertHasData =   "create edge hasdacport from (select  from dacport where name = :fromname) to (select  from dacport where name = :toname) set name = :name";

    private static final String UPDATE_FROM_SERVICE = "UPDATE dacport SET serviceid = :fromserviceid, portstatus = :fromportstatus, " +
        "site = :fromsite, dls = :fromdls, device = :fromdevice, connectortype = :fromconnectortype, frequency = :fromfrequency, rr = :fromrr, shelf = :fromshelf, " +
        "slot = :fromslot, name = :fromname, port = :fromport, comment = :fromcomment " +
        "where @rid = :fromtblid";

    private static final String UPDATE_TO_SERVICE = "UPDATE dacport SET serviceid = :toserviceid, portstatus = :toportstatus, " +
        "site = :tosite, dls = :todls, device = :todevice, connectortype = :toconnectortype, frequency = :tofrequency, rr = :torr, shelf = :toshelf, " +
        "slot = :toslot, name = :toname, port = :toport, comment = :tocomment " +
        " where @rid = :totblid";

    public Flux<DaccorrelationData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(FIND_ALL, args);
    }

    public Mono<Void> createData(DaccorrelationData daccorrelationData) {

        Map<Object, Object> argsFromData = mapper.dacportToMap(daccorrelationData);
        Map<Object, Object> argsToData = mapper.dacportToMap(daccorrelationData);
        Map<Object, Object> argsInsertHasData = mapper.dacportToMap(daccorrelationData);
        Map<Object, Object> argsUpdateDacData = mapper.dacportToMap(daccorrelationData);

        argsFromData.put("fromserviceid", daccorrelationData.getFromserviceid());
        argsFromData.put("fromcomment", daccorrelationData.getFromcomment());
        argsFromData.put("fromPortStatus", daccorrelationData.getFromPortStatus());
        argsFromData.put("fromname", daccorrelationData.getFromname());
        argsFromData.put("fromconnectortype",daccorrelationData.getFromconnectortype());
        argsFromData.put("fromrr",daccorrelationData.getFromrr());
        argsFromData.put("fromslot",daccorrelationData.getFromslot());
        argsFromData.put("fromshelf",daccorrelationData.getFromshelf());
        argsFromData.put("fromdls",daccorrelationData.getFromdls());
        argsFromData.put("fromfrequency",daccorrelationData.getFromfrequency());
        argsFromData.put("fromsite",daccorrelationData.getFromsite());
        argsFromData.put("fromdevice",daccorrelationData.getFromdevice());
        argsToData.put("toserviceid", daccorrelationData.getToserviceid());
        argsToData.put("tocomment", daccorrelationData.getTocomment());
        argsToData.put("toPortStatus", daccorrelationData.getToPortStatus());
        argsToData.put("toname", daccorrelationData.getToname());
        argsToData.put("toconnectortype",daccorrelationData.getToconnectortype());
        argsToData.put("torr",daccorrelationData.getTorr());
        argsToData.put("toslot",daccorrelationData.getToslot());
        argsToData.put("toshelf",daccorrelationData.getToshelf());
        argsToData.put("todls",daccorrelationData.getTodls());
        argsToData.put("tofrequency",daccorrelationData.getTofrequency());
        argsToData.put("tosite",daccorrelationData.getTosite());
        argsToData.put("todevice",daccorrelationData.getTodevice());


        argsInsertHasData.put("name", daccorrelationData.getFromname()+"-->"+daccorrelationData.getToname());
        argsInsertHasData.put("fromname", daccorrelationData.getFromname());
        argsInsertHasData.put("toname", daccorrelationData.getToname());

        return insert(insertData1, argsFromData)
            .then(insert(insertData2, argsToData))
            .then(insert(insertHasData, argsInsertHasData));
    }

    public Mono<Void> updateData(DaccorrelationData daccorrelationData) {
        Map<Object, Object> args = mapper.dacportToMap(daccorrelationData);
        args.put("fromtblid", daccorrelationData.getFromtblid());
        args.put("fromportstatus", daccorrelationData.getFromPortStatus());
        args.put("fromserviceid", daccorrelationData.getFromserviceid());

        String ridToNew = daccorrelationData.getTotblid().replace("[", "").replace("]", "");
        Map<Object, Object> args1 = mapper.dacportToMap(daccorrelationData);
        args1.put("totblid", ridToNew);
        args1.put("toportstatus", daccorrelationData.getToPortStatus());
        args1.put("toserviceid", daccorrelationData.getToserviceid());

        return update(UPDATE_FROM_SERVICE, args)
            .then(update(UPDATE_TO_SERVICE, args1));
    }

    public Mono<Void> insert(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.command(query, args);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Mono<Void> update(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.command(query, args);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Flux<DaccorrelationData> select(String query, Map<Object, Object> args) {

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
