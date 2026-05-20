package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.Site;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class SiteMapper {

    public Site fromDocument(ODocument doc) {
        String id = doc.field("@rid", String.class);

        return new Site(
            id,
            doc.field("labelname", String.class),
            doc.field("latitude", Float.class),
            doc.field("longitude", Float.class),
            doc.field("comment", String.class),
            doc.field("streetnumber", String.class),
            doc.field("city", String.class),
            doc.field("zipcode", String.class),
            doc.field("buisneesowner", String.class),
            doc.field("leasedcompany", String.class)
        );
    }

    public Map<String, Object> getParams(Map<Object, Object> args, String username) {
        if (args.get("id") != null) return Map.of(
            "id",
            new ORecordId((String) args.get("id")),
            "name",
            args.get("name"),
            "labelname",
            args.get("labelname"),
            "updatedby",
            username,
            "dateupdated",
            args.get("dateupdated"),
            "comment",
            args.get("comment"),
            "latitude",
            args.get("latitude"),
            "longitude",
            args.get("longitude")
        );
        else return Map.of(
            "name",
            args.get("name"),
            "labelname",
            args.get("labelname"),
            "createdby",
            username,
            "datecreated",
            args.get("datecreated"),
            "comment",
            args.get("comment"),
            "latitude",
            args.get("latitude"),
            "longitude",
            args.get("longitude")
        );
    }
}
