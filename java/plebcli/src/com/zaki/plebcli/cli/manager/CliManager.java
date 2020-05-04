package com.zaki.plebcli.cli.manager;

import com.sun.istack.internal.NotNull;
import com.zaki.plebcli.cli.Validator;
import com.zaki.plebcli.cli.exception.InvalidDefinitionException;
import com.zaki.plebcli.cli.in.Parser;
import com.zaki.plebcli.cli.Tokenizer;
import com.zaki.plebcli.cli.memory.GlobalObjectHolder;
import com.zaki.plebcli.cli.memory.ObjectHolder;
import com.zaki.plebcli.cli.out.Responder;
import com.zaki.plebcli.lang.core.object.CliObject;
import com.zaki.plebcli.lang.core.object.impl.Function;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CliManager {

    private static final String PLEB = ".pleb";

    private Parser parser;
    private Tokenizer tokenizer;
    private Responder responder;
    private Validator validator;

    private boolean running;
    private boolean debug;

    private Stack<String> stackTrace;

    public CliManager() {
        parser = new Parser();
        tokenizer = new Tokenizer();
        validator = new Validator();
        responder = new Responder();
        stackTrace = new Stack<>();
    }

    public void start(boolean debug) {

        setDebugging(debug);
        if (!running) {
            running = true;
            manage();
        }
    }

    private void setDebugging(boolean debug) {
        this.debug = debug;
    }

    public void start() {
        start(false);
    }

    private void manage() {
        while(running) {
            try {
                getInput();
            } catch (Exception e) {
                responder.respond(e.getMessage());
                stackTrace.clear();
            }
        }
    }

    public void startFromFile(@NotNull File f) throws IOException, InvalidDefinitionException {

        // open file
        if (!f.getName().endsWith(PLEB)) {
            throw new IllegalArgumentException("File is not a " + PLEB + " file");
        }

        Stack<String> input = new Stack<>();

        try (Stream<String> lines = Files.lines(Paths.get(f.toURI()))) {
            lines.forEach(line -> input.add(line.trim()));
        }

        // this will run once only
        processInput(input);

        // call main function
        List<CliObject> mainObjects = GlobalObjectHolder.getInstance().getObjectByName("main");

        boolean isMainCalled = false;
        for (CliObject main : mainObjects) {
            if (main instanceof Function) {
                ((Function) main).call(Collections.emptyList());
                isMainCalled = true;
                break;
            }
        }

        if (!isMainCalled) {
            throw new InvalidDefinitionException("programata ne moje da startira bez main funkciq");
        }
    }

    private void getInput() throws InvalidDefinitionException {

        // read user input
        String nextLine = parser.parse();
        if (!nextLine.trim().isEmpty()) {
            stackTrace.add(nextLine.trim());
            processInput(stackTrace);
        }
    }

    private void processInput(Stack<String> stackTrace) throws InvalidDefinitionException {

        // validate
        if (validator.validate(stackTrace)) {
            List<CliObject> obj = tokenizer.getObject(stackTrace, GlobalObjectHolder.getInstance());
            if (debug) {
                printStackTrace(stackTrace);
            }

            // if such token exists then we can evaluate and get the value
            if (obj != null) {
                for (CliObject o : obj) {
                    responder.respond(o.toString());
                }
            }

            stackTrace.clear();
        }
    }

    private void printStackTrace(Stack<String> stackTrace) {
        responder.respond("User input is");
        for (String s : stackTrace) {
            responder.respond(s);
        }
    }
}
