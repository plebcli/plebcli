package com.zaki.plebcli.cli.memory;

import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;

import java.util.Map;
import java.util.Set;

public class LocalObjectHolder extends ObjectHolder implements Cloneable {

    @Override
    public LocalObjectHolder clone() {

        LocalObjectHolder newInstance = new LocalObjectHolder();

        for (Map.Entry<ObjectType, Set<CliObject>> objects : getObjects().entrySet()) {
            for (CliObject obj : objects.getValue()) {
                newInstance.addObject(obj);
            }
        }

        return newInstance;
    }
}
