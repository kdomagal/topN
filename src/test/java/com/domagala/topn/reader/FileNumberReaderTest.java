package com.domagala.topn.reader;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class FileNumberReaderTest {

    private static final String FILE_PATH = "/tmp/testFile.txt";

    private File testFile;

    @Before
    public void setup() {
        testFile = new File(FILE_PATH);
        testFile.getParentFile().mkdirs();
    }

    @After
    public void tearDown() throws IOException {
        if (testFile.exists()) {
            FileUtils.forceDelete(testFile);
        }
    }

    @Test
    public void testReadLine() throws IOException {
        FileUtils.writeLines(testFile, Lists.newArrayList("1", "2", "3"));

        NumberReader reader = new FileNumberReader(FILE_PATH);
        assertThat(reader.read(), equalTo(1));
        assertThat(reader.read(), equalTo(2));
        assertThat(reader.read(), equalTo(3));
        assertThat(reader.read(), equalTo(null));
        assertThat(reader.hasNext(), is(false));
    }

    @Test
    public void testReadNonNumberLine_shouldSkipLines() throws IOException {
        FileUtils.writeLines(testFile, Lists.newArrayList("1", "a", "", " ", 2));

        NumberReader reader = new FileNumberReader(FILE_PATH);
        assertThat(reader.read(), equalTo(1));
        assertThat(reader.read(), equalTo(2));
        assertThat(reader.read(), equalTo(null));
        assertThat(reader.hasNext(), is(false));
    }

    @Test(expected = InvalidInputFileException.class)
    public void testReadLineNonExistingFile() {
        new FileNumberReader("/tmp/topN");
    }


}
