package com.zaki.plebcli.lang.core.object.impl;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;

import java.util.List;

public interface Callable {
    Primitive call(ObjectHolder localMemory) throws InvalidDefinitionException;
    Primitive call(List<String> parameterValues) throws InvalidDefinitionException;
}
