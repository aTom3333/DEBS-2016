package fise2.hpp.ok.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public abstract class AbstractParser {
    protected BufferedReader input;
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public AbstractParser(Reader reader) {
        if (reader instanceof BufferedReader) {
            input = (BufferedReader) reader;
        } else {
            input = new BufferedReader(reader);
        }
    }

    public boolean hasNext() throws IOException {
        return input.ready();
    }

    protected String[] splitLine(String s) throws IOException {
        StringTokenizer tok = new StringTokenizer(s, "|", false);
        ArrayList<String> result = new ArrayList<>();
        while (tok.hasMoreTokens()) {
            result.add(tok.nextToken());
        }

        return (String[]) result.toArray();
    }

    protected static long stringToTS(String date) throws ParseException {
        Date d = sdf.parse(date);
        return d.getTime();
    }
}
