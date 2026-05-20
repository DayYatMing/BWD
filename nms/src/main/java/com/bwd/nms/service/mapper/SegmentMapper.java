package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbdomain.Site;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class SegmentMapper {

    public Segment fromResult(OResult r) {
        String id = r.getIdentity()
            .map(Object::toString)
            .orElseGet(() -> {
                Object rid = r.getProperty("@rid");
                return rid != null ? rid.toString() : null;
            });

        Function<String, Float> safeFloat = field -> {
            Object val = r.getProperty(field);
            if (val == null) return null;

            if (val instanceof List) {
                val = ((List<?>) val).stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
                if (val == null) return null;
            }

            if (val instanceof Number) {
                return Float.valueOf(((Number) val).floatValue());
            }

            try {
                return Float.valueOf(Float.parseFloat(val.toString()));
            } catch (NumberFormatException e) {
                return null;
            }
        };

        return new Segment(
            id,
            r.getProperty("name"),
            r.getProperty("comment"),
            r.getProperty("labelname"),
            r.getProperty("dls"),
            r.getProperty("aend"),
            r.getProperty("bend"),
            r.getProperty("aendfiber"),
            r.getProperty("bendfiber"),
            r.getProperty("directionorder"),
            r.getProperty("createdby"),
            r.getProperty("updatedby"),
            r.getProperty("datecreated"),
            r.getProperty("dateupdated"),
            r.getProperty("networktype"),
            safeFloat.apply("lat"),
            safeFloat.apply("long")
        );
    }

    public Map<String, Object> getParams(Map<Object, Object> args, String username) {
        Map<String, Object> params = new HashMap<>();

        if (args == null) {
            return params;
        }

        args.forEach((key, value) -> {
            if (key == null) return;

            String field = key.toString();

            // Convert record id strings to ORecordId if needed
            if (value instanceof String && ((String) value).startsWith("#")) {
                try {
                    value = new ORecordId((String) value);
                } catch (Exception ignored) {
                }
            }

            params.put(field, value);
        });

        // Audit fields handling
        if (params.containsKey("dateupdated")) {
            params.put("updatedby", username);
        } else if (params.containsKey("datecreated")) {
            params.put("createdby", username);
        }

        return params;
    }
}
