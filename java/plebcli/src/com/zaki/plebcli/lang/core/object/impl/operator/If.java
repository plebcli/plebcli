package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.CliBoolean;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.Operator;

import java.util.List;
import java.util.Stack;

public class If extends Operator {

    private final String expression;

    public If(String expression, Stack<String> body) throws InvalidDefinitionException {
        this(Keywords.IF, expression, body);
    }

    public If(String name, String expression, Stack<String> body) throws InvalidDefinitionException {
        super(name, body);
        this.expression = expression;
    }

    @Override
    public void call(ObjectHolder localMemory) throws InvalidDefinitionException {
        if (CliBoolean.parseBoolean(new ExpressionEvaluator().evaluate(localMemory, expression))) {
            processBody(getBody(), localMemory);
        }
    }

    @Override
    public void call(List<String> parameterValues) throws InvalidDefinitionException {
        LocalObjectHolder localMemory = new LocalObjectHolder();
        if (CliBoolean.parseBoolean(new ExpressionEvaluator().evaluate(localMemory, expression))) {
            processBody(getBody(), localMemory);
        }
    }
}
