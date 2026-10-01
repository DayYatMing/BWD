package com.bwd.chatbot.nlp;

import com.bwd.chatbot.db.DBProcessor;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class IntentClassifier {

    public String classify(String text) {

        DBProcessor dbp = new DBProcessor();
        String query = "SELECT input_text FROM nrmsv2.cbot_input WHERE intent_id = 1";
        String[] greetings = dbp.getGreetings(query);

        String lowerCaseText = text.toLowerCase();
        int threshold = 2;
        if (isGreeting(lowerCaseText, greetings)) {
            return "Greeting";
        }else if (isSimilarGreeting(lowerCaseText, greetings, threshold)){
            return "Greeting";
        }

        if (isCustomer(lowerCaseText)){
            return "GetCustomer";
        }

        return "Unknown";
    }

    private static boolean isCustomer(String input){
        DBProcessor dbp = new DBProcessor();
        String query = "SELECT input_text FROM nrmsv2.cbot_input WHERE intent_id = 2";
        String[] customers = dbp.getCustomerIntent(query);
        for(String customer : customers){
            if(input.contains(customer)){
                return true;
            }
        }
        return false;
    }

    private static boolean isGreeting(String input, String[] greetings){
        for(String greeting : greetings){
            if(input.contains(greeting)){
                return true;
            }
        }
        return false;
    }

    private static boolean isSimilarGreeting(String input, String[] greetings, int threshold) {
        LevenshteinDistance levenshtein = new LevenshteinDistance();
        for(String greeting : greetings){
            int distance = levenshtein.apply(input, greeting);
            if(distance <= threshold){
                return true;
            }
        }
        return false;
    }

}
