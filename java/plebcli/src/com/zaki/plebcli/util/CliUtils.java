package com.zaki.plebcli.util;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.Callable;
import com.zaki.plebcli.lang.core.object.impl.Operator;

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

    public static void processBlock(ObjectHolder memory, Stack<String> block) throws InvalidDefinitionException {

        Tokenizer localTokenizer = new Tokenizer();
        List<CliObject> localObjects = localTokenizer.getObject(block, memory);

        for (CliObject o : localObjects) {
            if (o instanceof Callable) {
                ((Callable) o).call(memory);
            } else if (o instanceof Operator) {
                ((Operator) o).operate(memory);
            }
        }
    }
}
