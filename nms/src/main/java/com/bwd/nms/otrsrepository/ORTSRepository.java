package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.NRMSTicket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ORTSRepository extends R2dbcRepository<NRMSTicket, Long> {

        @Query(value = "SELECT t.id AS id,\n" +
            "       t.create_time AS create_time,\n" +
            "       t.change_time AS update_time,\n" +
            "       TIMESTAMPDIFF(MINUTE, t.create_time, NOW()) DIV 1440 AS days,\n" +
            "       TIMESTAMPDIFF(MINUTE, t.create_time, NOW()) DIV 60 AS hours,\n" +
            "       TIMESTAMPDIFF(MINUTE, t.create_time, NOW()) MOD 60 AS minutes,\n" +
            "       t.tn AS tn,\n" +
            "       t.title AS title,\n" +
            "       t.customer_user_id AS customeruser,\n" +
            "       q.name AS queue,\n" +
            "       tt.name AS tickettype,\n" +
            "       u.login AS user,\n" +
            "       tp.name AS priority,\n" +
            "       ts.name AS state,\n" +
            "       tl.name AS ticketlock,\n" +
            "       csc.name AS customer,\n" +
            "       cscs.name AS serviceid\n" +
            "FROM ticket t\n" +
            "INNER JOIN queue q ON t.queue_id = q.id\n" +
            "INNER JOIN ticket_type tt ON t.type_id = tt.id\n" +
            "INNER JOIN users u ON t.user_id = u.id\n" +
            "INNER JOIN ticket_priority tp ON t.ticket_priority_id = tp.id\n" +
            "INNER JOIN ticket_state ts ON t.ticket_state_id = ts.id\n" +
            "INNER JOIN ticket_lock_type tl ON t.ticket_lock_id = tl.id\n" +
            "LEFT JOIN (\n" +
            "    SELECT t.id AS cscid, s.name AS name, cc.name AS customer_id\n" +
            "    FROM ticket t\n" +
            "    INNER JOIN service s ON t.service_id = s.id\n" +
            "    INNER JOIN customer_company cc ON t.customer_id = cc.customer_id\n" +
            "    GROUP BY t.id\n" +
            ") csc ON t.id = csc.cscid\n" +
            "LEFT JOIN service cscs ON t.service_id = cscs.id\n" +
            "ORDER BY t.create_time DESC")
        List<NRMSTicket> findAllTickets();

    }
