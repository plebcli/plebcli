package com.zaki.plebcli.lang.core.object.impl.operator.prefix.arithmetic;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.base.Void;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;

import java.math.BigInteger;

public class AbsOperator extends Operator {

    private String expression;

    public AbsOperator(String expression) throws InvalidDefinitionException {
        super(Keywords.ABS);
        build(expression);
    }

    private void build(String expression) {
        this.expression = expression;
    }

    @Override
    public Primitive operate(LocalObjectHolder memory) throws InvalidDefinitionException {
        return new Primitive(new BigInteger(String.valueOf(new ExpressionEvaluator().evaluate(memory, expression))).abs().toString());
    }
}