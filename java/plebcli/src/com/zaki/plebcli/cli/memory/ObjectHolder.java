package com.zaki.plebcli.cli.memory;

import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.ObjectType;

import java.util.*;

public abstract class ObjectHolder {

    private Map<ObjectType, Set<CliObject>> objects;

    public ObjectHolder() {
        this.objects = new HashMap<>();
    }

    public void addObject(ObjectType type, CliObject obj) {
        objects.computeIfAbsent(type, k -> new HashSet<>()).add(obj);
    }

    public List<CliObject> getObjectByName(String s) {

        List<CliObject> result = new ArrayList<>();

        for (Map.Entry<ObjectType, Set<CliObject>> entry : objects.entrySet()) {
            for (CliObject obj : entry.getValue()) {
                if (obj.getName().equals(s)) {
                    result.add(obj);
                }
            }
        }

        return result;
    }
}
