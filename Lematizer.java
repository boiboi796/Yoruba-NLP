package nlp;
import java.util.ArrayList;
public class Lematizer {

    public ArrayList<ArrayList> lematize(String[] tokenizedSentence) {
        String[] vowels = {"a", "e", "i", "o", "u"};
        String[] consonants = {"b","d","f","g","gb","h","j","k","l","m","n","p","r","s","t","w","y"};
        ArrayList<ArrayList> refinedList = new ArrayList<>();
        for (String word : tokenizedSentence) {

            for (String currentVowel : vowels) {
                ArrayList<String> refined = new ArrayList<>();

                if (word.length() < 3) {
                    refined.add(word);
                    refinedList.add(refined);

                    break;
                }
                if (currentVowel.equals(word.substring(1, 2))) {
                    if (word.startsWith("l")) {
                        String newWord = word.substring(2);
                        refined.add("ni");
                        refined.add(currentVowel + newWord);
                        refinedList.add(refined);

                        break;
                    }
                }
                else if (currentVowel.equals(word.substring(0, 1))&&currentVowel.equals(word.substring(2, 3))) {
                    if (word.charAt(1) == 'l') {
                        String newWord = word.substring(3);
                        refined.add("oni");
                        refined.add(currentVowel + newWord);
                        refinedList.add(refined);

                        break;
                    }
                }
                else if (word.length()%2==1) {
                    if (word.substring(0, (word.length() / 2)).equals(word.substring((word.length() / 2) + 1))){
                        String newWord = word.substring(0,word.length()/2);
                        refined.add(newWord);
                        refined.add("oni");
                        refined.add(newWord);
                        refinedList.add(refined);

                        break;
                    }
                }
                else if (word.startsWith("a") && word.length() > 3) {
                    if (word.substring(1).startsWith(currentVowel)) {
                        refined.add("a");
                        refined.add(word.substring(1));
                        refinedList.add(refined);

                        break;
                    }
                }
                else if (word.endsWith("i") && word.length() > 3) {
                    refined.add(word.substring(0, word.length() - 1));
                    refined.add("i");
                    refinedList.add(refined);

                    break;
                }

            }

        }
        return refinedList;



    }
    public static void main (String[] args){
        String[] neWord = {"lomo","alamala","omolomo","ayalaya","lokan","oluwole","ekalade","alakata",
                "alokokin","elewe","alabere","alajide","osimimi","okinni","elerun","alakali","awolepa","akanji",
                "akasimi","awolusi","ayeyemi","alaburo","alabi","alabosi","alaro","alaro","aleyi","alimosho","aliye",
                "aliade","alidano","alidari","alilere","alimawo","alimomo","alinsomo","alinowo","aliroke","aliroto",
                "aliroti","aliroku","aliroza","alirzau","alirzi","alirzo","alirzu","alisa","alisade","alisawo","alisaye",
                "alise","aliseke","aliselu","alisemi","alisher","alishey","alisi","alisimi","aliso","alisoki","alison",
                "alisove","alisu","alita","alitade","alitayi","alite","aliteke","alitelu","alitemi","alither","alithey",
                "aliti","alitimi","alitiso","alitoki","aliton","alitove","alitru","alitsa","alitsde","alitsyi","alitue","alivade","alivane","alivatu","alivaye","alivde","aliveke","alivelu","alivemi","alivher","alivhey","alivi","alivimi","alivo","alivoki","alivon","alivove","alivru","alivsa","alivsde","alivye"};
        Lematizer Lemat = new Lematizer();
        ArrayList<ArrayList> result = Lemat.lematize(neWord);
        System.out.println(result);

    }
}