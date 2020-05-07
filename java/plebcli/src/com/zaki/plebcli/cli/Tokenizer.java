package com.zaki.plebcli.cli;

import com.sun.istack.internal.NotNull;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.fn.Function;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.Variable;
import com.zaki.plebcli.lang.core.object.impl.Block;
import com.zaki.plebcli.lang.core.object.impl.operator.OperatorBuilder;
import com.zaki.plebcli.util.CliUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private static final String NUMBER_REGEX = "^[0-9]+$";

    private static final String STRING_REGEX = "^\"(.*?)\"$";

    public Tokenizer() {
        CliUtils.noop();
    }

    public List<CliObject> getObject(@NotNull Stack<String> userInput, @NotNull ObjectHolder currentObjectHolder) throws InvalidDefinitionException {

        List<CliObject> result = new ArrayList<>();
        CliObject obj;

        int skip = 0;
        for (int i = 0; i < userInput.size(); i++) {

            // ignore whitespace
            if (userInput.get(i).trim().isEmpty()) {
                continue;
            }

            if (skip != 0) {
                skip--;
                continue;
            }
            String s = userInput.get(i).trim();

            obj = getPrimitive(s);

            if (obj == null) {
                obj = getDefine(s, userInput, i, currentObjectHolder);
            }
            if (obj == null) {
                obj = getOperator(s, userInput, i, currentObjectHolder);
            }
            boolean isReference = false;
            if (obj == null) {
                int prevSize = result.size();
                result.addAll(getFromObjectHolder(s, currentObjectHolder));
                isReference = result.size() != prevSize;
            }

            if (obj instanceof Block) {
                skip = ((Block) obj).getBlockSize();
            }

            if (obj != null) {
                result.add(obj);
            } else {
                if (!isReference) {
                    throw new InvalidDefinitionException(s);
                }
            }
        }

        for (CliObject o : result) {

            boolean existing = true;

            List<CliObject> objects = getObjectByName(o.getName(), currentObjectHolder);
            if (objects == null || objects.isEmpty()) {
                existing = false;
            }

            if (!existing) {
                currentObjectHolder.addObject(o);
            }
        }

        return result;
    }

    private List<CliObject> getFromObjectHolder(String s, ObjectHolder currentObjectHolder) {
        return currentObjectHolder.getObjectByName(s);
    }

    private CliObject getOperator(String s, Stack<String> userInput, int i, ObjectHolder currentObjectHolder) throws InvalidDefinitionException {

        CliObject obj = null;

        // check for prefix operators
        for (String operatorName : OperatorBuilder.getPrefixOperators()) {
            if (s.startsWith(operatorName)) {
                s = s.substring(operatorName.length()).trim();
                obj = processPrefixOperator(operatorName, s, userInput, i + 1);
                break;
            }
        }

        if (obj == null) {
            // check for infix operators
            for (String operatorName : OperatorBuilder.getInfixOperators()) {
                if (s.contains(operatorName) && !s.startsWith(operatorName) && !s.endsWith(operatorName)) {
                    obj = processInfixOperator(operatorName, s);
                    break;
                }
            }
        }

        return obj;
    }

    private CliObject getDefine(String s, Stack<String> userInput, int currentIteration, ObjectHolder currentObjectHolder) throws InvalidDefinitionException {

        CliObject obj = null;

        if (s.startsWith(Keywords.DEFINE)) {
            s = CliUtils.removeInputPart(s, Keywords.DEFINE);

            // check for variable or function
            if (s.startsWith(Keywords.VARIABLE)) {
                s = CliUtils.removeInputPart(s, Keywords.VARIABLE);
                obj = defineVariable(s, currentObjectHolder);
            } else if (s.startsWith(Keywords.FUNCTION)) {
                s = CliUtils.removeInputPart(s, Keywords.FUNCTION);
                obj = defineFunction(s, currentIteration + 1, userInput);
            } else {
                throw new InvalidDefinitionException("Nevalidna stoinost " + s);
            }
        }

        return obj;
    }

    private CliObject getPrimitive(@NotNull String s) throws InvalidDefinitionException {

        Primitive result = null;

        // Primitives are booleans, numbers and strings
        if (s.equals(Keywords.TRUE) || s.equals(Keywords.FALSE)) {
            result = new Primitive(s);
        }

        if (result == null) {
            Pattern numberPattern = Pattern.compile(NUMBER_REGEX);
            Pattern stringPattern = Pattern.compile(STRING_REGEX);
            Matcher numberMatcher = numberPattern.matcher(s);
            Matcher stringMatcher = stringPattern.matcher(s);
            if (numberMatcher.find() || stringMatcher.find()) {
                result = new Primitive(s);
            }
        }

        return result;
    }

    private CliObject processInfixOperator(String name, String currentLine) throws InvalidDefinitionException {
        return OperatorBuilder.buildInfixOperatorByName(name, currentLine);
    }

    private Operator processPrefixOperator(String name, String s, Stack<String> userInput, int bodyIdx) throws InvalidDefinitionException {
        return OperatorBuilder.buildPrefixOperatorByName(name, s, clearProcessedInput(userInput, bodyIdx));
    }

    private Stack<String> clearProcessedInput(Stack<String> userInput, int bodyIdx) {
        Stack<String> clearedInput = new Stack<>();

        for (int i = bodyIdx; i < userInput.size(); i++) {
            clearedInput.add(userInput.get(i));
        }

        return clearedInput;
    }

    private List<CliObject> getObjectByName(String s, ObjectHolder currentObjectHolder) {

        List<CliObject> result = currentObjectHolder.getObjectByName(s);

        if (!(currentObjectHolder instanceof GlobalObjectHolder)) {
            result.addAll(GlobalObjectHolder.getInstance().getObjectByName(s));
        }

        return result;
    }

    private Variable defineVariable(String s, ObjectHolder current) throws InvalidDefinitionException {

        // get variable name
        int nameSpaceIdx = s.indexOf(CliUtils.SPACE);
        if (nameSpaceIdx == -1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }
        String name = s.substring(0, nameSpaceIdx);
        s = s.substring(nameSpaceIdx).trim();

        String value;

        // expected is assigning operation
        if (s.startsWith(Keywords.ASSIGN)) {
            // get the value or expression
            s = CliUtils.removeInputPart(s, Keywords.ASSIGN);
            value = new ExpressionEvaluator().evaluate(current, s).toString();
        } else {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }

        return new Variable(name, value);
    }

    private CliObject defineFunction(String s, int bodyIdx, Stack<String> userInput) throws InvalidDefinitionException {

        // get function name
        int nameSpaceIdx = s.indexOf(CliUtils.SPACE);
        if (nameSpaceIdx == -1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + s);
        }
        String name = s.substring(0, nameSpaceIdx);
        s = s.substring(nameSpaceIdx).trim();

        List<String> parameters = new ArrayList<>();

        // expected are parameters
        if (s.startsWith(Keywords.PARAMETERS)) {
            s = CliUtils.removeInputPart(s, Keywords.PARAMETERS);

            // get all parameters separated by comma until opening the body
            int openBodyIdx = s.indexOf(CliUtils.OPEN_BODY);
            if (openBodyIdx == -1 || openBodyIdx != s.length() - 1) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + s);
            }

            String parametersStr = s.substring(0, openBodyIdx).trim();

            if (!parametersStr.startsWith(CliUtils.PARAMETERS_START) || !parametersStr.endsWith(CliUtils.PARAMETERS_END)) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + parametersStr);
            }

            parametersStr = parametersStr.substring(1, parametersStr.length() - 1);

            if (!"".equals(parametersStr)) {
                String[] parametersPart = parametersStr.split(CliUtils.COMMA);

                parameters.addAll(Arrays.asList(parametersPart));
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
            if (nextLine.equals(CliUtils.CLOSE_BODY)) {
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
                if (nextLine.contains(CliUtils.OPEN_BODY)) {
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
