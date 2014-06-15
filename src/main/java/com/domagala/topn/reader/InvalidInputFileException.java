package com.domagala.topn.reader;

public class InvalidInputFileException extends RuntimeException {

    public static final String MESSAGE = "Input file is invalid";

    public InvalidInputFileException(Exception e) {
        super(MESSAGE, e);
    }
}
