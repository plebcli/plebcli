package com.zaki.plebcli.lang.core.object.impl.fn;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.Block;
import com.zaki.plebcli.lang.core.object.impl.Callable;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.util.CliUtils;

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
    public Primitive call(List<String> parameterValues, LocalObjectHolder memory) throws InvalidDefinitionException {

        if (parameterValues.size() != parameters.size()) {
            throw new IllegalArgumentException("funkciq " + getName() + " ima " + parameters.size() + " na broi parametri, no ti q vikash s " + parameterValues.size());
        }

        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        // construct local variables from parameters
        for (int i = 0; i < parameters.size(); i++) {
            Variable v = new Variable(parameters.get(i), evaluator.evaluate(memory, parameterValues.get(i)).toString());
            memory.addObject(v);
        }

        return CliUtils.processBlock(memory, getBody());
    }

    @Override
    public Primitive call(LocalObjectHolder localMemory) throws InvalidDefinitionException {

        // get only the variables from the callers memory 
        for (String param : parameters) {
            List<CliObject> objects = localMemory.getObjectByName(param);
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

        return CliUtils.processBlock(localMemory, getBody());
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

        // add 1 because we do not count } as part of the block
        return body.size() + 1;
    }
}
