package com.zaki.plebcli.cli.in;

import java.util.Scanner;

public class Parser {

    private Scanner sc;

    public Parser() {
        sc = new Scanner(System.in);
    }

    public String parse() {
        return sc.nextLine();
    }
}
