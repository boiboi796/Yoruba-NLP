package nlp;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class data_cleaner {



    public static List<JSONObject> extractCleanJSON(String filePath, ArrayList<String> targetIds) throws IOException {
        List<JSONObject> extractedConfigs = new ArrayList<>();

        if (targetIds == null || targetIds.isEmpty()) return extractedConfigs;

        // 1. Clean the input brackets up front and store in a clean match list
        List<String> cleanTargetIds = new ArrayList<>();
        for (String id : targetIds) {
            if (id != null) {
                cleanTargetIds.add(id.replaceAll("[\\[\\]]", ""));
            }
        }

        // 2. Read the JSON file content once
        String jsonString = Files.readString(Path.of(filePath));
        JSONObject root = new JSONObject(jsonString);
        JSONArray typesArray = root.getJSONObject("sentence_type_pos_mapping").getJSONArray("types");

        // 3. Scan the JSON structures for matches
        for (int i = 0; i < typesArray.length(); i++) {
            JSONObject typeConfig = typesArray.getJSONObject(i);
            String currentId = typeConfig.getString("id");

            // If this JSON item matches any item in our lookup arraylist
            if (cleanTargetIds.contains(currentId)) {
                JSONObject cleanOutput = new JSONObject();
                cleanOutput.put("id", currentId);

                JSONObject originalPos = typeConfig.getJSONObject("pos");
                JSONObject filteredPos = new JSONObject();

                // 4. Dynamically loop through fields and drop "absent" values
                Iterator<String> keys = originalPos.keys();
                while (keys.hasNext()) {
                    String posKey = keys.next();
                    JSONObject posDetails = originalPos.getJSONObject(posKey);

                    if (!"absent".equals(posDetails.getString("role"))) {
                        filteredPos.put(posKey, posDetails);
                    }
                }

                cleanOutput.put("pos", filteredPos);
                extractedConfigs.add(cleanOutput);
            }
        }
        return extractedConfigs;
    }


    public static Map<String, Set<String>> cleanMyTexts(Object txtDataObj, Object csvDataObj) {
        Map<String, Set<String>> result = new HashMap<>();

        // 1. Process Text Data
        if (txtDataObj instanceof List) {
            for (Object row : (List<?>) txtDataObj) {
                processRow(row, result, true);
            }
        }

        // 2. Process CSV Data
        if (csvDataObj instanceof List) {
            for (Object row : (List<?>) csvDataObj) {
                processRow(row, result, false);
            }
        }

        return result;
    }

    private static void processRow(Object row, Map<String, Set<String>> result, boolean isTxtFormat) {
        switch (row) {
            case null -> {

            }
            case String[] arr -> {
                if (isTxtFormat && arr.length >= 3) {
                    String[] split = arr[2].split(" ");
                    if (split.length >= 2) {
                        result.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
                    }
                } else if (!isTxtFormat && arr.length > 6) {
                    result.computeIfAbsent(arr[3], k -> new HashSet<>()).add(arr[6]);
                }
            }
            // Route B: The row is a standard List
            case List list -> {
                if (isTxtFormat && list.size() >= 3 && list.get(2) instanceof String) {
                    String[] split = ((String) list.get(2)).split(" ");
                    if (split.length >= 2) {
                        result.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
                    }
                } else if (!isTxtFormat && list.size() > 6 && list.get(3) instanceof String && list.get(6) instanceof String) {
                    result.computeIfAbsent((String) list.get(3), k -> new HashSet<>()).add((String) list.get(6));
                }
            }
            default -> {
            }
        }

    }
}
