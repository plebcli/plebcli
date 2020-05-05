package com.zaki.plebcli.util;

public final class CliUtils {

    public static final String SPACE = " ";

    public static final String COMMA = ",";

    public static final String OPEN_BODY = "{";

    public static final String CLOSE_BODY = "}";

    public static final String PARAMETERS_START = "(";

    public static final String PARAMETERS_END = ")";

    public static void noop() {
        ;
    }

    public static String removeInputPart(String s, String part) {
        return s.substring(part.length()).trim();
    }
}
