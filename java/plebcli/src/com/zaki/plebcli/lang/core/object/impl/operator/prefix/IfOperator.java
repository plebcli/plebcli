package com.zaki.plebcli.lang.core.object.impl.operator.prefix;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.lang.CliBoolean;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.expression.ExpressionEvaluator;
import com.zaki.plebcli.lang.core.object.impl.Block;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.lang.core.object.impl.base.Void;
import com.zaki.plebcli.lang.core.object.impl.operator.Operator;
import com.zaki.plebcli.util.CliUtils;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IfOperator extends Operator implements Block {

    private List<Pair<String, Stack<String>>> conditions;

    public IfOperator(String expression, Stack<String> body) throws InvalidDefinitionException {
        super(Keywords.IF);
        build(expression, body);
    }

    private void build(String expression, Stack<String> body) throws InvalidDefinitionException {
        // the expression is actually the first condition
        // each condition has expression and block which has to be executed after it
        // find each expression paired with block and put it in the conditions list
        this.conditions = new ArrayList<>();

        // check if we have { at the end of the if expression
        if (!expression.endsWith(CliUtils.OPEN_BODY)) {
            throw new InvalidDefinitionException(expression);
        }
        expression = expression.substring(0, expression.length() - 1).trim();

        int currentIndex = 0;
        // ok so first parse the first if statement
        Stack<String> ifBlock = getNextBlock(body, currentIndex);
        currentIndex += ifBlock.size() + 1;
        conditions.add(new Pair<>(expression, ifBlock));

        boolean hasMoreExpressions = true;
        while (hasMoreExpressions) {

            String currentLine = body.get(currentIndex);

            boolean potentiallyNotOperator = false;
            if (!currentLine.endsWith(CliUtils.OPEN_BODY)) {
                potentiallyNotOperator = true;
            } else {
                currentLine = currentLine.substring(0, currentLine.length() - 1).trim();
            }

            String exp = null;
            boolean isOperator = false;
            if (currentLine.startsWith(Keywords.ELSE_IF)) {
                exp = CliUtils.removeInputPart(currentLine, Keywords.ELSE_IF);
                isOperator = true;
            } else if (currentLine.startsWith(Keywords.ELSE)) {
                // if we have 'else' operator then we do not need expression for this operator
                if (!currentLine.equals(Keywords.ELSE)) {
                    throw new InvalidDefinitionException(currentLine);
                }
                exp = Keywords.TRUE;
                isOperator = true;
            } else {
                hasMoreExpressions = false;
            }

            if (isOperator && potentiallyNotOperator) {
                throw new InvalidDefinitionException(currentLine);
            }

            if (hasMoreExpressions) {
                currentIndex++;
                Stack<String> block = getNextBlock(body, currentIndex);
                currentIndex += block.size() + 1;
                conditions.add(new Pair<>(exp, block));

                if (currentIndex == body.size()) {
                    hasMoreExpressions = false;
                }
            }
        }
    }

    @Override
    public Primitive operate(LocalObjectHolder memory) throws InvalidDefinitionException {

        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        for (Pair<String, Stack<String>> condition : conditions) {
            // evaluate each condition and the first one to evaluate to Keywords.TRUE will be executed
            if (CliBoolean.parseBoolean(evaluator.evaluate(memory, condition.getKey()).toString())) {
                return CliUtils.processBlock(memory, condition.getValue());
            }
        }

        return new Void();
    }

    @Override
    public int getBlockSize() {

        int blockSize = 0;

        // add 1 because we do not count } as part of the block
        for (Pair<String, Stack<String>> condition : conditions) {
            blockSize += condition.getValue().size() + 1;
        }

        // add conditions.size() - 1 because we did not count the expressions of the operators as part of the blocks
        return blockSize + conditions.size() - 1;
    }

    private Stack<String> getNextBlock(Stack<String> userInput, int bodyIdx) throws InvalidDefinitionException {

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

        return body;
    }
}
