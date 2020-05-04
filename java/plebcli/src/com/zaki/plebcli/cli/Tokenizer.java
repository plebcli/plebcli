package com.zaki.plebcli.cli;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;
import com.zaki.plebcli.lang.core.object.impl.Function;
import com.zaki.plebcli.lang.core.object.impl.Operator;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.lang.core.object.impl.operator.OperatorBuilder;
import com.zaki.plebcli.util.CliUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tokenizer {

    private static final String SPACE = " ";

    private static final String COMMA = ",";

    private static final String OPEN_BODY = "{";

    private static final String CLOSE_BODY = "}";

    private static final String PARAMETERS_START = "(";

    private static final String PARAMETERS_END = ")";

    public Tokenizer() {
        CliUtils.noop();
    }

    public List<CliObject> getObject(Stack<String> userInput, ObjectHolder currentObjectHolder) throws InvalidDefinitionException {

        List<CliObject> result = new ArrayList<>();
        CliObject obj = null;
        ObjectType type = null;

        int skip = 0;
        for (int i = 0; i < userInput.size(); i++) {

            // ignore whitespace
            if (userInput.get(i).trim().isEmpty()) {
                continue;
            }

            boolean existing = false;

            if (skip != 0) {
                skip--;
                continue;
            }
            String s = userInput.get(i).trim();
            // check for defining something
            if (s.startsWith(Keywords.DEFINE)) {
                s = removeInputPart(s, Keywords.DEFINE);

                // check for variable or function
                if (s.startsWith(Keywords.VARIABLE)) {
                    s = removeInputPart(s, Keywords.VARIABLE);
                    obj = defineVariable(s, currentObjectHolder);
                    type = ObjectType.VARIABLE;
                } else if (s.startsWith(Keywords.FUNCTION)) {
                    s = removeInputPart(s, Keywords.FUNCTION);
                    obj = defineFunction(s, i + 1, userInput);
                    // skip body size lines + 1 closing bracket
                    skip = ((Function) obj).getBody().size() + 1;
                    type = ObjectType.FUNCTION;
                } else {
                    throw new InvalidDefinitionException("Nevalidna stoinost " + s);
                }
            } else {
                // check for operators
                boolean foundOperator = false;

                // check for prefix operators
                for (String operatorName : Keywords.getPrefixOperators()) {
                    if (s.startsWith(operatorName)) {
                        obj = processPrefixOperator(s, userInput, i + 1);
                        skip = ((Operator) obj).getBody().size() + 1;
                        type = ObjectType.OPERATOR;
                        foundOperator = true;
                        break;
                    }
                }

                if (!foundOperator) {
                    List<CliObject> objects = getObjectByName(s, currentObjectHolder);
                    if (objects == null || objects.isEmpty()) {
                        throw new InvalidDefinitionException("Nevalidna stoinost " + s);
                    }

                    for (CliObject o : objects) {
                        System.out.println(o.toString());
                    }

                    existing = true;
                }
            }

            if (!existing && obj != null && type != null) {
                currentObjectHolder.addObject(type, obj);
            }
            if (obj != null) {
                result.add(obj);
            }
        }

        return result;
    }

    private Operator processPrefixOperator(String s, Stack<String> userInput, int bodyIdx) throws InvalidDefinitionException {

        // get operator name
        int nameSpaceIdx = s.indexOf(SPACE);
        if (nameSpaceIdx == -1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }
        String name = s.substring(0, nameSpaceIdx);
        s = s.substring(nameSpaceIdx).trim();

        // expected is open body
        int openBodyIdx = s.indexOf(OPEN_BODY);
        if (openBodyIdx == -1 || openBodyIdx != s.length() - 1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        String expression = s.substring(0, openBodyIdx).trim();

        Stack<String> body = new Stack<>();

        boolean closedBody = false;
        while (bodyIdx != userInput.size()) {
            String nextLine = userInput.get(bodyIdx);
            if (nextLine.equals(CLOSE_BODY)) {
                closedBody = true;
                break;
            } else {
                if (!nextLine.trim().isEmpty()) {
                    body.add(nextLine);
                }
                bodyIdx++;
            }
        }

        if (!closedBody) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + userInput);
        }

        return OperatorBuilder.buildPrefix(name, expression, body);
    }

    private List<CliObject> getObjectByName(String s, ObjectHolder currentObjectHolder) {

        List<CliObject> result = currentObjectHolder.getObjectByName(s);

        if (!(currentObjectHolder instanceof GlobalObjectHolder)) {
            result.addAll(GlobalObjectHolder.getInstance().getObjectByName(s));
        }

        return result;
    }

    private String removeInputPart(String s, String part) {
        return s.substring(part.length()).trim();
    }

    private Variable defineVariable(String s, ObjectHolder current) throws InvalidDefinitionException {

        // get variable name
        int nameSpaceIdx = s.indexOf(SPACE);
        if (nameSpaceIdx == -1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }
        String name = s.substring(0, nameSpaceIdx);
        s = s.substring(nameSpaceIdx).trim();

        String value;

        // expected is assigning operation
        if (s.startsWith(Keywords.ASSIGN)) {
            // get the value or expression
            s = removeInputPart(s, Keywords.ASSIGN);
            value = new ExpressionEvaluator().evaluate(current, s);
        } else {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        return new Variable(name, value);
    }

    private CliObject defineFunction(String s, int bodyIdx, Stack<String> userInput) throws InvalidDefinitionException {

        // get function name
        int nameSpaceIdx = s.indexOf(SPACE);
        if (nameSpaceIdx == -1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }
        String name = s.substring(0, nameSpaceIdx);
        s = s.substring(nameSpaceIdx).trim();

        List<String> parameters = new ArrayList<>();

        // expected are parameters
        if (s.startsWith(Keywords.PARAMETERS)) {
            s = removeInputPart(s, Keywords.PARAMETERS);

            // get all parameters separated by comma until opening the body
            int openBodyIdx = s.indexOf(OPEN_BODY);
            if (openBodyIdx == -1 || openBodyIdx != s.length() - 1) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + s);
            }

            String parametersStr = s.substring(0, openBodyIdx).trim();

            if (!parametersStr.startsWith(PARAMETERS_START) || !parametersStr.endsWith(PARAMETERS_END)) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + parametersStr);
            }

            parametersStr = parametersStr.substring(1, parametersStr.length() - 1);

            if (!"".equals(parametersStr)) {
                String[] parametersPart = parametersStr.split(COMMA);

                for (String p : parametersPart) {
                    parameters.add(p);
                }
            }
        } else {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        // expected is body
        if (bodyIdx == -1 || bodyIdx == userInput.size()) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + userInput);
        }

        Stack<String> body = new Stack<>();

        boolean closedBody = false;
        int openBrackets = 1;
        while (bodyIdx != userInput.size()) {
            String nextLine = userInput.get(bodyIdx);
            if (nextLine.equals(CLOSE_BODY)) {
                openBrackets--;
                if (openBrackets == 0){
                    closedBody = true;
                    break;
                } else {
                    body.add(nextLine);
                    bodyIdx++;
                }
            } else {
                if (!nextLine.trim().isEmpty()) {
                    body.add(nextLine);
                }
                if (nextLine.contains(OPEN_BODY)) {
                    openBrackets++;
                }
                bodyIdx++;
            }
        }

        if (!closedBody) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + userInput);
        }

        return new Function(name, parameters, body);
    }
}
