package com.zaki.plebcli.cli.memory;

public class GlobalObjectHolder extends ObjectHolder {

    private GlobalObjectHolder() {

    }

    private static GlobalObjectHolder instance;

    public synchronized static GlobalObjectHolder getInstance() {

        if (instance == null) {
            instance = new GlobalObjectHolder();
        }

        return  instance;
    }
}
