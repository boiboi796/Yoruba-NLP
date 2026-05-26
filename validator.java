package nlp;
import java.util.*;


public class validator {
    public ArrayList<String> determineSentenceType(ArrayList lemmatizedSentence){
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
    public HashMap<String,List<Enum>> getRuleBreakDown(){
        HashMap<String, List<Enum>> lookupWords = new HashMap<>();
        lookupWords.put("simple_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("focused_simple",
                Arrays.asList(validatorEnum.OBJECT,
                        validatorEnum.FOCUS_MARKER,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB));
        lookupWords.put("auxiliary_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.AUXILIARY,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("negative_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.NEGATION,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("question_sentence",
                Arrays.asList(validatorEnum.QUESTION_MARKER,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("wh_questions",
                Arrays.asList(validatorEnum.WH_WORD,
                        validatorEnum.FOCUS_MARKER,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("serial_verb_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.SERIAL_VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("compound_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT,
                        validatorEnum.CONJUNCTION,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("complex_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.SUBORDINATOR,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("relative_clause_sentence",
                Arrays.asList(validatorEnum.NOUN,
                        validatorEnum.RELATIVE_MARKER,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));
        lookupWords.put("conditional_sentence",
                Arrays.asList(validatorEnum.CONDITIONAL_MARKER,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB));
        lookupWords.put("compound_complex_sentence",
                Arrays.asList(validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT,
                        validatorEnum.RELATIVE_MARKER,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.CONJUNCTION,
                        validatorEnum.SUBJECT,
                        validatorEnum.VERB,
                        validatorEnum.OBJECT));

        return lookupWords;
    }
}
