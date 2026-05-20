package com.bwd.brokerotrs.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtrsService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String getUrlParams(String cusID, String stateType) {
        return "&AllArticles=1&Limit=500&TicketCreateTimeNewerDate=" +
                URLEncoder.encode(getDateFormat(LocalDateTime.now().minusYears(1)), StandardCharsets.UTF_8) +
                "&CustomerID=" + cusID + "&StateType=" + stateType;
    }

    public String getTicketArticle(){
        return "&AllArticles=1";
    }

    public String getDomainURN(Environment env){
        return env.getProperty("otrs.host") +
                env.getProperty("otrs.urn");
    }

    public String extractTicket(String response) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response);
            JsonNode ticketArray = root.path("Ticket");

            if (ticketArray.isArray() && ticketArray.size() > 0) {
                JsonNode firstTicket = ticketArray.get(0);
                return mapper.writeValueAsString(firstTicket);
            }

            return "{}";

        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public String getDetails(String response) {

        StringBuilder updatedUrl = new StringBuilder();
        updatedUrl.append("/");

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<String>> json = mapper.readValue(response, Map.class);

            for (List<String> dataCopy : json.values()) {
                for (String value : dataCopy) {
                    updatedUrl.append(value).append(",");
                }
            }

            if (!updatedUrl.isEmpty() && updatedUrl.charAt(updatedUrl.length() - 1) == ',') {
                updatedUrl.deleteCharAt(updatedUrl.length() - 1);
            }
            return updatedUrl.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateFormat(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    public Map<String, Object> createDynamicTicketPayload(
            String customer,
            String lastRaiseTime,
            String serviceId,
            String type,
            String clearedTime,
            boolean isCleared) {

        String title = "Network Incident Notification " + lastRaiseTime + " UTC | " + serviceId;

        String bodyText;

        if (isCleared) {
            bodyText =
                    "Dear " + customer + " NOC,\n\n"
                            + "This is a courtesy email to notify you that we have seen alarms incoming to the client ports of our equipment on "
                            + lastRaiseTime + " UTC for the service " + serviceId + ". Alarms cleared on "
                            + clearedTime + " UTC.\n\n"
                            + "Please advise if this event was due to any maintenance works or testing on your side.\n\n"
                            + "Best regards,\nHAWAIKI NOC";
        } else {
            bodyText =
                    "Dear " + customer + " NOC,\n\n"
                            + "This is a courtesy notification that we have observed alarms incoming on the client port at "
                            + lastRaiseTime + " UTC for the service " + serviceId + ".\n\n"
                            + "Please advise if this event was due to any maintenance or testing activities on your side.\n\n"
                            + "Best regards,\nHAWAIKI NOC";
        }

        Map<String, Object> article = new HashMap<>();
        article.put("Subject", title);
        article.put("Body", bodyText);
        article.put("ContentType", "text/plain; charset=utf8");

        Map<String, Object> ticket = new HashMap<>();
        ticket.put("Title", title);
        ticket.put("Queue", "NOC Incidents");
        ticket.put("State", "new");
        ticket.put("Priority", "3 normal");
        ticket.put("CustomerUser", customer);
        ticket.put("Type", type);

        Map<String, Object> payload = new HashMap<>();
        payload.put("Ticket", ticket);
        payload.put("Article", article);

        return payload;
    }

    public String toJson(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
