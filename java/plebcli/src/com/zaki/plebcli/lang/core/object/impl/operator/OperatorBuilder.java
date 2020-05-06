package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.Operator;
import com.zaki.plebcli.lang.core.object.impl.operator.in.In;
import com.zaki.plebcli.lang.core.object.impl.operator.out.Out;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class OperatorBuilder {

    private static final Map<String, Class<? extends Operator>> operators = new HashMap<>();

    static {
        operators.put(Keywords.IF, If.class);
        operators.put(Keywords.CALL, Call.class);
        operators.put(Keywords.OUT, Out.class);
        operators.put(Keywords.IN, In.class);
    }

    private OperatorBuilder() {

    }

    public static Operator buildPrefixOperatorByName(String name, String currentLine, Stack<String> userInput) throws InvalidDefinitionException {

        Operator result = null;

        Class<? extends Operator> operatorHandler = operators.get(name);
        if (operatorHandler != null) {
            try {
                // check whether our operator should have body i.e. {}
                if (Block.class.isAssignableFrom(operatorHandler)) {
                    result = operatorHandler.getConstructor(String.class, Stack.class).newInstance(currentLine, userInput);
                } else {
                    result = operatorHandler.getConstructor(String.class).newInstance(currentLine);
                }
            } catch (Exception e) {
                // TODO log it
                throw new InvalidDefinitionException(currentLine);
            }
        }

        return result;
    }
}
