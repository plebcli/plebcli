package com.zaki.plebcli.lang.core.object.impl.operator;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.Keywords;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.Callable;
import com.zaki.plebcli.lang.core.object.impl.fn.Function;
import com.zaki.plebcli.util.CliUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallOperator extends Operator {

    private Callable f;
    private List<String> p;

    public CallOperator(String callLine) throws InvalidDefinitionException {
        super(Keywords.CALL);
        build(callLine);
    }

    @Override
    public void operate(ObjectHolder memory) throws InvalidDefinitionException {
        f.call(memory);
    }

    private void build(String callLine) throws InvalidDefinitionException {

        // get function name to call
        int functionNameIdx = callLine.indexOf(CliUtils.SPACE);
        if (functionNameIdx == -1 || functionNameIdx == callLine.length() - 1) {
            throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
        }

        String functionName = callLine.substring(0, functionNameIdx).trim();
        callLine = callLine.substring(functionNameIdx).trim();

        this.p = new ArrayList<>();

        // expected are parameters
        if (callLine.startsWith(Keywords.PARAMETERS)) {
            callLine = CliUtils.removeInputPart(callLine, Keywords.PARAMETERS);

            if (!callLine.startsWith(CliUtils.PARAMETERS_START) || !callLine.endsWith(CliUtils.PARAMETERS_END)) {
                throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
            }

            String parametersStr = callLine.substring(1, callLine.length() - 1).trim();

            if (!"".equals(parametersStr)) {
                String[] parametersPart = parametersStr.split(CliUtils.COMMA);
                p.addAll(Arrays.asList(parametersPart));
            }
        } else {
            throw new InvalidDefinitionException("Nevalidna stoinost " + callLine);
        }

        List<CliObject> functions = GlobalObjectHolder.getInstance().getObjectByName(functionName);
        if (!functions.isEmpty()) {
            for (CliObject obj : functions) {
                if (obj instanceof Function) {
                    this.f = (Function) obj;
                    break;
                }
            }
        }

        if (this.f == null) {
            throw new InvalidDefinitionException("funkciq " + functionName + " ne e definirana");
        }
    }
}
