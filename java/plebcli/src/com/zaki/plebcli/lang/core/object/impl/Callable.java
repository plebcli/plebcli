package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;

import java.util.List;

public interface Callable {
    void call(ObjectHolder localMemory) throws InvalidDefinitionException;
    void call(List<String> parameterValues) throws InvalidDefinitionException;
}
