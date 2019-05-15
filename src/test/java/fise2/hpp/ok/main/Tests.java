package fise2.hpp.ok.main;

import fise2.hpp.ok.parsing.CommentParser;
import fise2.hpp.ok.utils.Utils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;

public class Tests {

    @Test
    public void Q1Basic() throws FileNotFoundException, URISyntaxException {
        CommentParser commentParser = new CommentParser(new FileReader(Utils.pathToResource("Q1Basic/comments.dat")));
    }
}