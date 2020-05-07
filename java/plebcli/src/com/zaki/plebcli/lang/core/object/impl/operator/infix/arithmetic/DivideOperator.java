package com.zaki.plebcli.lang.core.object.impl.operator.infix.arithmetic;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.math.BigInteger;

public class DivideOperator extends InfixOperator {
    public DivideOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name, expressionLine);
    }

    @Override
    public String operateWithResult(ObjectHolder memory) throws InvalidDefinitionException {
        return new BigInteger(getLeftValue(memory)).divide(new BigInteger(getRightValue(memory))).toString();
    }
}
