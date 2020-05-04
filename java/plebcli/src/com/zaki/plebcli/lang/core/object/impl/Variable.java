package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.CliObject;

public class Variable extends CliObject {

    private final String value;

    public Variable(String name, String value) throws InvalidDefinitionException {
        super(name);
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Keywords.VARIABLE + " [" + getName() + ", " + getValue() + "]";
    }

    @Override
    protected boolean validate() {
        return true;
    }
}
