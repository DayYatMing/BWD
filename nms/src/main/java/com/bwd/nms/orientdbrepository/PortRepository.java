package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Port;
import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.PortMapper;
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
public class PortRepository {
    private final Logger log = LoggerFactory.getLogger(PortRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public PortMapper portMapper = new PortMapper();

    @Autowired
    RoomLocationRepository  roomLocationRepository;

    @Autowired
    SegmentRepository segmentRepository;

    private static final String FIND_ALL = "select out('HasLSIODFPort').name as odf ,  out('HasInternalPortPatch').name as internalport , in('HasPort').in('HasDevice').in().in().name as site , in('HasPort').IN(HasDevice).in().labelname as roomlocation , in('HasPort').cardtype as cardtype , " +
        "in('HasPort').IN(HasDevice).shelf as shelf ,   in('HasPort').IN(HasDevice).slot as slot , in('HasPort').IN(HasDevice).position as position , out('HasPortSegment').dls as dls ,  IN('HasCustomerServicePatch').name as serviceid ," +
        "  * from port ";
    private static final String FIND_ALL_BYNAME = "select out('HasLSIODFPort').name as odf ,  out('HasInternalPortPatch').name as internalport , in('HasPort').in('HasDevice').in().in().name as site , in('HasPort').IN(HasDevice).in().labelname as roomlocation , in('HasPort').cardtype as cardtype , " +
        "in('HasPort').IN(HasDevice).shelf as shelf ,  in('HasPort').IN(HasDevice).slot as slot , in('HasPort').IN(HasDevice).position as position , out('HasPortSegment').dls as dls ,  IN('HasCustomerServicePatch').name as serviceid ," +
        " * from port where name = :name ";
    private static final String FIND_BYNAME = "SELECT *,  IN('HasCustomerServicePatch').name as serviceid  FROM PORT WHERE name =:name ";
    private static final String FIND_BYID = "SELECT FROM PORT WHERE @rid =:id";

    private static final String FIND_ALL_PORT_DEVICE = "select out('HasLSIODFPort').name as odf ,  out('HasInternalPortPatch').name as internalport , in('HasPort').in('HasDevice').in().in().name as site , in('HasPort').IN(HasDevice).in().labelname as roomlocation , in('HasPort').cardtype as cardtype , " +
        "in('HasPort').IN(HasDevice).shelf as shelf ,   in('HasPort').IN(HasDevice).slot as slot , in('HasPort').IN(HasDevice).position as position , out('HasPortSegment').dls as dls ,  IN('HasCustomerServicePatch').name as serviceid ," +
        " in('HasPort').name as device, * from port ";

    private static final String FIND_PORT_DEVICE = "select out('HasLSIODFPort').name as odf ,  out('HasInternalPortPatch').name as internalport , in('HasPort').in('HasDevice').in().in().name as site , "+
        "in('HasPort').IN(HasDevice).in().labelname as roomlocation , in('HasPort').cardtype as cardtype , "+
        "in('HasPort').IN(HasDevice).shelf as shelf ,   in('HasPort').IN(HasDevice).slot as slot , in('HasPort').IN(HasDevice).position as position , out('HasPortSegment').dls as dls , " +
        "IN('HasCustomerServicePatch').name as serviceid , "+
        "in('HasPort').name as device, * from port WHERE  route = :route and capacity = :capacity and IN('HasCustomerServicePatch').name = :serviceid";

    private static final String FINDPORTS_BYSID = "SELECT in('HasPort').in('HasDevice').in('HasNode').in().name as "+
        " sites , * , OUT('HasLSIODFPort').name as lsiodfs, in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , " +
        "   out('HasLSIODFPort').out('HasODFServicePatch').name as patch1 , "+
        "  OUT('HasLSIODFPort').name as lsiodfs , out('HasLSIODFPort').out('HasODFServicePatch').comment as patch1_comment , "+
        "  OUT('HasLSIODFPort').name as lsiodfs , out('HasLSIODFPort').out('HasODFServicePatch').thirdparty as patch1_thirdparty , "+
        "  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name as patch2 , "+
        "  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment as patch2_comment  , "+
        "  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty as patch2_thirdparty , "+
        "  in('HasCustomerServicePatch').name as serviceid " +
        " from port "
        + "where  in('HasCustomerServicePatch').name = :serviceid order by name";

    private static final String FINDALL_CORELATION_PORTS = "select in('HasPort').in('HasDevice').in('HasNode').in().name as sites , "
        +" in('HasPort').dls as dls , in('HasPort').cardtype as cardtype , "
        + "  * from Port where not name like "
        + "'%ODF%'";

    //   private static final String FINDPROV_PORTS_BYROUTEID = "select * , in('HasPort').out('HasDeviceSegment').name as dls ,  in('HasPort').labelname as cardtype ,  "
//           + " in('HasPort').in('HasDevice').shelf as shelf , in('HasPort').in('HasDevice').slot as slot ,  in('HasPort').in('HasDevice').in('HasNode').labelname as room , "
//		   + " In('HasPort').In('HasDevice').In('HasNode').In('HasRoomLocation').labelname as sites , "
//		   + "  OUT('HasLSIODFPort').name as lsiodfs "
//		   + " from port where not name  like '%ODF%'  and capacity = :capacity  and portstatus = 'FREE' "+
//            " and :routename in in('HasPort').out('HasDeviceSegment').in('HasSegment').name order by name";
//
    private static final String FINDPROV_PORTS_BYROUTEID = "select * , in('HasPort').out('HasDeviceSegment').name as dls ,  in('HasPort').labelname as cardtype ,  "
        + " in('HasPort').in('HasDevice').shelf as shelf , in('HasPort').in('HasDevice').slot as slot ,  in('HasPort').in('HasDevice').in('HasNode').labelname as room , "
        + " In('HasPort').In('HasDevice').In('HasNode').In('HasRoomLocation').labelname as sites , "
        + "  OUT('HasLSIODFPort').name as lsiodfs "
        + " from port where not name  like '%ODF%'  and capacity = :capacity  and portstatus = 'FREE' "+
        " and :routename in out('HasPortSegment').in('HasSegment').name order by name";



    private static final String FIND_FREEPREPATCHINGPORTS = "select *,  out('HasInternalPortPatch').name as patch1, "+
        "comment as patch1_comment, thirdparty as patch1_thirdparty, "+
        "out('HasInternalPortPatch').name as patch2,  out('HasInternalPortPatch').comment as patch2_comment, "+
        "out('HasInternalPortPatch').thirdparty as patch2_thirdparty, out('HasInternalPortPatch').out('HasInternalPortPatch').name as patch3, "+
        "out('HasInternalPortPatch').out('HasInternalPortPatch').comment as patch3_comment, out('HasInternalPortPatch').out('HasInternalPortPatch').thirdparty as patch3_thirdparty, "+
        "out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').name as patch4, out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').comment as patch4_comment, "+
        "out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').thirdparty as patch4_thirdparty from port where  name like '%ODF%' and "+
        "portstatus = 'FREE' and In('HasLSIODFPort').name == [] and In('HasODFServicePatch').name == [] and In('HasInternalPortPatch').name == [] order by patch1";

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

    private static final String INSERT = "INSERT INTO PORT ( name , labelname , createdby , datecreated , comment    "
        + " , frequency , wavelength , portstatus , connector , direction ,  capacity , thirdparty , route , segment )"
        + "VALUES ( :name , :labelname , :createdby , :datecreated , :comment , :frequency , :wavelength , :portstatus"
        + ",  :connector , :direction , :capacity , :thirdparty , :route , :segment)";

    private static final String INSERT_BATCH = "begin;\n"
        + "let a = INSERT INTO PORT ( name , labelname , createdby ,  comment , frequency , wavelength , portstatus , connector , direction ,  capacity , thirdparty ,  segment , portproperty)  VALUES ( :portname , :labelname  , :createdby , :comment , :frequency , :wavelength , 'FREE' ,  :connector , :direction , :capacity , :thirdparty , :segment , :portproperty);\n"
        + "let b = SELECT FROM DEVICE WHERE name = :devicelabelname;\n"
        + "let c = CREATE EDGE HASPORT FROM $b to $a set name = :edge_cardportname;\n"
        + "let d = SELECT FROM SEGMENT WHERE name = :segment;\n"
        + "if($d.SIZE() != 0){\n"
        + "let e1 = CREATE EDGE HASPORTSEGMENT FROM $a to $d set name = :edge_portsegmentname;\n"
        + "}\n"
        + "if(:internalport = false){\n"
        + "let e2 = SELECT FROM PORT WHERE name = :mappedportname;\n"
        + "let f = CREATE EDGE HasLSIODFPort FROM $a to $e2 set name = :edge_portodfname;\n"
        + "}\n"
        + "if(:odf = false){\n"
        + "let e3 = SELECT FROM PORT WHERE name = :mappedportname;\n"
        + "let f1 = CREATE EDGE HasInternalPortPatch FROM $a to $e3 set name = :edge_portodfname;\n"
        + "}\n"
        + "COMMIT RETRY 5;\n"
        + "return $a;";



    private static final String DELETE_BATCH = "begin;\n"
        + "let a = SELECT FROM SEGMENT WHERE dls = :dls;\n"
        + "let b = SELECT FROM PORT WHERE name = :portname;\n"
        + "let c = SELECT FROM DEVICE WHERE name = :devicename;\n"
        + "if($a.SIZE() != 0){\n"
        + "let a1 = DELETE EDGE HASPORTSEGMENT from $b to $a;\n"
        + "}\n"
        + "if(:odf = false){\n"
        + "let d = SELECT FROM PORT WHERE name = :mappedportname;\n"
        + "let e = DELETE EDGE HasInternalPortPatch from $b to $d;\n"
        + "}\n"
        + "if(:internalport = false){\n"
        + "let f = SELECT FROM PORT WHERE name = :mappedportname;\n"
        + "let g = DELETE EDGE HasLSIODFPort from $b to $f;\n"
        + "}\n"
        + "let h = DELETE EDGE HasPort from $c to $b;\n"
        + "let i = DELETE VERTEX PORT where name = :portname;\n"
        + "COMMIT RETRY 5;\n"
        + "return $d;";

    private static final String REMOVE_SERVICE =   "DELETE EDGE HasCustomerServicePatch where name = :serviceportname";
    private static final String UPDATE_CUSTOMERSERVICE = "CREATE EDGE HasCustomerServicePatch FROM (select from service where name = :serviceid ) to (select from port where name = :portname ) set name = :serviceportname";
    private static final String UPDATE_PORTSTATUS = "UPDATE port set portstatus = :portstatus where @rid = :id ";
    private static final String REMOVE_PORTPATCH = "DELETE EDGE HasODFServicePatch WHERE @rid IN (SELECT @rid FROM HasODFServicePatch WHERE name = :lsiodfpatch1)";
    private static final String UPDATE_PORTPATCH1 = "CREATE EDGE HasODFServicePatch FROM (SELECT FROM PORT WHERE name = :lsiodf) to (select from port where name = :patch1 ) set name = :lsiodfpatch1";
    private static final String UPDATE_PATCH_STATUS = "UPDATE port set portstatus = :portstatus where name = :patch1 ";
    private static final String UPDATE_PORTSTATUS_PATCH1 = "UPDATE port set portstatus = :portstatus where name = :patch1 ";
    private static final String UPDATE_PORTSTATUS_PATCH2 = "UPDATE port set portstatus = :portstatus where name = :patch2 ";

    public Flux<Port> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectPort(FIND_ALL, args);
    }

