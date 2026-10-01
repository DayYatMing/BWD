package com.bwd.chatbot;

import com.bwd.chatbot.intent.CustomerIntent;
import com.bwd.chatbot.intent.GreetingIntent;

import java.util.List;

public class ResponseGenerator {

    public String generateResponse(String intent, List<String> entities) {

        for(String entity : entities){
            System.out.println(entity);
        }

        switch (intent) {
            case "Greeting":
                GreetingIntent gi = new GreetingIntent();
                return gi.getGreeting();
            case "GetCustomer":
                CustomerIntent ci = new CustomerIntent();
                return ci.getCustomer();
            default:
                return "I'm sorry, I didn't understand that. Can you please rephrase?";
        }
    }

}
