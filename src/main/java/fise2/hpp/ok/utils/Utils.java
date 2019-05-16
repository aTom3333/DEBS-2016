package fise2.hpp.ok.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static String pathToResource(String name) throws URISyntaxException {
        return Objects.requireNonNull(Utils.class.getClassLoader().getResource(name)).toURI().getPath();
    }

    public static String[] splitLine(String s) {
        return StringUtils.splitPreserveAllTokens(s, '|');
    }

    public static long stringToTS(String date) throws ParseException {
        Date d = sdf.parse(date);
        return d.getTime();
    }
}
