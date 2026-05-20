package com.bwd.nms.service.mapper;

import com.bwd.nms.orientdbdomain.Card;
import com.bwd.nms.orientdbdomain.enumeration.CardType;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.sql.executor.OResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CardMapper {

    public Card fromResult(OResult r) {
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

        Function<String, CardType> safeCardType = field -> {
            Object val = r.getProperty(field);
            if (val == null) return CardType.UNKNOWN;

            if (val instanceof List) {
                List<?> list = (List<?>) val;
                if (list.isEmpty()) return CardType.UNKNOWN;
                return CardType.fromString(list.get(0).toString());
            }

            return CardType.fromString(val.toString());
        };

        return new Card(
            id,
            safeString.apply("site"),
            safeString.apply("roomlocation"),
            safeCardType.apply("cardtype"),
            safeString.apply("cardlabelname"),
            safeString.apply("shelf"),
            safeString.apply("slot"),
            safeString.apply("position"),
            safeString.apply("dls"),
            safeInteger.apply("capacity"),
            safeInteger.apply("totalports"),
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
