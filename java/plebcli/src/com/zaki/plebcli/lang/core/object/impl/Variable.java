package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;

public class Variable extends CliObject {

    private final String value;

    public Variable(String name, String value) throws InvalidDefinitionException {
        super(name, ObjectType.VARIABLE);
        this.value = value;
    }

    public String getValue(LocalObjectHolder memory) {
        return new ExpressionEvaluator().evaluate(memory, value).toString();
    }

    @Override
    protected boolean validate() {
        return true;
    }
}
