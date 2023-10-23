package com.example.superjjj;

import java.util.Stack;

public class BasicLogical {

    public double calculate(String expression) {
        try {
            expression = expression.replaceAll("\\s+", "");
            Stack<Double> operandStack = new Stack<>();
            Stack<Character> operatorStack = new Stack<>();
            int index = 0;

            while (index < expression.length()) {
                char token = expression.charAt(index);
                if (Character.isDigit(token) || token == '-' && (index == 0 || expression.charAt(index - 1) == '(')) {
                    int startIdx = index;
                    while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.' || (expression.charAt(index) == '-' && index == startIdx))) {
                        index++;
                    }
                    operandStack.push(Double.parseDouble(expression.substring(startIdx, index)));
                } else if (token == '(') {
                    operatorStack.push(token);
                    index++;
                } else if (token == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        applyOperation(operandStack, operatorStack);
                    }
                    operatorStack.pop();
                    index++;
                } else if (token == '+' || token == '-' || token == '×' || token == '÷') {
                    while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(token)) {
                        applyOperation(operandStack, operatorStack);
                    }
                    operatorStack.push(token);
                    index++;
                } else {
                    throw new IllegalArgumentException("Invalid character in expression: " + token);
                }
            }

            while (!operatorStack.isEmpty()) {
                applyOperation(operandStack, operatorStack);
            }

            if (operandStack.size() != 1) {
                return Double.MAX_VALUE;
            }

            return operandStack.pop();
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }

    private int getPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '×' || operator == '÷') {
            return 2;
        }
        return 0;
    }

    private void applyOperation(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char operator = operatorStack.pop();
        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();
        switch (operator) {
            case '+':
                operandStack.push(operand1 + operand2);
                break;
            case '-':
                operandStack.push(operand1 - operand2);
                break;
            case '×':
                operandStack.push(operand1 * operand2);
                break;
            case '÷':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                operandStack.push(operand1 / operand2);
                break;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
