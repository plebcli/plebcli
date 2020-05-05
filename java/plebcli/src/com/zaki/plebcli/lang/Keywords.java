package com.zaki.plebcli.lang;

import com.zaki.plebcli.util.CliUtils;

import java.util.HashSet;
import java.util.Set;

public class Keywords {

    public static final String TRUE = "istina";

    public static final String FALSE = "luja";

    public static final String DEFINE = "definirai";

    public static final String VARIABLE = "promenliva";

    public static final String FUNCTION = "funkciq";

    public static final String ASSIGN = "ravna na";

    public static final String IF = "ako";

    public static final String ELSE = "inache";

    public static final String ELSE_IF = "ili ako";

    public static final String PARAMETERS = "s parametri";

    public static final String CALL = "izvikai " + FUNCTION;

    public static final String PLUS = "plius";

    public static final String MINUS = "minus";

    public static final String DIVIDE = "deleno na";

    public static final String MULTIPLY = "umnojeno po";

    public static final String IN = "vuvedi";

    public static final String OUT = "izvedi";

    private static Set<String> keywords = new HashSet<>();

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
    }

    private static Set<String> prefixOperators = new HashSet<>();

    static {
        prefixOperators.add(IF);
        prefixOperators.add(ELSE_IF);
        prefixOperators.add(ELSE);
        prefixOperators.add(CALL);
        prefixOperators.add(IN);
        prefixOperators.add(OUT);
    }

    private static Set<String> infixOperators = new HashSet<>();

    static {
        infixOperators.add(PLUS);
        infixOperators.add(MINUS);
        infixOperators.add(DIVIDE);
        infixOperators.add(MULTIPLY);
    }

    private Keywords() {
        CliUtils.noop();
    }

    public static Set<String> getKeywords() {
        return keywords;
    }

    public static Set<String> getPrefixOperators() {
        return prefixOperators;
    }

    public static Set<String> getInfixOperators() {
        return infixOperators;
    }
}
