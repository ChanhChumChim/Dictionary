package main;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public static final String INPUT_PATH = "src/main/java/files/dictionaries.txt";
    public static final String OUTPUT_PATH = "";

    public static void insertFromCommandLine() {
        Scanner getStringInput = new Scanner(System.in);
        Scanner getIntegerInput = new Scanner(System.in);
        int count = getIntegerInput.nextInt();
        int i = 1;
        while (i <= count) {
            String target = getStringInput.nextLine();
            String meaning = getStringInput.nextLine();
            Word temp = new Word(target, meaning);
            words.add(temp);
            i++;
        }
    }

    public static void insertFromFile() {
        try {
            File input = new File(INPUT_PATH);
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordsInLine = line.split(",");
                Word temp = new Word(wordsInLine[0], wordsInLine[1]);
                words.add(temp);
            }
            Collections.sort(words);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
