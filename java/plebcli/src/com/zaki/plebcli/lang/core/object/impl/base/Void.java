package com.zaki.plebcli.lang.core.object.impl.base;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;

public class Void extends Primitive {
    public Void() throws InvalidDefinitionException {
        super(Keywords.VOID);
    }
}
