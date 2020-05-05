package com.zaki.plebcli.lang.core.object.impl.operator.in;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.Operator;
import com.zaki.plebcli.lang.core.object.impl.Variable;

import java.util.Scanner;
import java.util.Stack;

public class In extends Operator {

    private String varName;

    public In(String varName) throws InvalidDefinitionException {
        super(Keywords.IN);
        this.varName = varName;
    }

    @Override
    public void operate(ObjectHolder memory) throws InvalidDefinitionException {
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine();
        memory.addObject(new Variable(varName, value));
    }
}
