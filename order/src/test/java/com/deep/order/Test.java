package com.deep.order;

import java.util.Stack;

public class Test {

    public static boolean isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else if (ch == ')' || ch == '}' || ch == ']') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (!isMatchingPair(top, ch)) return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char open, char close) {
        isNumber("5");
        return (open == '(' && close == ')') ||
                (open == '[' && close == ']') ||
                (open == '{' && close == '}');
    }

    public static boolean isNumber(String intStr) {
        boolean result = false;
        try {
            Integer.parseInt(intStr);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        System.out.println(result);
        return result;
    }


    public static void main(String[] args) {
//        String input = "{[()]}";
//        System.out.println("Is balanced? " + isBalanced(input)); // true
//
//        String input2 = "({[)]}";
//        System.out.println("Is balanced? " + isBalanced(input2)); // false
        isNumber("hello");

    }
}
