package main;

import java.io.IOException;

public class DictionaryCommandline extends Dictionary {
    public static String showAllWords() {
        String temp = "";
        System.out.printf("%-4s%c %-12s%c %-15s%n","No", '|' ,"English", '|', "Vietnamese");
        for (int i = 0; i < words.size(); i++) {
            System.out.printf("%-4d%c %-12s%c %-15s%n", i + 1,'|'
                    , words.get(i).getWord_target(), '|',words.get(i).getWord_explain());
        }
        return temp;
    }

    public static void main(String[] args) throws IOException {
        DictionaryManagement.insertFromFile();
        DictionaryManagement.insertFromCommandLine();
        System.out.println(showAllWords());
    }
}

