package nlp;

import java.util.ArrayList;
import java.util.Arrays;

public class test {
    public static void main (String[] args){
        String name = "jbybu yigfuy ugf 7gr iygye8";
        char  arr[] = name.toCharArray();
        ArrayList<String> Word = new ArrayList<>();
        for (char b : arr) {
            if (b == ' ') {
                Word.add(":");
            } else {
                Word.add(String.valueOf(b));
            }
        }
        String Rubbish = Word.toString();
        String arr2 = Rubbish.replace(',',' ');
        System.out.println(arr2);
    }

}