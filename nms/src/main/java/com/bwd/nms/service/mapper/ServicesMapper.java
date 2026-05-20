package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.Segment;
import com.bwd.nms.orientdbdomain.Services;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class ServicesMapper {

    public Services fromResult(OResult r) {
        String id = r.getIdentity()
            .map(Object::toString)
            .orElseGet(() -> {
                Object rid = r.getProperty("@rid");
                return rid != null ? rid.toString() : null;
            });


        Function<String, Integer> safeInteger = field -> {
            Object val = r.getProperty(field);
            if (val == null) return null;
            if (val instanceof List) {
                List<?> list = (List<?>) val;
                if (list.isEmpty()) return null;
                val = list.get(0);
            }
            if (val instanceof Number) return (Integer) ((Number) val).intValue();
            try {
                return (Integer) Integer.parseInt(val.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        };

        return new Services(
            id,
            r.getProperty("serviceName"),
            r.getProperty("labelname"),
            r.getProperty("comment"),
            r.getProperty("servicestatus"),
            r.getProperty("servicecapacity"),
            r.getProperty("serviceimage"),
            r.getProperty("servicedata"),
            r.getProperty("servicedataContentType"),
            r.getProperty("customername"),
            Optional.ofNullable(safeInteger.apply("max")).orElse(0),
            r.getProperty("portid"),
            r.getProperty("internal")
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
