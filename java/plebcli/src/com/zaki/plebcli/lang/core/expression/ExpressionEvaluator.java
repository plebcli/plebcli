package com.zaki.plebcli.lang.core.expression;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;

import java.util.Stack;

public class ExpressionEvaluator {

    public String evaluate(ObjectHolder memory, String expression) throws InvalidDefinitionException {

        Stack<String> input = new Stack();
        input.add(expression);
        return new Tokenizer().getObject(input, memory).toString();
    }
}
