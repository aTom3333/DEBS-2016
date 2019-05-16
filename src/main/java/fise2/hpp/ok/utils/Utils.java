package fise2.hpp.ok.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Utils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static String pathToResource(String name) throws URISyntaxException {
        return Objects.requireNonNull(Utils.class.getClassLoader().getResource(name)).toURI().getPath();
    }

    public static Path getJARPath() throws URISyntaxException {
        return Paths.get(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
    }

    public static String[] splitLine(String s) {
        return StringUtils.splitPreserveAllTokens(s, '|');
    }

    public static long stringToTS(String date) throws ParseException {
        Date d = sdf.parse(date);
        return d.getTime();
    }

    public static String TSToString(long ts) {
        return sdf.format(new Date(ts));
    }
}
