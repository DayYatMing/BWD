package com.bwd.grafanaboot.persistence.repository;

import com.bwd.grafanaboot.persistence.DAO.TemsOCH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerServicesRepository extends JpaRepository<TemsOCH, Long> {

    @Query( value = "SELECT serv.`serviceid` " +
            "FROM service serv " +
            "WHERE serv.`serviceid` LIKE :customerId AND serv.`active` = 1 AND serv.`visible_to_customer` = 1",
            nativeQuery = true)
    List<String> findServices(String customerId);

}

