package com.zaki.plebcli.lang;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;

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

    public static String getCliBoolean(boolean bool) {
        return bool ? Keywords.TRUE : Keywords.FALSE;
    }
}
