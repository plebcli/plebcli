package com.zaki.plebcli.lang.core.object;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.Keywords;

import java.util.Objects;

public abstract class CliObject {

    private final String name;

    public CliObject(String name) throws InvalidDefinitionException {
        if (validate() && Keywords.getKeywords().contains(name)) {
            throw new InvalidDefinitionException(name + " e zapazena kliuchova duma");
        }
        this.name = name;
    }

    public String getName() {
        return name;
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
