package com.zaki.plebcli.lang.core.object.impl.operator.infix.arithmetic;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.math.BigInteger;

public class MinusOperator extends InfixOperator {
    public MinusOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name, expressionLine);
    }

    @Override
    public String operateWithResult(ObjectHolder memory) throws InvalidDefinitionException {
        return new BigInteger(getLeftValue(memory)).subtract(new BigInteger(getRightValue(memory))).toString();
    }
}
