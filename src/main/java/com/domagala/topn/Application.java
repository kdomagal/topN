package com.domagala.topn;

import com.domagala.topn.reader.FileNumberReader;
import com.domagala.topn.reader.NumberReader;
import com.domagala.topn.sort.InMemorySorter;
import com.domagala.topn.sort.Sorter;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.NavigableSet;

import static java.lang.System.exit;

public class Application {

    public static void main(String[] args) {

        if (args.length != 2) {
            showUsageAndExit();
        }

        String filePath = args[0];
        validateFileOrExit(filePath);

        int topNCount = Integer.valueOf(args[1]);

        NumberReader reader = new FileNumberReader(filePath);
        Sorter sorter = new InMemorySorter(reader);

        Instant start = Instant.now();
        NavigableSet<Integer> sorted = sorter.sort(topNCount);
        Instant finish = Instant.now();

        sorted.stream().forEach(System.out::println);
        System.out.println("Execution time: " + Duration.between(start, finish));
    }

    private static void showUsageAndExit() {
        System.out.println(" \nUsage: ");
        System.out.println("\tjava -jar topN.jar inputfile topNumbersCount");
        exit(1);
    }

    private static void validateFileOrExit(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Input file does not exist.");
            exit(1);
        }
    }

}
