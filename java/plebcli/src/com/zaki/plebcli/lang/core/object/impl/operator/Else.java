package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;

import java.util.Stack;

public class Else extends If {
    public Else(Stack<String> body) throws InvalidDefinitionException {
        super(Keywords.ELSE, Keywords.TRUE, body);
    }
}
