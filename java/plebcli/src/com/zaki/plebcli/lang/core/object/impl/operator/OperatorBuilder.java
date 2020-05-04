package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.Operator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class OperatorBuilder {

    private static final Map<String, Class> prefixOperators = new HashMap<>();

    static {
        prefixOperators.put(Keywords.IF, If.class);
        prefixOperators.put(Keywords.ELSE, Else.class);
        prefixOperators.put(Keywords.ELSE_IF, If.class);
        prefixOperators.put(Keywords.CALL, Call.class);
    }

    private OperatorBuilder() {

    }

    public static Operator buildPrefix(String name, String expression, Stack<String> body) throws InvalidDefinitionException {
        Operator result = null;

        if (Keywords.IF.equals(name)) {
            result = new If(name, expression, body);
        }

        return result;
    }
}
