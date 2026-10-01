package com.bwd.chatbot.spellcheck;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class FuzzyMatching {

    public static void main(String[] args) {
        String input = "How are you today?";
        String target = "123456789012345?";

        // Create a LevenshteinDistance instance
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        // Calculate the Levenshtein distance between the input and target strings
        int distance = levenshtein.apply(input, target);

        // Define a threshold for similarity
        int threshold = 17;

        // Check if the input is similar to the target
        if (distance <= threshold) {
            System.out.println("The input is similar to the target.");
        } else {
            System.out.println("The input is not similar to the target.");
        }

        // Print the distance for reference
        System.out.println("Levenshtein distance: " + distance);
    }

}
