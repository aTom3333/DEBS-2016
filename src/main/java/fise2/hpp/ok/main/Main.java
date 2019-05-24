package fise2.hpp.ok.main;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        long beg = System.nanoTime();
        Query1 q = new Query1("sample", "output/sample/output.txt");
        q.run();
        long time = System.nanoTime() - beg;

        System.out.println(String.valueOf((double) time / 1000000000) + " secondes");
    }
}