    public Mono<Void> save(Port port) {

        Map<Object, Object> args = port.portMap();

        return fillArgs(args)
            .then(Mono.defer(() -> {
                if ("TX".equalsIgnoreCase(port.getDirection()) || "RX".equalsIgnoreCase(port.getDirection())) {
                    args.put("portname",
                        args.get("portname") + port.getLabelname() + "-" + port.getDirection() + ":C_" + port.getConnector());
                    args.put("internalport", false);
                    args.put("odf", true);
                } else {
                    args.put("portname",
                        args.get("portname") + port.getLabelname() + ":Ps_" + port.getPosition() + ":C_" + port.getConnector());
                    args.put("internalport", true);
                    args.put("odf", false);
                }

                // Edge names
                args.put("edge_portodfname", args.get("portname") + "-->" + args.get("mappedportname"));
                args.put("edge_cardportname", args.get("devicelabelname") + "-->" + args.get("portname"));
                args.put("edge_portsegmentname", args.get("portname") + "-->" + args.get("segment"));

                return updatePort(INSERT_BATCH, args);
            }));
    }

    public Mono<Void> update(Port port) {
        return delete(port).then(save(port));
    }

    public Mono<Void> delete(Port port) {
        Map<Object, Object> args = port.portMap();

        return fillArgs(args)
            .then(Mono.defer(() -> {
                args.put("portname", port.getPortname());

                if ("TX".equalsIgnoreCase(port.getDirection()) || "RX".equalsIgnoreCase(port.getDirection())) {
                    args.put("internalport", false);
                    args.put("odf", true);
                } else {
                    args.put("internalport", true);
                    args.put("odf", false);
                }

                return updatePort(DELETE_BATCH, args).then(Mono.fromRunnable(() -> args.put("portname", null)));
            }));
    }

