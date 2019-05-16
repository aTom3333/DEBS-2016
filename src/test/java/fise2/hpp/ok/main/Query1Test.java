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

public class Query1Test {

    private boolean compareFiles(String expected, String actual) throws URISyntaxException, IOException {
        BufferedReader expectedReader = Files.newBufferedReader(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(expected)).toURI()));
        BufferedReader actualReader = Files.newBufferedReader(Paths.get(Utils.getJARPath().toString(), actual));

        int i = 0;
        while (expectedReader.ready()) {
            ++i;
            if (!expectedReader.readLine().equals(actualReader.readLine())) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void Q1Basic() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Basic", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Basic2() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Basic2", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1BigTest() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1BigTest", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Case1() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Case1", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Case2() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Case2", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Case3() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Case3", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Case4() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Case4", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1Case5() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Case5", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1CommentCount() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1CommentCount", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1PostExpiredComment() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1PostExpiredComment", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }

    @Test
    public void Q1PostExpiredComment2() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1PostExpiredComment2", "output.txt");
        q.run();
        Assert.assertTrue(compareFiles("Q1Basic/_expectedQ1.txt", "output.txt"));
    }
}