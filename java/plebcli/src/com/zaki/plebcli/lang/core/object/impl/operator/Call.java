package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.Operator;

import java.util.List;
import java.util.Stack;

public class Call extends Operator {

    public Call(Stack<String> body) throws InvalidDefinitionException {
        super(Keywords.CALL, body);
    }

    @Override
    public void call(ObjectHolder localMemory) throws InvalidDefinitionException {

    }

    @Override
    public void call(List<String> parameterValues) throws InvalidDefinitionException {

    }
}
