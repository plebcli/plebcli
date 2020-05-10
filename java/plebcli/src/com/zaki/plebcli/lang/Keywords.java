package com.zaki.plebcli.lang;

import com.zaki.plebcli.util.CliUtils;

import java.util.HashSet;
import java.util.Set;

public class Keywords {

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String DEFINE = "def";

    public static final String VARIABLE = "var";

    public static final String FUNCTION = "function";

    public static final String ASSIGN = "=";

    public static final String IF = "if";

    public static final String ELSE = "else";

    public static final String ELSE_IF = "else if";

    public static final String PARAMETERS = "with parameters";

    public static final String CALL = "call";

    public static final String PLUS = "+";

    public static final String MINUS = "-";

    public static final String DIVIDE = "/";

    public static final String MULTIPLY = "*";

    public static final String IN = "enter";

    public static final String OUT = "print";

    public static final String EQUAL = "==";

    public static final String GREATER_THAN_OR_EQUAL = ">=";

    public static final String GREATER_THAN = ">";

    public static final String LESS_THAN_OR_EQUAL = "<=";

    public static final String LESS_THAN = "<";

    public static final String RETURN = "return";

    public static final String VOID = "void";

    public static final String ARRAY = "array";

    public static final String ABS = "abs";

    public static final String MODULO = "%";

    public static final String POWER = "^";

    private static Set<String> keywords = new HashSet<>();

    // TODO add all keywords here and think of way to simplify it
    static {
        keywords.add(DEFINE);
        keywords.add(VARIABLE);
        keywords.add(FUNCTION);
        keywords.add(ASSIGN);
        keywords.add(PARAMETERS);
        keywords.add(TRUE);
        keywords.add(FALSE);
        keywords.add(IF);
        keywords.add(ELSE);
        keywords.add(ELSE_IF);
        keywords.add(CALL);
        keywords.add(RETURN);
    }

    private Keywords() {
        CliUtils.noop();
    }

    public static Set<String> getKeywords() {
        return keywords;
    }
}
