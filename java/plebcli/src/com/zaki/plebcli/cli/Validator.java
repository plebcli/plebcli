package com.zaki.plebcli.cli;

import java.util.Stack;

public class Validator {

    public boolean validate(Stack<String> userInput) {

        int count = 0;

        for (String s : userInput) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '{' || s.charAt(i) == '(') {
                    count++;
                } else if (s.charAt(i) == '}' || s.charAt(i) == ')') {
                    count--;
                }
            }
        }

        return count == 0;
    }
}
