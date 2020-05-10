package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.Block;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.arithmetic.*;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.comparison.*;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.CallOperator;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.IfOperator;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.ReturnOperator;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.arithmetic.AbsOperator;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.in.InOperator;
import com.zaki.plebcli.lang.core.object.impl.operator.prefix.out.OutOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class OperatorBuilder {

    private static final Map<String, Class<? extends Operator>> prefixOperators = new HashMap<>();

    static {
        prefixOperators.put(Keywords.IF, IfOperator.class);
        prefixOperators.put(Keywords.CALL, CallOperator.class);
        prefixOperators.put(Keywords.OUT, OutOperator.class);
        prefixOperators.put(Keywords.IN, InOperator.class);
        prefixOperators.put(Keywords.RETURN, ReturnOperator.class);
        prefixOperators.put(Keywords.ABS, AbsOperator.class);
        prefixOperators.put(Keywords.POWER, PowerOperator.class);
    }

    private static final Map<String, Class<? extends Operator>> infixOperators = new HashMap<>();

    static {
        infixOperators.put(Keywords.PLUS, PlusOperator.class);
        infixOperators.put(Keywords.MINUS, MinusOperator.class);
        infixOperators.put(Keywords.DIVIDE, DivideOperator.class);
        infixOperators.put(Keywords.MULTIPLY, MultiplyOperator.class);
        infixOperators.put(Keywords.EQUAL, EqualOperator.class);
        infixOperators.put(Keywords.LESS_THAN, LessThanOperator.class);
        infixOperators.put(Keywords.LESS_THAN_OR_EQUAL, LessThanOrEqualOperator.class);
        infixOperators.put(Keywords.GREATER_THAN, GreaterThanOperator.class);
        infixOperators.put(Keywords.GREATER_THAN_OR_EQUAL, GreaterThanOrEqualOperator.class);
        infixOperators.put(Keywords.MODULO, ModuloOperator.class);
        infixOperators.put(Keywords.POWER, PowerOperator.class);
    }

    private OperatorBuilder() {

    }

    public static Operator buildPrefixOperatorByName(String name, String currentLine, Stack<String> userInput) throws InvalidDefinitionException {

        Operator result = null;

        Class<? extends Operator> operatorHandler = prefixOperators.get(name);
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

    public static Operator buildInfixOperatorByName(String name, String currentLine) throws InvalidDefinitionException {

        Operator result = null;

        Class<? extends Operator> operatorHandler = infixOperators.get(name);
        if (operatorHandler != null) {
            try {
                result = operatorHandler.getConstructor(String.class, String.class).newInstance(name, currentLine);
            } catch (Exception e) {
                // TODO log it
                throw new InvalidDefinitionException(currentLine);
            }
        }

        return result;
    }

    public static Set<String> getInfixOperators() {
        return infixOperators.keySet();
    }

    public static Set<String> getPrefixOperators() {
        return prefixOperators.keySet();
    }
}
