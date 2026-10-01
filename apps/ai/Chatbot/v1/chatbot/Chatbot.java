package com.bwd.chatbot;

import com.bwd.chatbot.db.DBProcessor;
import com.bwd.chatbot.nlp.NaturalLangProcessor;
import com.bwd.chatbot.spellcheck.SpellChecker;

import java.io.IOException;

public class Chatbot {

    public Chatbot(){}

    public static void main(String[] args) throws IOException {
        NaturalLangProcessor cbot = new NaturalLangProcessor();
//        String userInput = "I want to book a flight to Auckland next week eight am.";
        String userInput = "Hallo, how are you today?";
        SpellChecker spellChecker = new SpellChecker();
        System.out.println("=============================================================");
        System.out.println(cbot.processUserInput(spellChecker.getCorrectedText(userInput)));
        System.out.println("=============================================================");

    }

}
