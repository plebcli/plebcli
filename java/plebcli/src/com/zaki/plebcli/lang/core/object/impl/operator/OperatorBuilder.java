package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.Function;
import com.zaki.plebcli.lang.core.object.impl.Operator;
import com.zaki.plebcli.lang.core.object.impl.operator.in.In;
import com.zaki.plebcli.lang.core.object.impl.operator.out.Out;
import com.zaki.plebcli.util.CliUtils;

import java.util.*;

public class OperatorBuilder {

    private static final String SPACE = " ";

    private static final Map<String, Class> prefixOperators = new HashMap<>();

    static {
        prefixOperators.put(Keywords.IF, If.class);
        prefixOperators.put(Keywords.ELSE, Else.class);
        prefixOperators.put(Keywords.ELSE_IF, If.class);
        prefixOperators.put(Keywords.CALL, Call.class);
    }

    private OperatorBuilder() {

    }

    public static Operator buildPrefix(String name, String expression, Stack<String> body) throws InvalidDefinitionException {
        Operator result = null;

        if (Keywords.IF.equals(name)) {
            result = new If(name, expression, body);
        }

        return result;
    }

    public static Operator buildPrefixOperatorByName(String name, String s, Stack<String> userInput, int bodyIdx) throws InvalidDefinitionException {

        if (Keywords.IF.equals(name)) {
            return buildIf();
        } else if (Keywords.CALL.equals(name)) {
            return buildCall(s);
        } else if (Keywords.OUT.equals(name)) {
            return buildOut(s);
        } else if (Keywords.IN.equals(name)) {
            return buildIIn(s);
        }

        return null;
    }

    private static Operator buildIIn(String s) throws InvalidDefinitionException {

        if (s.contains(SPACE)) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        return new In(s);
    }

    private static Operator buildOut(String s) throws InvalidDefinitionException {
        return new Out(s);
    }

    private static Operator buildIf() {
        return null;
    }

    private static Operator buildCall(String callLine) throws InvalidDefinitionException {
        // get function name to call
        int functionNameIdx = callLine.indexOf(SPACE);
        if (functionNameIdx == -1 || functionNameIdx == callLine.length() - 1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
        }

        String functionName = callLine.substring(0, functionNameIdx).trim();
        callLine = callLine.substring(functionNameIdx).trim();

        List<String> parameters = new ArrayList<>();

        // expected are parameters
        if (callLine.startsWith(Keywords.PARAMETERS)) {
            callLine = CliUtils.removeInputPart(callLine, Keywords.PARAMETERS);

            if (!callLine.startsWith(CliUtils.PARAMETERS_START) || !callLine.endsWith(CliUtils.PARAMETERS_END)) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
            }

            String parametersStr = callLine.substring(1, callLine.length() - 1).trim();

            if (!"".equals(parametersStr)) {
                String[] parametersPart = parametersStr.split(CliUtils.COMMA);

                for (String p : parametersPart) {
                    parameters.add(p);
                }
            }
        } else {
            throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
        }

        Function f = null;
        List<CliObject> functions = GlobalObjectHolder.getInstance().getObjectByName(functionName);
        if (!functions.isEmpty()) {
            for (CliObject obj : functions) {
                if (obj instanceof Function) {
                    f = (Function) obj;
                    break;
                }
            }
        }

        if (f == null) {
            throw new InvalidDefinitionException("funkciq " + functionName + " ne e definirana");
        }

        return new Call(f, parameters);
    }
}
