package main;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public static final String INPUT_PATH = "src/main/java/files/dictionaries.txt";
    public static final String OUTPUT_PATH = "src/main/java/files/dictionaries_out.txt";

    public void insertFromCommandLine() {
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

    public static void exportToFile() {
        try {
            File file = new File(OUTPUT_PATH);
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String format = "%-15s %-15s%n";
            for (Word word : words) {
                bufferedWriter.write(String.format(format, word.getWord_target(), word.getWord_explain()));
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateWordToFile() {
        try {
            FileWriter fileWriter = new FileWriter(INPUT_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Word word : words) {
                bufferedWriter.write(word.getWord_target() + "," + word.getWord_explain() + "\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addWord(String searching, String meaning) {
        searching = searching.toLowerCase();
        meaning = meaning.toLowerCase();
        int posAddWord = binaryCheck(0, words.size(), searching);
        if (posAddWord == -1) {
            System.out.println("Từ đã tồn tại!");
            return;
        }
        words.add(new Word());
        for (int i = words.size() - 2; i >= posAddWord; i--) {
            words.get(i + 1).setWord_target(words.get(i).getWord_target());
            words.get(i + 1).setWord_explain(words.get(i).getWord_explain());
        }
        words.get(posAddWord).setWord_target(searching);
        words.get(posAddWord).setWord_explain(meaning);
        updateWordToFile();
    }

    public static void removeWord(String searching) {
        searching = searching.toLowerCase();
        int index = Collections.binarySearch(words, new Word(searching, null));
        if (index >= 0) {
            words.remove(words.get(index));
        } else {
            System.out.println("Từ không tồn tại!");
        }
        updateWordToFile();
    }

    public static void modifyWord(String searching, String meaning) {
        searching = searching.toLowerCase();
        meaning = meaning.toLowerCase();
        int pos = -1;
        pos = Collections.binarySearch(words, new Word(searching, null));
        if (pos >= 0) {
            words.get(pos).setWord_explain(meaning);
        } else {
            System.out.println("Từ không tồn tại!");
        }
        updateWordToFile();
    }

    public static int binaryCheck(int start, int end, String word) {
        if (end < start) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        int compareNext = word.compareTo(words.get(mid).getWord_target());
        if (mid == 0) {
            if (compareNext < 0) {
                return 0;
            } else if (compareNext > 0) {
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        } else {
            int comparePrevious = word.compareTo(words.get(mid - 1).getWord_target());
            if (comparePrevious > 0 && compareNext < 0) {
                return mid;
            } else if (comparePrevious < 0) {
                return binaryCheck(start, mid - 1, word);
            } else if (compareNext > 0) {
                if (mid == words.size() - 1) {
                    return words.size();
                }
                return binaryCheck(mid + 1, end, word);
            } else {
                return -1;
            }
        }
    }
}
