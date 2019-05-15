package fise2.hpp.ok.main;

import fise2.hpp.ok.parsing.CommentParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.Objects;

public class Tests {

    @Test
    public void Q1Basic() throws FileNotFoundException, URISyntaxException {
        CommentParser commentParser = new CommentParser(new FileReader(Objects.requireNonNull(Tests.class.getClassLoader().getResource("Q1Basic/comments.dat")).toURI().getPath()));
    }
}