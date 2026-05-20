package com.bwd.nms.repository;
import com.bwd.nms.domain.Notification;
import com.bwd.nms.domain.Timeline;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface TimelineRepository extends R2dbcRepository<Timeline, Long> {

    @Query("""
    SELECT tl.object AS feature,
           tl.operation AS operation,
           COALESCE(ju.login, 'unknown') AS login,
           tl.old_value AS oldvalue,
           tl.new_value AS newvalue,
           tl.event_date AS eventdate
    FROM timeline tl
    LEFT JOIN jhi_user ju
    ON tl.user_id = ju.id
    ORDER BY tl.event_date DESC LIMIT 100
    """)
    Flux<Notification> findAllByEventDate();


    @Query("SELECT login FROM jhi_user WHERE id = :userId")
    Mono<String> findUserByIdCustom(Long userId);

    @Query("SELECT id FROM jhi_user WHERE login = :login")
    Mono<Long> findIdByLogin(String login);

}
