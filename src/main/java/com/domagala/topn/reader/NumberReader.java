package com.domagala.topn.reader;

public interface NumberReader {

    /**
     * Reads the number from a single line. Empty line or invalid values are skipped.
     * If reader reaches EOF the stream will close automatically.
     * @return Number read.
     */
    Integer read();

    boolean hasNext();
}
