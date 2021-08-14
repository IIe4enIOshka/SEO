package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SEO {
    private Path pathInFile1;
    private Path pathInFile2;
    private Path pathInFile3;

    private List<char[]> words = new ArrayList<>();

    private List<String> result = new ArrayList<>();

    private int firstIndex = 0;
    private int hitCount = 0;

    public SEO(Path pathInFile1, Path pathInFile2, Path pathInFile3) {
        this.pathInFile1 = pathInFile1;
        this.pathInFile2 = pathInFile2;
        this.pathInFile3 = pathInFile3;
    }

    public void startAnalysis() {
        List<String> textForAnalysis; //переименовать
        List<String> templates;
        try {
            textForAnalysis = Files.readAllLines(pathInFile1);
            templates = Files.readAllLines(pathInFile2);
            for (String str : textForAnalysis) {
                firstIndex = 0;
                splittingWords(str);
            }
            for (String str : templates) {
                hitCount = 0;
                templateValidation(str);
            }
            saveTheResultToAFile();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTheResultToAFile() {
        try (FileWriter writer = new FileWriter(String.valueOf(pathInFile3), false)) {
            for (String str : result) {
                writer.write(str);
                writer.append("\n");
                writer.flush();
            }
            System.out.println("Файл сохранен");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void templateValidation(String str) {
        if (str.charAt(0) == '"') {
            for (char[] word : words) {
                if (search(String.valueOf(word), str))
                    hitCount++;
            }
            result.add(str + "\t" + hitCount);
        } else {
            for (char[] word : words) {
                hitCount(str, word);
            }
            preparingAResponse(str);
        }
    }

    private void preparingAResponse(String str){
        String save = "";
        for (int i = 0; i < str.length() - 1; i += 2) {
            save += str.charAt(i) + "" + str.charAt(i + 1);
        }
        result.add(save + "\t" + hitCount);
    }

    private void hitCount(String str, char[] word) {
        boolean check = true;
        for (int i = 0; i < str.length() - 1; i += 2) {
            if (check)
                if (!search(word, str.charAt(i), Integer.parseInt(str.charAt(i + 1) + ""))) {
                    check = false;
                }
        }
        if (check)
            hitCount++;
    }

    private void splittingWords(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                if (str.length() - 1 == i) {
                    copyWordsToList(str, i, firstIndex, count);
                }
                count++;
            } else {
                copyWordsToList(str, i, firstIndex, count);
            }
        }
    }

    private void copyWordsToList(String str, int i, int firstIndex, int count) {
        char[] _buffer = new char[count];
        str.getChars(firstIndex, i, _buffer, 0);
        words.add(_buffer);
        this.firstIndex = i + 1;
    }


    private boolean search(String str, String str2) {
        str2 = str2.substring(1, str2.length() - 1);
        if (str.contains(str2)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean search(char[] word, char str, int n) {
        int k = 0;
        for (int i = 0; i < word.length; i++) {
            if (word[i] == str)
                k++;
        }
        if (k >= n) {
            return true;
        }
        return false;
    }
}



