package nlp;
import java.util.*;


public class validator {
    public static ArrayList<String> determineSentenceType(ArrayList lemmatizedSentence){
        ArrayList<String> sentenceTypePredict = new ArrayList<>();
        if (lemmatizedSentence.toArray().length>1&&lemmatizedSentence.toArray().length<6)
            sentenceTypePredict.add("simple_sentence");
        else if (lemmatizedSentence.toArray().length>3 && lemmatizedSentence.toArray().length<9){
            sentenceTypePredict.add("focused_simple");
            sentenceTypePredict.add("auxiliary_sentence");
            sentenceTypePredict.add("negative_sentence");
            sentenceTypePredict.add("question_sentence");
            sentenceTypePredict.add("wh_questions");
            sentenceTypePredict.add("serial_verb_sentence");
        }
        else if (lemmatizedSentence.toArray().length>=5 && lemmatizedSentence.toArray().length<17){
            sentenceTypePredict.add("compound_sentence");
            sentenceTypePredict.add("complex_sentence");
            sentenceTypePredict.add("relative_clause_sentence");
            sentenceTypePredict.add("conditional_sentence");
        }
        else if (lemmatizedSentence.toArray().length>=10 && lemmatizedSentence.toArray().length<40){
            sentenceTypePredict.add("compound_complex_sentence");

        }

        return sentenceTypePredict;
    }
}
