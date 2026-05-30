package nlp;

import java.util.*;
public class Tokenize {
    public String[] tokenizeToWord(String input){
        String[] split = input.split(" ");
        ArrayList<String> separated = new ArrayList<>(Arrays.asList(split));
        String[] returned = separated.toArray(new String[0]);
        return returned;

    }
    public String[] tokenizeToSentence(String input){

        String split = input.replace("."," :");
        String[] splitted = split.split(":");
        ArrayList<String> separated = new ArrayList<>(Arrays.asList(splitted));
        String[] returned = separated.toArray(new String[0]);
        return returned;
    }

    public static void main (String[] args){
        Scanner enter = new Scanner(System.in);
        System.out.println("Enter A String");
        String word = enter.nextLine();
        Tokenize newToken = new Tokenize();
        String[] tokenized = newToken.tokenizeToWord(word);
        String[] tokenized2 = newToken.tokenizeToSentence(word);
        System.out.println(tokenized);
        System.out.println(tokenized2);
    }
}


