package com.bwd.nms.repository;
import com.bwd.nms.domain.NetworkState;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface NetworkStateRepository extends R2dbcRepository<NetworkState, Long> {

    @Query("SELECT id, updatedby, dateupdated FROM networkstate ORDER BY id DESC LIMIT 1")
    Mono<NetworkState> findNetworkState();

    @Query("SELECT * FROM networkstate WHERE id = :id")
    Mono<NetworkState> findByIdNetworkState(Long id);

}
