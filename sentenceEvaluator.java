package nlp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class sentenceEvaluator {

    /**
     * Evaluates a sentence structure against a JSON ruleset.
     * * @return List containing:
     * [0] Boolean isCorrect,
     * [1] Double accuracyPercent,
     * [2] List<String> missingRequirements,
     * [3] Map<String, List<String>> tokenExpectedPosMap
     */
    public static List<Object> evaluateSentence(JSONArray rulesArray, List<Map<String, Set<String>>> tokenSequence) {
        List<Object> evaluationResults = new ArrayList<>();

        // Handle edge cases safely
        if (rulesArray == null || rulesArray.isEmpty() || tokenSequence == null || tokenSequence.isEmpty()) {
            evaluationResults.add(false);
            evaluationResults.add(0.0);
            evaluationResults.add(Collections.emptyList());
            evaluationResults.add(new HashMap<String, List<String>>());
            return evaluationResults;
        }

        // 1. Extract rule definitions from the JSONArray
        JSONObject targetRule = rulesArray.getJSONObject(0);
        JSONObject posRules = targetRule.getJSONObject("pos");

        double totalTokens = tokenSequence.size();
        double correctTokens = 0;

        Set<String> requiredRoles = new HashSet<>();
        Set<String> observedRoles = new HashSet<>();

        // This will map each word to its list of expected/allowed POS tags from the config
        Map<String, List<String>> tokenExpectedPosMap = new LinkedHashMap<>();

        // Identify all required roles from the schema rules up front
        for (String key : posRules.keySet()) {
            if ("required".equals(posRules.getJSONObject(key).getString("role"))) {
                requiredRoles.add(key);
            }
        }

        // 2. Loop through your sequential token lists
        for (Map<String, Set<String>> tokenMap : tokenSequence) {
            if (tokenMap == null || tokenMap.isEmpty()) continue;

            String word = tokenMap.keySet().iterator().next();
            Set<String> possibleTags = tokenMap.get(word);

            boolean hasValidMatch = false;
            List<String> expectedTagsForThisToken = new ArrayList<>();

            // Cross-reference the token's possible tags against the JSON configuration rules
            for (String tag : possibleTags) {
                if (posRules.has(tag)) {
                    hasValidMatch = true;
                    expectedTagsForThisToken.add(tag); // It's a valid, expected tag for this word position!

                    if (requiredRoles.contains(tag)) {
                        observedRoles.add(tag);
                    }
                }
            }

            // Populate our breakdown map for this word
            tokenExpectedPosMap.put(word, expectedTagsForThisToken);

            if (hasValidMatch) {
                correctTokens++;
            }
        }

        // 3. Compute accuracy and track missing structural layout elements
        double accuracyPercentage = (correctTokens / totalTokens) * 100.0;

        Set<String> missingRequired = new HashSet<>(requiredRoles);
        missingRequired.removeAll(observedRoles);

        // A sentence is only 100% correct if all tokens are valid AND no required roles are missing
        boolean isSentenceCorrect = (accuracyPercentage == 100.0) && missingRequired.isEmpty();

        // 4. Wrap everything up neatly into the returned list
        evaluationResults.add(isSentenceCorrect);
        evaluationResults.add(accuracyPercentage);
        evaluationResults.add(new ArrayList<>(missingRequired));
        evaluationResults.add(tokenExpectedPosMap);

        return evaluationResults;
    }
}
