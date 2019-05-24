package fise2.hpp.ok.main;

import fise2.hpp.ok.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Tests {

    private boolean compareFiles(String expected, String actual) throws URISyntaxException, IOException {
        BufferedReader expectedReader = Files.newBufferedReader(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(expected)).toURI()));
        BufferedReader actualReader = Files.newBufferedReader(Paths.get(Utils.getJARPath().toString(), actual));

        while (expectedReader.ready()) {
            Assert.assertEquals(expectedReader.readLine(), actualReader.readLine());
        }
        expectedReader.close();
        actualReader.close();
        return true;
    }

    @Test
    public void sampleTest() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("sample", "output/sample/output.txt");
        q.run();
        compareFiles("sample/_expectedAXEL.txt", "output/sample/output.txt");
    }

}