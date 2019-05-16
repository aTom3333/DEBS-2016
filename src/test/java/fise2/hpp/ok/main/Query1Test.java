package fise2.hpp.ok.main;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Query1Test {

    @Test
    public void Q1Basic() throws FileNotFoundException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Basic");
        q.run();
    }
}