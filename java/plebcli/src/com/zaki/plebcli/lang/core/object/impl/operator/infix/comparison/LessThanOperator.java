package com.zaki.plebcli.lang.core.object.impl.operator.infix.comparison;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.CliBoolean;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.math.BigInteger;

public class LessThanOperator extends InfixOperator {
    public LessThanOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name, expressionLine);
    }

    @Override
    public Primitive operateInfix(LocalObjectHolder memory) throws InvalidDefinitionException {
        return CliBoolean.getCliBoolean(new BigInteger(getLeftValue(memory)).compareTo(new BigInteger(getRightValue(memory))) < 0);
    }
}
