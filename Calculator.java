package com.bham.pij.assignments.calculator;
// @author Anwin Robin

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public Calculator() {
    }
    public float evaluate(String expression) {

        //evaluate basic and arbitrary calculator
        expressionChecker1(basicRegex, expression);
        if (expressionCheck1 == true) {
            String[] expArray = expression.split(" ");

            String operand1 = expArray[0];
            String operator = expArray[1];
            String operand2 = expArray[2];

            float opNum1 = Float.parseFloat(operand1);
            float opNum2 = Float.parseFloat(operand2);

            if (expArray.length == 3) {
                if (operator.equals("+")) {
                    value = opNum1 + opNum2;
                } else if (operator.equals("-")) {
                    value = opNum1 - opNum2;
                } else if (operator.equals("*")) {
                    value = opNum1 * opNum2;
                } else if (operator.equals("/") && opNum2 != 0) {
                    value = opNum1 / opNum2;
                } else if (opNum2 == 0) {
                    expressionCheck1 = false;
                    value = Float.MIN_VALUE;
                    return value;
                }
                expressionCheck1 = false;
                addToHistory(value);
                return value;
            } else {

                Stack<String> operatorStack = new Stack<String>();
                Stack<String> operandStack = new Stack<String>();

                String oprtr;
                String oprnd2;
                String oprnd1;
                float arbResult;
                String strArbResult;

                for (int i = 0; i < expArray.length; ++i) {

                    if (isOperator(expArray[i])) {
                        // while the stack is not empty and the top of the operator stack has higher precedence
                        // than the current expArray operator
                        while (!operatorStack.isEmpty() && higherPrecedence(operatorStack.peek(), expArray[i])) {
                            // set the the top of the operator stack equal to an operator variable and then pop
                            oprtr = operatorStack.peek();
                            operatorStack.pop();
                            // set the the top 2 values of the operand stack equal to two operand variables and then pop
                            oprnd2 = operandStack.peek();
                            operandStack.pop();
                            oprnd1 = operandStack.peek();
                            operandStack.pop();

                            arbResult = arithmetic(oprnd1, oprnd2, oprtr);
                            strArbResult = String.valueOf(arbResult);
                            operandStack.push(strArbResult);
                        }
                        // if the stack is empty or if the top of the stack has lower precedence than the element
                        operatorStack.push(expArray[i]);
                    }
                    // else assume it's an operand and push onto operand stack
                    else {
                        operandStack.push(expArray[i]);
                    }
                    // complete calculation of popped operator and operands and push result back onto stack
                }
                // pop remaining operator(s) in stack and perform final calculation
                while (!operatorStack.isEmpty()) {
                    oprtr = operatorStack.peek();
                    operatorStack.pop();

                    oprnd2 = operandStack.peek();
                    operandStack.pop();
                    oprnd1 = operandStack.peek();
                    operandStack.pop();
                    arbResult = arithmetic(oprnd1, oprnd2, oprtr);
                    strArbResult = String.valueOf(arbResult);
                    operandStack.push(strArbResult);
                }
                strArbResult = operandStack.peek();
                arbResult = Float.parseFloat(strArbResult);
                value = arbResult;

                expressionCheck1 = false;
                addToHistory(value);
                return value;
            }

        }

        //evaluate operations on memoryValue
        expressionChecker2(memoryRegex, expression);
        if (expressionCheck2 == true) {
            String[] expArray = expression.split(" ");

            String operator = expArray[0];
            String operand2 = expArray[1];

            float opNum2 = Float.parseFloat(operand2);

            if (operator.equals("+")) {
                value = memoryValue + opNum2;
            } else if (operator.equals("-")) {
                value = memoryValue - opNum2;
            } else if (operator.equals("*")) {
                value = memoryValue * opNum2;
            } else if (operator.equals("/") && opNum2 != 0) {
                value = memoryValue / opNum2;
            } else if (opNum2 == 0) {
                expressionCheck2 = false;
                value = Float.MIN_VALUE;
                return value;
            }
            expressionCheck2 = false;
            addToHistory(value);
            return value;
        }


        // evaluate brackets (regex also used to capture 2 bracket groups and operator group this time)
        expressionChecker3(bracketRegex, expression);
        if (expressionCheck3 == true) {

            String[] bracketArray1 = bracket1.split(" ");

            String operand1 = bracketArray1[0];
            String operator1 = bracketArray1[1];
            String operand2 = bracketArray1[2];

            float opNum1 = Float.parseFloat(operand1);
            float opNum2 = Float.parseFloat(operand2);
            float bracketValue1 = 0;

            if (operator1.equals("+")) {
                bracketValue1 = opNum1 + opNum2;
            } else if (operator1.equals("-")) {
                bracketValue1 = opNum1 - opNum2;
            } else if (operator1.equals("*")) {
                bracketValue1 = opNum1 * opNum2;
            } else if (operator1.equals("-") && opNum2 != 0) {
                bracketValue1 = opNum1 / opNum2;
            } else if (opNum2 == 0) {
                expressionCheck3 = false;
                value = Float.MIN_VALUE;
                return value;
            }

            String[] bracketArray2 = bracket2.split(" ");

            String operand3 = bracketArray2[0];
            String operator2 = bracketArray2[1];
            String operand4 = bracketArray2[2];

            float opNum3 = Float.parseFloat(operand3);
            float opNum4 = Float.parseFloat(operand4);
            float bracketValue2 = 0;

            if (operator2.equals("+")) {
                bracketValue2 = opNum3 + opNum4;
            } else if (operator2.equals("-")) {
                bracketValue2 = opNum3 - opNum4;
            } else if (operator2.equals("*")) {
                bracketValue2 = opNum3 * opNum4;
            } else if (operator2.equals("/") && opNum4 != 0) {
                bracketValue2 = opNum3 / opNum4;
            } else if (opNum4 == 0) {
                expressionCheck3 = false;
                value = Float.MIN_VALUE;
                return value;
            }

            if (bracketOp.equals("+")) {
                value = bracketValue1 + bracketValue2;
            } else if (bracketOp.equals("-")) {
                value = bracketValue1 - bracketValue2;
            } else if (bracketOp.equals("*")) {
                value = bracketValue1 * bracketValue2;
            } else if (bracketOp.equals("/") && bracketValue2 != 0) {
                value = bracketValue1 / bracketValue2;
            } else if (bracketValue2 == 0) {
                expressionCheck3 = false;
                value = Float.MIN_VALUE;
                return value;
            }
            expressionCheck3 = false;
            addToHistory(value);
            return value;
        }

        else{
            // System.out.println("Invalid input");
            value = Float.MIN_VALUE;
            return value;
        }
    }

    public float getCurrentValue(){
        if (value == Float.MIN_VALUE){
            return 0;
        }
        else {
            return value;
        }
    }

    // answer
    public static float value;

    // expression format check
    private static boolean expressionCheck1;
    private static boolean expressionCheck2;
    private static boolean expressionCheck3;
    String basicRegex = "(\\-?)((\\d*[.])?\\d+)(\\s)([+-/*])(\\s)(\\-?)((\\d*[.])?\\d+)((\\s)([+-/*])(\\s)(\\-?)((\\d*[.])?\\d+))*";
    String memoryRegex = "([+-/*])(\\s)(\\-?)((\\d*[.])?\\d+)";
    String bracketRegex = "([(])((\\-?)((\\d*[.])?\\d+)(\\s)([+-/*])(\\s)(\\-?)((\\d*[.])?\\d+))([)])" +
                          "(\\s)([+-/*])(\\s)" +
                          "([(])((\\-?)((\\d*[.])?\\d+)(\\s)([+-/*])(\\s)(\\-?)((\\d*[.])?\\d+))([)])";
    public static void expressionChecker1( String theRegex, String theExpression){
        // regex input to pattern object which compiles it
        Pattern pat = Pattern.compile(theRegex);

        // searches String for matches with regex
        Matcher mat = pat.matcher(theExpression);


        if (mat.matches()){
            expressionCheck1 = true;
        }
        else {
            expressionCheck1 = false;
        }
    }
    public static void expressionChecker2( String theRegex, String theExpression){
        // regex input to pattern object which compiles it
        Pattern pat = Pattern.compile(theRegex);

        // searches String for matches with regex
        Matcher mat = pat.matcher(theExpression);

        if (mat.matches()){
            expressionCheck2 = true;
        }
        else {
            expressionCheck2 = false;
        }
    }
    public static void expressionChecker3( String theRegex, String theExpression){
        // regex input to pattern object which compiles it
        Pattern pat = Pattern.compile(theRegex);

        // searches String for matches with regex
        Matcher mat = pat.matcher(theExpression);

        if (mat.matches()){
            expressionCheck3 = true;
            bracket1 = mat.group(2);
            bracketOp = mat.group(14);
            bracket2 = mat.group(17);
        }
        else {
            expressionCheck3 = false;
        }

    }

    // memory function
    public static float memoryValue = 0;
    public void setMemoryValue(float memval) {
        this.memoryValue = memval;
    }
    public float getMemoryValue() {
        return memoryValue;
    }
    public void clearMemory() {
        this.memoryValue = 0;
    }

    // history function
    public static ArrayList<String> history = new ArrayList<String>();
    public float getHistoryValue(int index) {
        String historyStr = history.get(index);
        float historyFloat = Float.parseFloat(historyStr);
        return historyFloat;
    }
    public void displayHistory(){
        System.out.println();
        for (int i = 0; i < history.size(); ++i) {
            System.out.print(history.get(i) + " ");
        }
        System.out.println();
    }
    public static void addToHistory(Float calcValue) {
        String strValue = String.valueOf(calcValue);
        history.add(strValue);
    }


    // brackets
    private static String bracket1;
    private static String bracketOp;
    private static String bracket2;

    // arbitrary
    public static float arithmetic(String operand1, String operand2, String operator) {
        float opNum1 = Float.parseFloat(operand1);
        float opNum2 = Float.parseFloat(operand2);
        float result;

        if (operator.equals("+")) {
            result = opNum1 + opNum2;
        } else if (operator.equals("-")) {
            result = opNum1 - opNum2;
        } else if (operator.equals("*")) {
            result = opNum1 * opNum2;
        } else if (operator.equals("/") && opNum2 != 0) {
            result = opNum1 / opNum2;
        } else if (opNum2 == 0) {
            result = Float.MIN_VALUE;
            return result;
        }
        else{
            result = Float.MIN_VALUE;
            return result;
        }
        return result;
    }
    public static boolean isOperator(String element){
        if (element.equals("+")){
            return true;
        }
        else
        if (element.equals("-")){
            return true;
        }
        else
        if (element.equals("*")){
            return true;
        }
        else
        if (element.equals("/")){
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean higherPrecedence(String stackTop, String operator) {
        if (stackTop.equals("+") && (operator.equals("+") || operator.equals("-"))) {
            return true;
        }
        else
        if (stackTop.equals("-") && (operator.equals("+") || operator.equals("-"))) {
            return true;
        }
        else
        if (stackTop.equals("*") && (operator.equals("+") || operator.equals("-"))) {
            return true;
        }
        else
        if (stackTop.equals("*") && (operator.equals("*") || operator.equals("/"))) {
            return true;
        }
        else
        if (stackTop.equals("/") && (operator.equals("+") || operator.equals("-"))) {
            return true;
        }
        else
        if (stackTop.equals("/") && (operator.equals("*") || operator.equals("/"))) {
            return true;
        }

        else{
            return false;
        }

    }




}
