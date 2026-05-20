package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.ClientportData;
import com.bwd.nms.service.mapper.ClientportMapper;
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
public class ClientportRepository {
    private final Logger log = LoggerFactory.getLogger(ClientportRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    @Autowired
    public ClientportMapper mapper;

    private static final String ALL_NETWORKDATA_CORELATION_QUERY = " select in('HasCustomerServicePatch').in('HasBackhaulService').name as backhaul,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').providerName as backhaulvendor,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').out('HasBackhaulAend').name as backhaulaend,  @rid , @rid as tblid, name ,customerserviceid,  labelname as portnumber ,  direction, connector , comment , wavelength , frequency , capacity , portstatus , route , segment ,  in('HasPort').out('HasDeviceSegment').name as segmentnm , in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , "+
        " in('HasCustomerServicePatch').name as service ,  in('HasPort').model as vendors ,  in('HasPort').in('HasDevice').shelf as shelfs , in('HasPort').in('HasDevice').slot as slots , in('HasPort').in('HasDevice').position as positions ,"+
        " in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site ,"+
        " in('HasCustomerServicePatch').in('HasService').name as  customer,"+
        " out('HasLSIODFPort').name as lsiodf , out('HasLSIODFPort').in('HasPort').cardtype as lsi_odfcardtype ,  out('HasLSIODFPort').in('HasPort').in().in().labelname as  lsi_roomlocation "+
        ", out('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as lsi_shelf , out('HasLSIODFPort').in('HasPort').in('HasDevice').position as lsi_position , out('HasLSIODFPort').labelname as lsi_port , "+
        " out('HasLSIODFPort').connector as lsi_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').name as patch1 , out('HasLSIODFPort').out('HasODFServicePatch').comment as patch1_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').thirdparty as patch1_thirdparty , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype as patch1_cardtype ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname as  patch1_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf as patch1_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position as patch1_position , out('HasLSIODFPort').out('HasODFServicePatch').labelname as patch1_port , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').connector as patch1_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name as patch2  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment as patch2_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty as patch2_thirdparty , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype as patch2_cardtype , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname "+
        " as  patch2_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf as patch2_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position as patch2_position , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname as "+
        " patch2_port , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector as patch2_connector "+
        " from port where not name like '%ODF%' and name is not null  order by portstatus desc ,  name ";

    public Flux<ClientportData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(ALL_NETWORKDATA_CORELATION_QUERY, args);
    }

    public Flux<ClientportData> select(String query, Map<Object, Object> args) {

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
