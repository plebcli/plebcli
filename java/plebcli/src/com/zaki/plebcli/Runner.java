package com.zaki.plebcli;

import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.manager.CliManager;

import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException, InvalidDefinitionException {
        CliManager cliManager = new CliManager();
        //cliManager.start(false);
        cliManager.startFromFile(new File("test.pleb"));
    }
}
