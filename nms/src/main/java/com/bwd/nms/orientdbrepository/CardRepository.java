package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Port;
import com.bwd.nms.orientdbdomain.RoomLocation;
import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.security.SecurityUtils;
import com.bwd.nms.service.mapper.CardMapper;
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

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class CardRepository {
    private final Logger log = LoggerFactory.getLogger(CardRepository.class);

    @Autowired
    private ODatabasePool databasePool;
    public CardMapper cardMapper = new CardMapper();

    @Autowired
    RoomLocationRepository roomLocationRepository;

    @Autowired
    SegmentRepository segmentRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    PortRepository portRepository;

    private static final String FIND_ALL = "select name as cardlabelname , in('HasDevice').in().in().name as site , out('HasPort').name as portnames ,   out('HasPort').name.asSet().SIZE() as totalports ,  out('HasPort').capacity.asSet() as capacity,"
        + " out('HasPort').portstatus AS status,  out('HasPort').out('HasPortSegment').dls.asSet()  as dls ,  out('HasPort').portproperty.asSet()  as portproperty ,"
        + " IN(HasDevice).in().labelname as roomlocation ,  IN(HasDevice).shelf as shelf ,  IN(HasDevice).slot as slot , out('HasPort').connector.asSet() as connector , out('HasPort').frequency.asSet() as frequency ,  out('HasPort').wavelength.asSet() as wavelength ,  out('HasPort').comment.asSet() as comment ,"
        + "IN(HasDevice).position as position   , * from Device unwind shelf , slot , position , site , roomlocation , dls , capacity , portproperty , wavelength , frequency , connector , comment";

    private static final String FIND_BYNAME =  "select name as cardlabelname , in('HasDevice').in().in().name as site , out('HasPort').name as portnames ,   out('HasPort').name.asSet().SIZE() as totalports ,  out('HasPort').capacity.asSet() as capacity, "
        + " out('HasPort').portstatus AS status, out('HasPort').out('HasPortSegment').dls.asSet() as dls ,  "
        + " IN(HasDevice).in().labelname as roomlocation ,  IN(HasDevice).shelf as shelf ,  IN(HasDevice).slot as slot , "
        + "IN(HasDevice).position as position   , * from Device WHERE name =:name  unwind shelf , slot , position , site , roomlocation , dls, capacity";

    private static final String FIND_BYID =  "select name as cardlabelname , in('HasDevice').in().in().name as site ,  out('HasPort').name as portnames ,   out('HasPort').name.asSet().SIZE() as totalports ,   out('HasPort').capacity.asSet() as capacity,"
        + " out('HasPort').portstatus AS status, out('HasPort').out('HasPortSegment').dls.asSet()  as dls , "
        + " IN(HasDevice).in().labelname as roomlocation ,  IN(HasDevice).shelf as shelf ,  IN(HasDevice).slot as slot , "
        + "IN(HasDevice).position as position   , * from Device WHERE @rid =:id unwind shelf , slot , position , site , roomlocation , dls , capacity";

    private static final String INSERT = "INSERT INTO DEVICE ( name , labelname , createdbyuser , datecreated , comment ,"
        + " cardtype , cardstate , dls , model ) "
        + "  VALUES ( :name , :labelname , :createdbyuser , :datecreated , :comment , :cardtype , :cardstate , "
        + ":dls , :model)";

    private static final String INSERT_BATCH =
        "begin;\n"
            + "let a = INSERT INTO NODE (name, labelname, shelf, slot, position, createdby, comment) "
            + "VALUES (:nodename, :nodelabelname, :shelf, :slot, :position, :createdby, '');\n"

            + "let b = SELECT FROM ROOMLOCATION WHERE @rid = :rlid;\n"

            + "let c = CREATE EDGE HASNODE FROM $b TO $a SET name = :edge_noderoomlocationname;\n"

            + "let d = INSERT INTO DEVICE (name, labelname, status, vendor, cardtype, createdby, comment, totalports, tx, nodename) "
            + "VALUES (:devicename, :devicelabelname, 'IN_SERVICE', :vendor, :cardtype, :createdby, :cardcomment, :totalports, :tx, :nodename);\n"

            + "let e = CREATE EDGE HASDEVICE FROM $a TO $d SET name = :edge_nodedevicename;\n"

            + "let $portnumber = 1;\n"

            + "WHILE ($portnumber <= :totalports) {\n"

            + "if(:tx = true) {\n"

            + "let f = INSERT INTO PORT (name, labelname, createdby, comment, frequency, wavelength, portstatus, connector, direction, capacity, thirdparty, segment, portproperty) "
            + "VALUES (:portname + $portnumber + '-Tx' + :C_ + :connector, $portnumber, :createdby, :comment, :frequency, :wavelength, 'FREE', :connector, 'Tx', :capacity, :thirdparty, :segment, :portproperty);\n"

            + "let g = CREATE EDGE HasPort FROM $d TO $f SET name = :edge_deviceportname + $portnumber + '-Tx' + :C_ + :connector;\n"

            + "let h = SELECT FROM SEGMENT WHERE dls = :dls;\n"

            + "if($h.SIZE() != 0) {\n"
            + "let i = CREATE EDGE HASPORTSEGMENT FROM $f TO $h SET name = :portname + $portnumber + '-Tx' + :C_ + :connector + '-->' + :segment;\n"
            + "}\n"

            + "let f1 = INSERT INTO PORT (name, labelname, createdby, comment, frequency, wavelength, portstatus, connector, direction, capacity, thirdparty, segment, portproperty) "
            + "VALUES (:portname + $portnumber + '-Rx' + :C_ + :connector, $portnumber, :createdby, :comment, :frequency, :wavelength, 'FREE', :connector, 'Rx', :capacity, :thirdparty, :segment, :portproperty);\n"

            + "let g1 = CREATE EDGE HasPort FROM $d TO $f1 SET name = :edge_deviceportname + $portnumber + '-Rx' + :C_ + :connector;\n"

            + "let h1 = SELECT FROM SEGMENT WHERE dls = :dls;\n"

            + "if($h1.SIZE() != 0) {\n"
            + "let i1 = CREATE EDGE HASPORTSEGMENT FROM $f1 TO $h1 SET name = :portname + $portnumber + '-Rx' + :C_ + :connector + '-->' + :segment;\n"
            + "}\n"

            + "}\n"

            + "if(:tx = false) {\n"

            + "let f = INSERT INTO PORT (name, labelname, createdby, comment, frequency, wavelength, portstatus, connector, capacity, thirdparty, segment, portproperty) "
            + "VALUES (:portname + $portnumber + ':Ps_' + :position + :C_ + :connector, $portnumber, :createdby, :comment, :frequency, :wavelength, 'FREE', :connector, :capacity, :thirdparty, :segment, :portproperty);\n"

            + "let g = CREATE EDGE HasPort FROM $d TO $f SET name = :edge_deviceportname + $portnumber + ':Ps_' + :position + :C_ + :connector;\n"

            + "let h = SELECT FROM SEGMENT WHERE dls = :dls;\n"

            + "if($h.SIZE() != 0) {\n"
            + "let i = CREATE EDGE HASPORTSEGMENT FROM $f TO $h SET name = :portname + $portnumber + ':Ps_' + :position + :C_ + :connector + '-->' + :segment;\n"
            + "}\n"

            + "}\n"

            + "LET $portnumber = $portnumber + 1;\n"

            + "}\n"

            + "COMMIT RETRY 5;\n"

            + "return $d;";

    private static final String REVERT_BATCH = "begin;\n"
        + "COMMIT RETRY 5;\n"
        + "return $d;";

    private static final String UPDATE = "UPDATE DEVICE SET name = :name , labelname = :labelname , "
        + "createdbyuser = :createdbyuser , datecreated = :datecreated  , comment = :comment ,"
        + "cardtype = :cardtype , cardstate = :cardstate , dls = :dls , model = :model "
        + " where @rid = :id";

    private static final String DELETE = "DELETE VERTEX DEVICE WHERE @rid =:id";

    private static final String DELETE_BATCH =
        "begin;\n"
            + "let a1 = SELECT FROM ROOMLOCATION WHERE name = :roomlocationname;\n"
            + "let b1 = SELECT FROM NODE WHERE name = :nodename;\n"
            + "let c1 = SELECT FROM DEVICE WHERE name = :devicename;\n"

            + "let d11 = DELETE EDGE HASNODE FROM $a1 TO $b1;\n"
            + "let e11 = DELETE EDGE HASDEVICE FROM $b1 TO $c1;\n"

            + "let seg = SELECT FROM SEGMENT WHERE dls = :dls;\n"

            + "let $portnumber = 1;\n"

            + "WHILE ($portnumber <= :totalports) {\n"

            + "  if(:tx = true) {\n"

            + "    let f1 = SELECT FROM PORT WHERE name = :portname + $portnumber + '-Tx' + :connector;\n"
            + "    let g = DELETE EDGE HasPort FROM $c1 TO $f1;\n"

            + "    if($seg.SIZE() != 0) {\n"
            + "        let i = DELETE EDGE HASPORTSEGMENT FROM $f1 TO $seg;\n"
            + "    }\n"

            + "    let f = DELETE VERTEX PORT WHERE name = :portname + $portnumber + '-Tx' + :connector;\n"

            + "    let f2 = SELECT FROM PORT WHERE name = :portname + $portnumber + '-Rx' + :connector;\n"
            + "    let g1 = DELETE EDGE HasPort FROM $c1 TO $f2;\n"

            + "    if($seg.SIZE() != 0) {\n"
            + "        let i1 = DELETE EDGE HASPORTSEGMENT FROM $f2 TO $seg;\n"
            + "    }\n"

            + "    let f3 = DELETE VERTEX PORT WHERE name = :portname + $portnumber + '-Rx' + :connector;\n"

            + "  }\n"

            + "  if(:tx = false) {\n"

            + "    let f11 = SELECT FROM PORT WHERE name = :portname + $portnumber + ':Ps_' + :position + :connector;\n"
            + "    let g = DELETE EDGE HasPort FROM $c1 TO $f11;\n"

            + "    if($seg.SIZE() != 0) {\n"
            + "        let i = DELETE EDGE HASPORTSEGMENT FROM $f11 TO $seg;\n"
            + "    }\n"

            + "    let f4 = DELETE VERTEX PORT WHERE name = :portname + $portnumber + ':Ps_' + :position + :connector;\n"

            + "  }\n"

            + "  LET $portnumber = $portnumber + 1;\n"

            + "}\n"

            + "let d = DELETE VERTEX DEVICE WHERE name = :devicename;\n"
            + "let a = DELETE VERTEX NODE WHERE name = :nodename;\n"

            + "COMMIT RETRY 5;\n"

            + "return $d;";

    public Flux<Card> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return selectCard(FIND_ALL, args);
    }

    public Mono<Void> save(Card card) {

        Map<Object, Object> args = card.cardMap();

        return fillArgs(args)
            .then(Mono.defer(() -> {

                args.put("name", args.get("devicename"));
                String name = (String)args.get("devicename");

                Mono<Boolean> cardExists =
                    selectCard(FIND_ALL, args)
                        .filter(c -> name.equals(c.getCardname()))
                        .hasElements();

                Mono<Boolean> rackExists =
                    nodeRepository
                        .findByName((String) args.get("nodename"))
                        .hasElements();

                return cardExists.flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Card Name already Exists"));
                    }

                    return rackExists.flatMap(rExists -> {
                        if (rExists) {
                            return Mono.error(new RuntimeException("Rack Name already Exists"));
                        }

                        return updateCard(INSERT_BATCH, args);
                    });
                });

            }));
    }

    public Mono<Void> update(Card card) {
        return delete(card).then(save(card));
    }

    public Mono<Void> delete(Card card) {
        Map<Object, Object> args = card.cardMap();
        args.put("name", card.getCardlabelname());

        return selectCard(FIND_ALL, args)
            .filter(c -> card.getCardlabelname().equals(c.getCardlabelname()))
            .next()
            .switchIfEmpty(Mono.error(new RuntimeException("Card not found")))
            .flatMap(foundCard -> {
                Map<Object, Object> filledArgs = foundCard.cardMap();

                return fillArgs(filledArgs)
                    .then(Mono.just(filledArgs));
            })
            .flatMap(filledArgs -> {
                List<String> portNames = (List<String>) filledArgs.get("portnames");

                if (portNames == null || portNames.isEmpty()) {
                    // No ports, just delete
                    return updateCard(DELETE_BATCH, filledArgs);
                }

                // Check all ports asynchronously
                return Flux.fromIterable(portNames)
                    .flatMap(port -> portRepository.findByName(port)
                        .flatMap(portDetails -> {
                            if (portDetails.getServiceid() != null) {
                                return Mono.error(new RuntimeException(
                                    "Cannot delete cards whose ports are allocated to a service"));
                            }

                            filledArgs.put("connector", portDetails.getConnector());
                            if (port.contains("-Tx") || port.contains("-Rx")) {
                                filledArgs.put("tx", true);
                            }

                            return Mono.empty(); // Mono<Void>
                        })
                    )
                    .then(updateCard(DELETE_BATCH, filledArgs)); // <-- converts Flux<Void> to Mono<Void>
            }).then();
    }

    public Mono<Void> updateCard(String query, Map<Object, Object> args) {
        log.debug("Execute Query: {} \nArgs: {}", query, args);

        if (query.contains("UPDATE")) {
            args.put("dateupdated", new Date());
        } else {
            args.put("datecreated", new Date());
        }

        return SecurityUtils.getCurrentUserLogin()
            .defaultIfEmpty("anonymous")
            .flatMap(username -> {
                Map<String, Object> params = cardMapper.getParams(args, username);

                return Mono.fromRunnable(() -> {
                    try (ODatabaseSession db = databasePool.acquire()) {
                        db.execute("sql", query, params);
                    }
                }).subscribeOn(Schedulers.boundedElastic());
            })
            .then();
    }

    public Flux<Card> selectCard(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> cardMapper.fromResult(r))
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

        return roomLocationRepository.findRoomLocationName((String)args.get("roomlocation"))
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
