package com.bwd.grafanaboot.persistence.repository;

import com.bwd.grafanaboot.persistence.DAO.TemsOCH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TemsOCHRepository extends JpaRepository<TemsOCH, Long> {

    @Query( value = "SELECT tems.`Date/Time`, tems.`PM Source`, " +
            "tems.`Link Fail Seconds - In`, tems.`Link Fail Seconds - Out`, " +
            "tems.`Physical Error Count - In`, tems.`Physical Error Count - Out`, " +
            "tems.`Frame Check Sequence Error Count - In`, tems.`Frame Check Sequence Error Count - Out`, " +
            "tems.`Number of Seconds in Bin - Tx Line Card`, tems.`Number of Seconds in Bin - Rx Line Card`, " +
            "tems.`Errored Seconds - In`, tems.`Errored Seconds - Out`, " +
            "tems.`Severely Errored Seconds - In`, tems.`Severely Errored Seconds - Out` " +
            "FROM tems_och tems " +
            "WHERE tems.`Date/Time` BETWEEN :from AND :to " +
            "AND tems.`customer_sid` = :serviceId " +
            "AND tems.`PM Source` IN (:pmSource) ",
            nativeQuery = true)
    List<Object[]> findResults(String serviceId, List<String> pmSource, LocalDateTime from, LocalDateTime to);

}

