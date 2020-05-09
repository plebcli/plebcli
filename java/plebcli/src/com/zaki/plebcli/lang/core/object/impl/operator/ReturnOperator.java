package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.base.Void;

public class ReturnOperator extends Operator {

    private final String expression;

    public ReturnOperator(String callLine) throws InvalidDefinitionException {
        super(Keywords.RETURN);
        this.expression = callLine;
    }

    @Override
    public Primitive operate(LocalObjectHolder memory) throws InvalidDefinitionException {
        return expression == null || expression.isEmpty() ? new Void() : new ExpressionEvaluator().evaluate(memory, expression);
    }
}
