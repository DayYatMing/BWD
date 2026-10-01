package com.bwd.chatbot.nlp;

import com.bwd.chatbot.ResponseGenerator;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NaturalLangProcessor {

    private StanfordCoreNLP pipeline;
    private IntentClassifier intentClassifier;
    private ResponseGenerator responseGenerator;

    public NaturalLangProcessor(){
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        pipeline = new StanfordCoreNLP(props);

        intentClassifier = new IntentClassifier();
        responseGenerator = new ResponseGenerator();
    }

    public String processUserInput(String userInput) {
        CoreDocument document = new CoreDocument(userInput);
        pipeline.annotate(document);

        List<String> entities = new ArrayList<>();
        for (CoreLabel token : document.tokens()) {
            if (!token.ner().equals("O")) {
                System.out.println(token.word() + " : " + token.ner());
                entities.add(token.word());
            }
        }

        String intent = intentClassifier.classify(userInput);

        return responseGenerator.generateResponse(intent, entities);
    }
}
