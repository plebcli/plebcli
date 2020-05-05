package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.Callable;
import com.zaki.plebcli.lang.core.object.impl.Function;
import com.zaki.plebcli.lang.core.object.impl.Operator;

import java.util.List;
import java.util.Stack;

public class Call extends Operator {

    private final Callable f;
    private final List<String> p;

    public Call(Callable f, List<String> p) throws InvalidDefinitionException {
        super(Keywords.CALL);
        this.f = f;
        this.p = p;
    }

    @Override
    public void operate(ObjectHolder memory) throws InvalidDefinitionException {
        f.call(p);
    }
}
