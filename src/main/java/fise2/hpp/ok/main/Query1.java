package fise2.hpp.ok.main;

import fise2.hpp.ok.parsing.CommentParser;
import fise2.hpp.ok.parsing.Linker;
import fise2.hpp.ok.parsing.PostParser;
import fise2.hpp.ok.structs.Data;
import fise2.hpp.ok.utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class Query1 {
    public Data data;
    private CommentParser commentParser;
    private PostParser postParser;

    public Query1(String directory) throws URISyntaxException, FileNotFoundException {
        data = Data.instance();
        commentParser = new CommentParser(new FileReader(Utils.pathToResource(directory + "/comments.dat")));
        postParser = new PostParser(new FileReader(Utils.pathToResource(directory + "/posts.dat")));
    }

    public void loadFullRessources() throws IOException, ParseException {
        fise2.hpp.ok.eventsIR.Comment commentIR;
        fise2.hpp.ok.eventsIR.Post postIR;
        while ((commentIR = commentParser.getNext()) != null)
            Linker.link(commentIR, data);
        while ((postIR = postParser.getNext()) != null)
            Linker.link(postIR, data);
    }

    public void processNaive() {

    }
}
