package com.zaki.plebcli.lang.core.object.impl.operator.infix.arithmetic;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.math.BigInteger;

public class PowerOperator extends InfixOperator {
    public PowerOperator(String name, String expressionLine) throws InvalidDefinitionException {
        super(name, expressionLine);
    }

    @Override
    public Primitive operateInfix(LocalObjectHolder memory) throws InvalidDefinitionException {
        return new Primitive(new BigInteger(getLeftValue(memory)).pow(Integer.parseInt(getRightValue(memory))).toString());
    }
}
