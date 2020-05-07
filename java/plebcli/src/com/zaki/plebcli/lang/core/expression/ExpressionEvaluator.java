package com.zaki.plebcli.lang.core.expression;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.Primitive;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.lang.core.object.impl.fn.Function;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.util.Stack;

public class ExpressionEvaluator {

    public String evaluate(ObjectHolder memory, String expression) throws InvalidDefinitionException {

        Stack<String> input = new Stack();
        input.add(expression);

        // TODO
        CliObject obj = new Tokenizer().getObject(input, memory).get(0);

        String result = null;

        if (obj.getObjectType() == ObjectType.PRIMITIVE) {
            result = obj.getName();
        } else if (obj.getObjectType() == ObjectType.VARIABLE) {
            result = ((Variable) obj).getValue();
        } else if (obj.getObjectType() == ObjectType.FUNCTION) {
            // TODO
            //result = ((Function) obj).call(memory);
        } else if (obj instanceof InfixOperator) {

            // TODO
            result = ((InfixOperator) obj).operateWithResult(memory);
        }

        return result;
    }
}
