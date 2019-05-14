package fise2.hpp.ok.parser;

import java.io.InputStream;

public abstract class AbstractParser {
    InputStream input;

    public AbstractParser(InputStream stream) {
        input = stream;
    }
}
