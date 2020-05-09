package com.zaki.plebcli.lang.core.object.impl.base;

import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;

public class Primitive extends CliObject {
    public Primitive(String name) {
        super(name, ObjectType.PRIMITIVE);
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    protected boolean validate() {
        return false;
    }
}
