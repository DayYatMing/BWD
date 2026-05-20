package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.NRMSQueue;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueOverviewRepository extends R2dbcRepository<NRMSQueue, String> {

    @Query(value = "SELECT queue,  SUM( new ) AS new ,  SUM( open ) AS open ,  SUM( pending_reminder ) AS pending_reminder FROM (  SELECT  SUM(CASE WHEN  ts.name = 'new' THEN 1 ELSE 0 END) AS NEW,  SUM(CASE WHEN  ts.name = 'open' THEN 1 ELSE 0 END) AS OPEN, SUM(CASE WHEN  ts.name = 'pending reminder' THEN 1 ELSE 0 END) AS pending_reminder , q.name AS queue FROM ticket t , queue q, ticket_type tt , users u , ticket_priority tp , ticket_state ts , ticket_lock_type tl  WHERE t.queue_id = q.id AND t.type_id = tt.id AND t.user_id = u.id AND t.ticket_priority_id = tp.id AND  t.ticket_state_id = ts.id AND t.ticket_lock_id = tl.id  AND ( ts.name = 'pending reminder' OR ts.name = 'new' OR ts.name = 'open' ) GROUP BY t.id ORDER BY  t.create_time ) T1 GROUP BY queue ORDER BY queue")
    List<NRMSQueue> findQueueOverview();

}
