package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.core.object.CliObject;

import java.util.Stack;

public abstract class Operator extends CliObject implements Callable {

    private final Stack<String> body;

    public Operator(String name, Stack<String> body) throws InvalidDefinitionException {
        super(name);
        this.body = body;
    }

    @Override
    public String toString() {
        return "operator " + getName();
    }

    public Stack<String> getBody() {
        return body;
    }

    @Override
    protected boolean validate() {
        return false;
    }
}
