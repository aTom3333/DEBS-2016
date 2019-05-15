package fise2.hpp.ok.parsing;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractParser {
    protected BufferedReader input;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public AbstractParser(Reader reader) {
        if(reader instanceof BufferedReader) {
            input = (BufferedReader) reader;
        } else {
            input = new BufferedReader(reader);
        }
    }

    public boolean hasNext() throws IOException {
        return input.ready();
    }

    protected static String[] splitLine(String s) {
        return StringUtils.splitPreserveAllTokens(s, '|');
    }

    protected static long stringToTS(String date) throws ParseException {
        Date d = sdf.parse(date);
        return d.getTime();
    }
}
