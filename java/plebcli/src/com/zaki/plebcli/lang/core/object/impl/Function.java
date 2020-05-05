package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.operator.Block;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Function extends CliObject implements Callable, Block {

    private List<String> parameters;

    private Stack<String> body;

    public Function(String name, List<String> parameters, Stack<String> body) throws InvalidDefinitionException {
        super(name, ObjectType.FUNCTION);
        this.parameters = Collections.unmodifiableList(parameters);
        this.body = body;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Stack<String> getBody() {
        return body;
    }

    @Override
    public void call(List<String> parameterValues) throws InvalidDefinitionException {

        LocalObjectHolder localMemory = new LocalObjectHolder();

        if (parameterValues.size() != parameters.size()) {
            throw new IllegalArgumentException("funkciq " + getName() + " ima " + parameters.size() + " na broi parametri, no ti q vikash s " + parameterValues.size());
        }

        // construct local variables from parameters
        for (int i = 0; i < parameters.size(); i++) {
            Variable v = new Variable(parameters.get(i), parameterValues.get(i));
            localMemory.addObject(v);
        }

        processBody(getBody(), localMemory);
    }

    @Override
    public void call(ObjectHolder callerMemory) throws InvalidDefinitionException {

        LocalObjectHolder localMemory = new LocalObjectHolder();

        // get only the variables from the callers memory
        for (String param : parameters) {
            List<CliObject> objects = callerMemory.getObjectByName(param);
            if (objects.isEmpty()) {
                throw new IllegalArgumentException("funkciq " + getName() + " nqma stoinost za parametur " + param);
            }

            for (CliObject obj : objects) {
                if (obj instanceof Variable) {
                    localMemory.addObject(obj);
                    break;
                }
            }
        }

        processBody(getBody(), localMemory);
    }

    @Override
    public String toString() {
        return Keywords.FUNCTION + " [" + getName() + ", parametri " + getParameters() + "]";
    }

    @Override
    protected boolean validate() {
        return true;
    }

    @Override
    public int getBlockSize() {
        return body.size();
    }
}
