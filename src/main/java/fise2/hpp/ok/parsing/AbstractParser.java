package fise2.hpp.ok.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

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
        StringTokenizer tok = new StringTokenizer(s, "|", false);
        String[] result = new String[tok.countTokens() + 1];

        int i = 0;
        while(tok.hasMoreElements()) {
            result[i++] = tok.nextToken();
        }
        if(result[result.length - 1] == null)
            result[result.length - 1] = "";

        return result;
    }

    public static long stringToTS(String date) throws ParseException {
        Date d = sdf.parse(date);
        return d.getTime();
    }
}
