package fise2.hpp.ok.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public abstract class AbstractParser {
    private BufferedReader input;
    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public AbstractParser(Reader reader) {
        if (reader instanceof BufferedReader) {
            input = (BufferedReader) reader;
        } else {
            input = new BufferedReader(reader);
        }
    }

    protected String[] splitLine() throws IOException {
        String line = input.readLine();
        StringTokenizer tok = new StringTokenizer(line, "|", false);
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
