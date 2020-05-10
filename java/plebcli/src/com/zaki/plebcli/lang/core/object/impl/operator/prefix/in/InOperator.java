package com.zaki.plebcli.lang.core.object.impl.operator.prefix.in;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.util.CliUtils;

import java.util.Scanner;

public class InOperator extends Operator {

    private String varName;

    public InOperator(String varName) throws InvalidDefinitionException {
        super(Keywords.IN);
        build(varName);
    }

    @Override
    public Primitive operate(LocalObjectHolder memory) throws InvalidDefinitionException {
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine();
        memory.addObject(new Variable(varName, value));
        return new Primitive(value);
    }

    private void build(String s) throws InvalidDefinitionException {

        // TODO proveri dali ne e duma, ako ne e duma hwurli greshka
        if (s.contains(CliUtils.SPACE)) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        this.varName = s;
    }
}
