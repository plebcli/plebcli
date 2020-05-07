package com.zaki.plebcli.util;

import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.base.Void;
import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.Callable;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;
import com.zaki.plebcli.lang.core.object.impl.operator.infix.InfixOperator;

import java.util.List;
import java.util.Stack;

public final class CliUtils {

    public static final String SPACE = " ";

    public static final String COMMA = ",";

    public static final String OPEN_BODY = "{";

    public static final String CLOSE_BODY = "}";

    public static final String PARAMETERS_START = "(";

    public static final String PARAMETERS_END = ")";

    public static void noop() {
        ;
    }

    public static String removeInputPart(String s, String part) {
        return s.substring(part.length()).trim();
    }

    public static Primitive processBlock(ObjectHolder memory, Stack<String> block) throws InvalidDefinitionException {

        Tokenizer localTokenizer = new Tokenizer();
        List<CliObject> localObjects = localTokenizer.getObject(block, memory);

        for (CliObject o : localObjects) {
            Primitive result = null;
            if (o instanceof Callable) {
                result = ((Callable) o).call(memory);
            } else if (o instanceof Operator) {
                result = ((Operator) o).operate(memory);
            }
            if (!(result instanceof Void)) {
                return result;
            }
        }

        return new Void();
    }
}
