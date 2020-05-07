package com.zaki.plebcli.lang.core.object.impl.operator.infix.arithmetic;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.math.BigInteger;

public class PlusOperator extends InfixOperator {
    public PlusOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name, expressionLine);
    }

    @Override
    public String operateWithResult(ObjectHolder memory) throws InvalidDefinitionException {
        return new BigInteger(getLeftValue(memory)).add(new BigInteger(getRightValue(memory))).toString();
    }
}
