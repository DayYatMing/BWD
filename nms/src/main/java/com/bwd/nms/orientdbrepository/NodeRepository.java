package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Node;
import com.bwd.nms.service.mapper.NodeMapper;
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
public class NodeRepository {
    @Autowired
    private ODatabasePool databasePool;
    public NodeMapper nodeMapper = new NodeMapper();

   private final Logger log = LoggerFactory.getLogger(NodeRepository.class);

   private static final String FIND_ALL = "SELECT IN('HasNode').labelname as roomlocation , IN('HasNode').IN('HasRoomLocation').name as site  , *  FROM NODE";
   private static final String FIND_ALL_ROOMLOCATION = "SELECT IN('HasNode').labelname as roomlocation , IN('HasNode').IN('HasRoomLocation').name as site  , *  FROM NODE where name like '%:roomlocation%'";
   private static final String FIND_BYNAME = "SELECT IN('HasNode').labelname as roomlocation , IN('HasNode').IN('HasRoomLocation').name as site  , * FROM NODE WHERE name =:name";
   private static final String FIND_BYID = "SELECT IN('HasNode').labelname as roomlocation , IN('HasNode').IN('HasRoomLocation').name as site  , * FROM NODE WHERE @rid =:id";
   private static final String INSERT = "INSERT INTO NODE ( name , labelname , createdby , datecreated , comment )   VALUES ( :name , :labelname , :createdbyuser , :datecreated , :comment)";
   private static final String INSERT_BATCH = "begin;\n"
	   		+ "let a = INSERT INTO NODE ( name , labelname , shelf , slot , position ,  createdbyuser ,  comment )   VALUES ( ':name' , ':labelname' ,  ':shelf' , ':slot' , ':position' , ':createdbyuser' , ':comment');\n"
			+ "let b = SELECT FROM ROOMLOCATION WHERE @rid=:rlid;\n"
	   		+ "let c = CREATE EDGE HASNODE FROM $b to $a set name = ':edgename';\n"
			+ "COMMIT RETRY 5;\n"
	   		+ "return $a;";
   private static final String UPDATE = "UPDATE NODE SET name = :name , "
   		+ "labelname = :labelname , updatedby = :updatedby , "
   		+ "dateupdated = :dateupdated  , "
   		+ " shelf = :shelf ,  slot = :slot , position = :position ,"
   		+ "comment = :comment where @rid = :id";

   private static final String DELETE = "DELETE VERTEX NODE WHERE @rid =:id";
   private static final String DELETE_BATCH = "begin;\n"
	   		+ "let a = SELECT FROM Node WHERE @rid = :nodeid;\n"
			+ "let b = SELECT FROM RoomLocation WHERE @rid=:rlid;\n"
	   		+ "let c = DELETE EDGE HASNODE FROM $b to $a;\n"
	   		+ "let d = DELETE VERTEX Node WHERE @rid =:nodeid;\n"
			+ "COMMIT RETRY 5;";


    public Flux<Node> findByName(String name){
        Map<Object, Object> args = new HashMap<>();
        args.put("name", name);
        return selectNode(FIND_ALL, args)
            .filter(n -> name.equals(n.getName()));
    }

    public Flux<Node> selectNode(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db.query(query)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results -> Flux.fromIterable(results)
                .map(r -> nodeMapper.fromResult(r))
            )
            .subscribeOn(Schedulers.boundedElastic());
    }
}
