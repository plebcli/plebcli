package com.zaki.plebcli.lang;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.lang.core.object.impl.base.Primitive;
import com.zaki.plebcli.util.CliUtils;

public final class CliBoolean {

    private CliBoolean() {

    }

    public static boolean parseBoolean(String toParse) throws InvalidDefinitionException {
        if (Keywords.TRUE.equals(toParse)) {
            return true;
        } else if (Keywords.FALSE.equals(toParse)) {
            return false;
        } else {
            throw new InvalidDefinitionException(toParse);
        }
    }

    public static Primitive getCliBoolean(boolean bool) {
        return bool ? new Primitive(Keywords.TRUE) : new Primitive(Keywords.FALSE);
    }
}
