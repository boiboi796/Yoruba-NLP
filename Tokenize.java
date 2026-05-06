package nlp;

import java.util.*;
public class Tokenize {
    public ArrayList<String> tokenizeToWord(String input){
        String[] split = input.split(" ");
        ArrayList<String> separated = new ArrayList<>();
        for (int i = 0; i< split.length; i++) {
            separated.add(split[i]);
        }
        return separated;

    }
    public ArrayList tokenizeToSentence(String input){

        String split = input.replace("."," :");
        String[] splited = split.split(":");
        ArrayList<String> separated = new ArrayList<>();
        for (int i = 0; i< splited.length; i++) {
            separated.add(splited[i]);
        }
        return separated;
    }

    public static void main (String[] args){
        Scanner enter = new Scanner(System.in);
        System.out.println("Enter A String");
        String word = enter.nextLine();
        Tokenize newToken = new Tokenize();
        ArrayList tokenized = newToken.tokenizeToWord(word);
        ArrayList tokenized2 = newToken.tokenizeToSentence(word);
        System.out.println(tokenized);
        System.out.println(tokenized2);
    }
}


