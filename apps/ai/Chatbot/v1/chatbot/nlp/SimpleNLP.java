package com.bwd.chatbot.nlp;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class SimpleNLP {

    public static void main(String[] args) {
        // Set up pipeline properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");

        // Build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // User input text
        String text = "I want to book a flight to New York next week.";

        // Create a document object
        CoreDocument document = new CoreDocument(text);

        // Annotate the document
        pipeline.annotate(document);

        // Extract entities
        for (CoreLabel token : document.tokens()) {
            String word = token.word();
            String ner = token.ner();
            System.out.println("Word: " + word + ", NER: " + ner);
        }
    }

}
