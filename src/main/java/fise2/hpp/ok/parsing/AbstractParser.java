package fise2.hpp.ok.parsing;

import fise2.hpp.ok.interfaces.Event;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class AbstractParser {
    protected BufferedReader input;

    public AbstractParser(Reader reader) {
        if(reader instanceof BufferedReader) {
            input = (BufferedReader) reader;
        } else {
            input = new BufferedReader(reader);
        }
    }

    public abstract Event getNext() throws IOException, ParseException;

    public boolean hasNext() throws IOException {
        return input.ready();
    }

}
