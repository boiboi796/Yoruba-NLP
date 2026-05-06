package nlp;
import java.util.ArrayList;
public class Lematizer {

    public ArrayList<String> lematize(String[] tokenizedSentence) {
        String[] vowels = {"a", "e", "i", "o", "u"};
        String[] consonants = {"b","d","f","g","gb","h","j","k","l","m","n","p","r","s","t","w","y"};
        ArrayList<String> refined = new ArrayList<>();
        for (String word : tokenizedSentence) {
            for (String currentVowel : vowels) {
                if (currentVowel.equals(word.substring(1, 2))) {
                    if (word.startsWith("l")) {
                        String newWord = word.substring(2);
                        refined.add("ni");
                        refined.add(currentVowel + newWord);
                        break;
                    }
                }
                else if (currentVowel.equals(word.substring(0, 1))&&currentVowel.equals(word.substring(2, 3))) {
                    if (word.charAt(1) == 'l') {
                        String newWord = word.substring(3);
                        refined.add("oni");
                        refined.add(currentVowel + newWord);
                        break;
                    }
                }
                else if (word.length()%2==1) {
                    if (word.substring(0, (word.length() / 2)).equals(word.substring((word.length() / 2) + 1))){
                        String newWord = word.substring(0,word.length()/2);
                        refined.add(newWord);
                        refined.add("oni");
                        refined.add(newWord);
                        break;
                    }
                }

            }

        }
        return refined;



    }
    public static void main (String[] args){
        String[] neWord = {"lomo","alamala","omolomo","ayalaya"};
        Lematizer Lemat = new Lematizer();
        ArrayList<String> result = Lemat.lematize(neWord);
        System.out.println(result);

    }
}