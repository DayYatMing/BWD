package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.RoomLocation;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomLocationMapper {

    public RoomLocation fromResult(OResult r) {
        String id = r.getIdentity()
            .map(Object::toString)
            .orElseGet(() -> {
                Object rid = r.getProperty("@rid");
                return rid != null ? rid.toString() : null;
            });

        Function<String, String> safeString = field -> {
            Object val = r.getProperty(field);
            if (val == null) return null;
            if (val instanceof List) {
                List<?> list = (List<?>) val;
                return list.isEmpty() ? null : list.get(0).toString();
            } else {
                return val.toString();
            }
        };

        return new RoomLocation(
            id,
            safeString.apply("site"),
            safeString.apply("labelname"),
            safeString.apply("comment")
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