    public Mono<Port> findByName(String name){
        Map<Object, Object> args = new HashMap<>();
        args.put("name", name);

        return selectPort(FIND_ALL, args)
            .next();
    }

    public Mono<Void> updatePort(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = portMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Flux<Port> selectPort(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> portMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> fillArgs(Map<Object, Object> args) {

        String cardName = args.get("site") + "/" + args.get("cardtype") + "/" + args.get("roomlocation") + "/S_" +
            args.get("shelf") + ":" + (args.get("slot") == null || "".equals(args.get("slot")) ? "Ps_" + args.get("position") : "Sl_" + args.get("slot"));

        String rackname = args.get("site") + "/" + args.get("roomlocation") + "/S_" +
            args.get("shelf") + ":" + (args.get("slot") == null || "".equals(args.get("slot")) ? "Ps_" + args.get("position") : "Sl_" + args.get("slot"));

        String racklabelname = "/S_" +
            args.get("shelf") + ":" + (args.get("slot") == null || "".equals(args.get("slot")) ? "Ps_" + args.get("position") : "Sl_" + args.get("slot"));

        String portname = args.get("site") + "/" + args.get("cardtype") + "/" + args.get("roomlocation") + "/S_" +
            args.get("shelf") + (args.get("slot") == null || "".equals(args.get("slot")) ? ":P_" : ":Sl_" + args.get("slot") + ":P_");

        args.put("datecreated", new Date());
        args.put("nodename", rackname);
        args.put("cardcomment", args.get("comment"));

        String name = (String)args.get("roomlocation");

        return roomLocationRepository.findRoomLocationName(name)
            .next()
            .switchIfEmpty(Mono.defer(() -> {
                RoomLocation roomLocation = new RoomLocation();
                roomLocation.setSite((String) args.get("site"));
                roomLocation.setLabelname((String) args.get("roomlocation"));
                return roomLocationRepository.saveForCard(roomLocation);
            }))
            .flatMap(roomLocation -> {
                args.put("roomlocationname", args.get("site") + "/" + args.get("roomlocation"));
                args.put("rlid", roomLocation.getId());

                String dls = (String) args.get("dls");
                Mono<Segment> segmentMono = dls != null ? segmentRepository.findByDLS(dls) : Mono.empty();

                return segmentMono
                    .doOnNext(segment -> args.put("segment", segment.getLabelname()))
                    .then(Mono.fromRunnable(() -> {
                        if (args.get("totalports") == null) args.put("totalports", 0);

                        args.put("nodelabelname", racklabelname);
                        args.put("edge_noderoomlocationname", args.get("site") + "/" + args.get("roomlocation") + "-->" + rackname);
                        args.put("devicename", cardName);
                        args.put("devicelabelname", cardName);
                        args.put("edge_nodedevicename", rackname + "-->" + cardName);

                        if (args.get("portname") == null) args.put("portname", portname);

                        args.put("edge_deviceportname", cardName + "-->" + portname);
                        args.put("portproperty", "N/A");
                    }));
            });
    }

}
