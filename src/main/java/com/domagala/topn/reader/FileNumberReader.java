package com.domagala.topn.reader;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public class FileNumberReader implements NumberReader {

    private LineIterator lineIterator;


    /**
     * Opens reader for a file under given path. File has to exist.
     * @param filePath Path to a file.
     */
    public FileNumberReader(String filePath) {
        File file = new File(filePath);
        try {
            lineIterator = FileUtils.lineIterator(file, Charsets.UTF_8.name());
        } catch (IOException e) {
            throw new InvalidInputFileException(e);
        }
    }

    @Override
    public Integer read() {
        Integer number = findNextNumber();
        closeIfEOF();
        return number;
    }

    @Override
    public boolean hasNext() {
        return lineIterator.hasNext();
    }

    private Integer findNextNumber() {
        String line;
        Integer number = null;
        while (lineIterator.hasNext() && number == null) {
            line = lineIterator.nextLine();
            number = parseLine(line);
        }
        return number;
    }

    private Integer parseLine(String line) {
        try {
            return Integer.valueOf(line);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void closeIfEOF() {
        if (!lineIterator.hasNext()) {
            LineIterator.closeQuietly(lineIterator);
        }
    }
}
