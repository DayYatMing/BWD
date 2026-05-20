package com.bwd.grafanaboot.persistence.repository;

import com.bwd.grafanaboot.persistence.DAO.TemsOCH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceSourcesRepository extends JpaRepository<TemsOCH, Long> {

    @Query( value = "SELECT DISTINCT conf.`sourcename` " +
            "FROM configuration conf " +
            "WHERE conf.`serviceid` = :service ",
            nativeQuery = true)
    List<String> findSources(String service);

}

