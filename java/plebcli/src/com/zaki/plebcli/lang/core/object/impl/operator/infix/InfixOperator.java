package com.zaki.plebcli.lang.core.object.impl.operator.infix;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;
import com.zaki.plebcli.util.CliUtils;

public abstract class InfixOperator extends Operator {

    private final String leftExpression;
    private final String rightExpression;

    public InfixOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name);

        this.leftExpression = expressionLine.substring(0, expressionLine.indexOf(name)).trim();
        this.rightExpression = expressionLine.substring(expressionLine.indexOf(name) + name.length()).trim();
    }

    public String getLeftExpression() {
        return leftExpression;
    }

    public String getRightExpression() {
        return rightExpression;
    }

    public String getLeftValue(ObjectHolder memory) throws InvalidDefinitionException {
        return new ExpressionEvaluator().evaluate(memory, leftExpression);
    }

    public String getRightValue(ObjectHolder memory) throws InvalidDefinitionException {
        return new ExpressionEvaluator().evaluate(memory, rightExpression);
    }

    @Override
    public void operate(ObjectHolder memory) {
        CliUtils.noop();
    }

    public abstract String operateWithResult(ObjectHolder memory) throws InvalidDefinitionException;
}
