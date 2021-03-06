package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;

public abstract class Operator extends CliObject {

    public Operator(String name) throws InvalidDefinitionException {
        super(name, ObjectType.OPERATOR);
    }

    @Override
    public String toString() {
        return "operator " + getName();
    }

    @Override
    protected boolean validate() {
        return false;
    }

    public abstract Primitive operate(LocalObjectHolder memory) throws InvalidDefinitionException;
}
