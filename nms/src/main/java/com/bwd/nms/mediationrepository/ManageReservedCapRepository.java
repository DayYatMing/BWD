package com.bwd.nms.mediationrepository;
import com.bwd.nms.service.dto.ManageReservedCapDTO;
import com.bwd.nms.mediationdomain.ManageReservedCap;

import com.bwd.nms.service.dto.DlsCapacityDTO;
import com.bwd.nms.service.dto.ColHeadersDTO;
import com.bwd.nms.service.dto.CapacityRepLabelDTO;
import feign.Param;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface ManageReservedCapRepository extends R2dbcRepository<ManageReservedCap, Integer> {
    @Query(value = "select   rcc.id as id, rc.name as capacityName,s.name as segmentName,s.id as segmentid, d.id as dlsid, rc.id as capacityid,fp.id as fiberpairid,fp.name as fiberPair,d.name as dlsName ,c.name as customerName, rcc.total, csfdx.id as capSegFpDlsId \n" +
        " FROM pm_db.ref_segment s,pm_db.fiber_pair fp,pm_db.ref_capacity rc\n" +
        " JOIN pm_db.segment_fp_xrf sfx\n" +
        " LEFT JOIN pm_db.segment_fp_dls_xrf sfdx ON sfx.id = sfdx.segment_fp_id\n" +
        " LEFT JOIN pm_db.dls d ON sfdx.dls_id = d.id\n" +
        " LEFT JOIN pm_db.capacity_segment_fp_dls_xrf csfdx on csfdx.segment_fp_dls_id = sfdx.id\n" +
        " LEFT JOIN pm_db.reserved_capacity_calculation rcc on rcc.cap_seg_fp_dls_id = csfdx.id\n" +
        " LEFT JOIN pm_db.customer c on c.id = rcc.customer_id\n" +

        " where  sfx.segment_id = s.id and\n" +
        "        sfx.fibre_pair_id = fp.id and\n" +
        "        sfdx.segment_fp_id = sfx.id and\n" +
        "        sfdx.is_reserved = 1  AND\n" +
        "        rc.id = csfdx.capacity_id and rcc.id is not null \n" +

        " group by csfdx.capacity_id, csfdx.segment_fp_dls_id, rcc.id, rc.name, s.name, fp.name, d.name, c.name, rcc.total ")
    Flux<ManageReservedCapDTO> findAllData() ;

    @Query(value = "SELECT sfx.id AS sfxtblid,\n" +
        "       sfx.dls_id AS dlsid,\n" +
        "       sfx.is_reserved AS isreserved,\n" +
        "       sfx.segment_fp_id,\n" +
        "       c.id AS crossTableid,\n" +
        "       c.capacity_id AS capacity_id,\n" +
        "       (SELECT name FROM pm_db.dls d WHERE d.id = sfx.id) AS dlsname,\n" +
        "       (SELECT name FROM pm_db.ref_capacity WHERE id = c.capacity_id) AS capacity_name\n" +
        "FROM pm_db.segment_fp_dls_xrf sfx,\n" +
        "     pm_db.capacity_segment_fp_dls_xrf c\n" +
        "WHERE sfx.id = c.segment_fp_dls_id")
    Flux<DlsCapacityDTO> findDlsCapacityData();

    @Query(value = "SELECT c.id AS tbl_id, c.capacity_id,\n" +
        "       (SELECT name FROM pm_db.ref_capacity WHERE id = c.capacity_id) AS capacity_name,\n" +
        "       (SELECT name FROM pm_db.dls WHERE id = s.dls_id) AS dlsname,\n" +
        "       (SELECT name FROM pm_db.fiber_pair WHERE id = sf.fibre_pair_id) AS fibrepairname,\n" +
        "       (SELECT name FROM pm_db.ref_segment WHERE id = sf.segment_id) AS segmentname\n" +
        "FROM pm_db.capacity_segment_fp_dls_xrf c,\n" +
        "     pm_db.segment_fp_dls_xrf s,\n" +
        "     pm_db.segment_fp_xrf sf\n" +
        "WHERE s.id = c.segment_fp_dls_id\n" +
        "  AND sf.id = s.segment_fp_id\n" +
        "  AND s.is_reserved = 1")
    Flux<ColHeadersDTO> findColHeadersData();

    @Query(value = "SELECT sf.id, s.segment_id, s.fibre_pair_id,\n" +
        "           (SELECT name FROM pm_db.ref_segment WHERE id = s.segment_id) AS segment_name,\n" +
        "           (SELECT name FROM pm_db.fiber_pair WHERE id = s.fibre_pair_id) AS fiber_pair_name,\n" +
        "           (SELECT name FROM pm_db.dls WHERE id = sf.dls_id) AS dlsname\n" +
        "    FROM pm_db.segment_fp_xrf s,\n" +
        "         pm_db.segment_fp_dls_xrf sf\n" +
        "    WHERE sf.segment_fp_id = s.id")
    Flux<CapacityRepLabelDTO> findCapacityRepLabelData();


}






