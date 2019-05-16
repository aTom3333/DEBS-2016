package fise2.hpp.ok.main;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class Query1Test {

    @Test
    public void Q1Basic() throws IOException, URISyntaxException, InterruptedException {
        Query1 q = new Query1("Q1Basic", "output.txt");
        q.run();
    }
}