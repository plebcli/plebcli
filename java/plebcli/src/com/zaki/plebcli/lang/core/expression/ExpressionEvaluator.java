package com.zaki.plebcli.lang.core.expression;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.fn.Function;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;

import java.util.Stack;

public class ExpressionEvaluator {

    public Primitive evaluate(LocalObjectHolder memory, String expression) throws InvalidDefinitionException {

        Stack<String> input = new Stack<>();
        input.add(expression);

        // TODO
        CliObject obj = new Tokenizer().getObject(input, memory).get(0);

        Primitive result = null;

        if (obj.getObjectType() == ObjectType.PRIMITIVE) {
            result = (Primitive) obj;
        } else if (obj.getObjectType() == ObjectType.VARIABLE) {
            result = new Primitive(((Variable) obj).getValue());
        } else if (obj.getObjectType() == ObjectType.FUNCTION) {
            result = ((Function) obj).call(memory.clone());
        } else if (obj instanceof Operator) {
            result = ((Operator) obj).operate(memory.clone());
        }

        return result;
    }
}
