package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.LocalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.CliObject;

import java.util.List;
import java.util.Stack;

public interface Callable {
    void call(ObjectHolder localMemory) throws InvalidDefinitionException;
    void call(List<String> parameterValues) throws InvalidDefinitionException;

    default void processBody(Stack<String> body, ObjectHolder localMemory) throws InvalidDefinitionException {

        Tokenizer localTokenizer = new Tokenizer();
        List<CliObject> localObjects = localTokenizer.getObject(body, localMemory);

        for (CliObject o : localObjects) {
            if (o instanceof Callable) {
                ((Callable) o).call(localMemory);
            }
        }
    }
}
