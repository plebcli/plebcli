package com.zaki.plebcli.lang.core.object;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;

import java.util.Objects;

public abstract class CliObject {

    private final ObjectType objectType;

    private final String name;

    public CliObject(String name, ObjectType objectType) throws InvalidDefinitionException {
        if (validate() && Keywords.getKeywords().contains(name)) {
            throw new InvalidDefinitionException(name + " e zapazena kliuchova duma");
        }
        this.name = name;
        this.objectType = objectType;
    }

    public String getName() {
        return name;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CliObject cliObject = (CliObject) o;
        return Objects.equals(name, cliObject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    protected abstract boolean validate();
}
