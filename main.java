package nlp;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static nlp.data_cleaner.cleanMyTexts;
import static nlp.data_cleaner.extractCleanJSON;
import static nlp.validator.determineSentenceType;
import static nlp.sentenceEvaluator.evaluateSentence;

public class main {
    public static void main(String[] args) throws Exception {
        System.out.print("\u001B[34m------------------------------------------------\n\u001B[0m");
        System.out.print("\u001B[34m------------------------------------------------\n\u001B[0m");
        System.out.print("\u001B[34m Enter a sentence (Ko ede Yoruba ti o ba fe):  \u001B[0m");
        Scanner input = new Scanner(System.in);
        String inputStream = input.nextLine();

        Tokenize tokenizer = new Tokenize();
        String[] splitted = tokenizer.tokenizeToWord(inputStream);

        Lematizer lematizer = new Lematizer();
        ArrayList<ArrayList> lematized = lematizer.lematize(splitted);
        System.out.printf("\u001B[33m output: %s \n\u001B[0m",lematized);


        ArrayList<String> sentencePrediction = determineSentenceType(lematized);
        System.out.printf("\u001B[33mSentence type: %s \n\u001B[0m",sentencePrediction);

        ArrayList<Map<String, Set<String>>> gottenData= new ArrayList<>();

        for (int i = 0; i < lematized.toArray().length; i++) {
            ArrayList current = lematized.get(i);
            for (int j = 0; j <current.toArray().length ; j++) {
                String searchWord = current.get(j).toString();
                System.out.println("Searching for: \"" + searchWord + "\"\n");
                ArrayList firstsearchInCSV = searchInCSV("C:\\Users\\HomePC\\Documents\\NLP-YORUBA\\Yoruba-NLP\\data\\yoruba_10k_dataset.csv", searchWord, "CSV1");
                ArrayList secondsearchInCSV =searchInCSV("C:\\Users\\HomePC\\Documents\\NLP-YORUBA\\Yoruba-NLP\\data\\yoruba_10k_final.csv", searchWord, "CSV2");
                ArrayList firstsearchInTXT =searchInTextFile("C:\\Users\\HomePC\\Documents\\NLP-YORUBA\\Yoruba-NLP\\data\\test_plain.txt", searchWord, "TXT1");
                ArrayList secondsearchInTXT =searchInTextFile("C:\\Users\\HomePC\\Documents\\NLP-YORUBA\\Yoruba-NLP\\data\\train_plain.txt", searchWord, "TXT2");


                System.out.println(Arrays.deepToString(firstsearchInCSV.toArray()));
                System.out.println(Arrays.deepToString(secondsearchInCSV.toArray()));
                System.out.println(Arrays.deepToString(firstsearchInTXT.toArray()));
                System.out.println(Arrays.deepToString(secondsearchInTXT.toArray()));

                Map<String, Set<String>> result = cleanMyTexts(firstsearchInTXT, firstsearchInCSV);
                gottenData.add(result);


            }
        }
        List<JSONObject> cleanpredictions = extractCleanJSON("C:\\Users\\HomePC\\Documents\\NLP-YORUBA\\Yoruba-NLP\\sentence_type_pos_mapping.json",sentencePrediction);

        System.out.println(cleanpredictions);
        System.out.println(gottenData);

        List<JSONObject> list =cleanpredictions;

        JSONArray jsonArray = new JSONArray(list);
        List<Object> parsedResult = evaluateSentence(jsonArray,gottenData);

        System.out.println(parsedResult);

        String blue = "\u001B[34m";
        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        String[] predictions = sentencePrediction.toString().split(", ");
        String[] expectedPos = parsedResult.get(3).toString().split(", ");

        String missing = parsedResult.get(2).toString().equalsIgnoreCase("[]") ? "NULL" : parsedResult.get(2).toString();

        String gridFormat = "%-20s %-25s %-20s %-20s %-20s %s\n";

        System.out.println(blue + "------------------------------------------------------------------------------------------------------------------------------------------" + reset);
        System.out.printf(green + gridFormat + reset, "Tokens", "sentencePrediction", "AccuracyPercentage", "MissingRequired", "isSentenceValid", "Expected_POS");

        int totalLines = Math.max(predictions.length, expectedPos.length);
        for (int i = 0; i < totalLines; i++) {
            if (i == 0) {
                System.out.printf(gridFormat,
                        Arrays.toString(splitted), predictions[i], String.format("%.2f%%", parsedResult.get(1)), missing, parsedResult.get(0), expectedPos[i]);
            } else {
                String predLine = (i < predictions.length) ? predictions[i] : "";
                String expLine = (i < expectedPos.length) ? expectedPos[i] : "";
                System.out.printf(gridFormat, "", predLine, "", "", "", expLine);
            }
        }
        System.out.println(blue + "------------------------------------------------------------------------------------------------------------------------------------------" + reset);
    }



    public static ArrayList searchInTextFile(String filePath, String word, String label) throws Exception {
        ArrayList<String[]> results = new ArrayList();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Check each word in the line for exact match
                String[] words = line.split("\\s+"); // split by spaces
                for (String w : words) {
                    if (w.trim().equalsIgnoreCase(word)) {
                        results.add(new String[] {label, Integer.toString(lineNumber) , line});
                        break;
                    }
                }
            }
        }
        return results;
    }


   public static ArrayList searchInCSV(String filePath, String word, String label) throws Exception {
        ArrayList<String[]> results = new ArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                for (String column : columns) {
                    if (column.trim().equalsIgnoreCase(word)) {
                         results.add(new String[] {label, Integer.toString(lineNumber) , line});
                        break;
                    }
                }
            }
        }

        return results;
    }
}
