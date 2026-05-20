package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.CapPlanningData;
import com.bwd.nms.orientdbdomain.Site;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class CapPlanningMapper {

    public CapPlanningData fromDocument(ODocument doc) {
        String id = doc.field("@rid", String.class);

        return new CapPlanningData(
            id,
            doc.field("segmentnm", String.class),
            doc.field("site", String.class),
            Objects.requireNonNullElse(doc.field("capacity", Integer.class), 0),
            doc.field("portstatus", String.class),
            doc.field("cardtype", String.class),
            doc.field("networktype", String.class),
            doc.field("frequency", String.class),
            doc.field("route", String.class),
            doc.field("vendor", String.class),
            doc.field("servicename", String.class),
            doc.field("customername", String.class),
            doc.field("name", String.class),
            doc.field("lsiodf", String.class)

        );
    }

}


