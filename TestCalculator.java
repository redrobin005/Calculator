package com.bham.pij.assignments.calculator;
import java.util.Scanner;
// @author Anwin Robin

public class TestCalculator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String      input;
        Calculator calc = new Calculator();
        while (true) {
            System.out.println("Enter an expression");
            input = in.nextLine();
            calc.evaluate(input);
            System.out.println(calc.getCurrentValue());

            input = in.nextLine();
            memory(input);
            calc.getHistoryValue(0);

        }
    }

    public static void memory(String expression) {
        Calculator calc = new Calculator();
        if (expression.equals("m")) {
            calc.setMemoryValue(calc.value);
            System.out.println("Stored");
        } else if (expression.equals("mr")) {
            System.out.println(calc.memoryValue);
        } else if (expression.equals("c")) {
            calc.clearMemory();
        } else if (expression.equals("h")) {
            calc.displayHistory();
        }
    }
}

