package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;

public class ReturnOperator extends Operator {

    private final String expression;

    public ReturnOperator(String callLine) throws InvalidDefinitionException {
        super(Keywords.RETURN);
        this.expression = callLine;
    }

    @Override
    public Primitive operate(ObjectHolder memory) throws InvalidDefinitionException {
        return new ExpressionEvaluator().evaluate(memory, expression);
    }
}
