package nlp;
import java.util.ArrayList;
public class Lematizer {

    public ArrayList<ArrayList> lematize(String[] tokenizedSentence) {
        String[] vowels = {"a", "e", "i", "o", "u"};
        String[] consonants = {"b","d","f","g","gb","h","j","k","l","m","n","p","r","s","t","w","y"};
        ArrayList<ArrayList> refinedList = new ArrayList<>();
        int len = tokenizedSentence.length;

        for (String word : tokenizedSentence) {
            boolean present = false;

            if (word.length() < 3) {
                ArrayList<String> refined = new ArrayList<>();
                refined.add(word);
                refinedList.add(refined);
                continue;
            }

            for (String currentVowel : vowels) {


                if (word.length() >= 2 && currentVowel.equals(word.substring(1, 2))) {
                    if (word.startsWith("l")) {
                        ArrayList<String> refined = new ArrayList<>();
                        String newWord = word.substring(2);
                        refined.add("ni");
                        refined.add(currentVowel + newWord);
                        refinedList.add(refined);
                        present = true;
                        break;
                    }
                }


                else if (word.length() >= 3
                        && currentVowel.equals(word.substring(0, 1))
                        && currentVowel.equals(word.substring(2, 3))) {
                    if (word.charAt(1) == 'l') {
                        ArrayList<String> refined = new ArrayList<>();
                        String newWord = word.substring(3);
                        refined.add("oni");
                        refined.add(currentVowel + newWord);
                        refinedList.add(refined);
                        present = true;
                        break;
                    }
                }

                else if (word.length() % 2 == 1) {
                    String newWord = word.substring(0, word.length() / 2);
                    if (newWord.equals(word.substring((word.length() / 2) + 1))) {
                        ArrayList<String> refined = new ArrayList<>();
                        refined.add(newWord);
                        refined.add("oni");
                        refined.add(newWord);
                        refinedList.add(refined);
                        present = true;
                        break;
                    }
                }

                else if (word.startsWith("a") && word.length() > 3) {
                    if (word.substring(1).startsWith(currentVowel)) {
                        ArrayList<String> refined = new ArrayList<>();
                        refined.add("a");
                        refined.add(word.substring(1));
                        refinedList.add(refined);
                        present = true;
                        break;
                    }
                }
            }


            if (!present) {
                ArrayList<String> refinedy = new ArrayList<>();
                refinedy.add(word);
                refinedList.add(refinedy);
            }
        }

        return refinedList;
    }
    public static void main (String[] args){
        String[] neWord = {"lomo","alamala","we",};
        Lematizer Lemat = new Lematizer();
        ArrayList<ArrayList> result = Lemat.lematize(neWord);
        System.out.println(result);

    }
}