package fise2.hpp.ok.utils;

import java.net.URISyntaxException;
import java.util.Objects;

public class Utils {
    public static String pathToResource(String name) throws URISyntaxException {
        return Objects.requireNonNull(Utils.class.getClassLoader().getResource(name)).toURI().getPath();
    }
}
