package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.Port;
import com.bwd.nms.orientdbdomain.enumeration.CardType;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PortMapper {

    public Port fromResult(OResult r) {
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
                return ((List<?>) val).stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(Object::toString)
                    .orElse(null);
            }

            return val.toString();
        };

        Function<String, Integer> safeInteger = field -> {
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
                return Integer.valueOf(((Number) val).intValue());
            }

            try {
                return Integer.valueOf(Integer.parseInt(val.toString()));
            } catch (NumberFormatException e) {
                return null;
            }
        };

        return new Port(
            id,
            safeString.apply("name"),
            safeString.apply("odf"),
            safeString.apply("site"),
            safeString.apply("roomlocation"),
            safeString.apply("cardtype"),
            safeString.apply("shelf"),
            safeString.apply("slot"),
            safeString.apply("position"),
            safeString.apply("labelname"),
            safeString.apply("direction"),
            safeString.apply("connector"),
            safeInteger.apply("capacity"),
            safeString.apply("thirdparty"),
            safeString.apply("comment"),
            safeString.apply("frequency"),
            safeString.apply("wavelength"),
            safeString.apply("portstatus"),
            safeString.apply("serviceid")
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
