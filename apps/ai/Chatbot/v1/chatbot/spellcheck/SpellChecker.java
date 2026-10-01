package com.bwd.chatbot.spellcheck;

import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;

public class SpellChecker {
    public String getCorrectedText(String text) throws IOException {

        JLanguageTool langTool = new JLanguageTool(Languages.getLanguageForShortCode("en-GB"));
        try {
            List<RuleMatch> matches = langTool.check(text);

            StringBuilder correctedText = new StringBuilder(text);

            for (int i = matches.size() - 1; i >= 0; i--) {
                RuleMatch match = matches.get(i);
                List<String> suggestions = match.getSuggestedReplacements();
                if (!suggestions.isEmpty()) {
                    // always getting the first suggestion
                    correctedText.replace(match.getFromPos(), match.getToPos(), suggestions.get(0));
                }
            }

            System.out.println("Corrected text: " + correctedText);
            return correctedText.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }

    }
}
