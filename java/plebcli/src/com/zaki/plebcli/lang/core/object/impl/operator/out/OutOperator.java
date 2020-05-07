package com.zaki.plebcli.lang.core.object.impl.operator.out;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;

public class OutOperator extends Operator {

    private String expression;

    public OutOperator(String expression) throws InvalidDefinitionException {
        super(Keywords.OUT);
        build(expression);
    }

    private void build(String expression) {
        this.expression = expression;
    }

    @Override
    public void operate(ObjectHolder memory) throws InvalidDefinitionException {
        System.out.println(new ExpressionEvaluator().evaluate(memory, expression));
    }
}
