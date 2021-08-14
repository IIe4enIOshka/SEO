package com.company;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Введите названия файлов:");

        Path path1 = Paths.get(".\\" + in.next() + ".txt");
        Path path2 = Paths.get(".\\" + in.next() + ".txt");
        Path path3 = Paths.get(".\\" + in.next() + ".txt");

        long m = System.currentTimeMillis();

        if (!Files.notExists(path1) && !Files.notExists(path2) && !Files.notExists(path3)) {
            SEO seo = new SEO(path1, path2, path3);
            System.out.println("Файлы открыты...");
            seo.startAnalysis();
        } else {
            System.out.println("Файл не найден");
        }
//        SEO seo = new SEO(
//                Paths.get(".\\file1.txt"),
//                Paths.get(".\\file2.txt"),
//                Paths.get(".\\file3.txt")
//        );
//        seo.startAnalysis();

        System.out.println((double) (System.currentTimeMillis() - m));
    }
}
