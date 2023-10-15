package com.example.superjjj;
import java.util.Stack;
import java.util.Stack;

public class BasicLogical {
    public double calculate(String expression) {
        expression = expression.replaceAll("\\s+", ""); // 去掉空格
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        int index = 0;

        while (index < expression.length()) {
            char token = expression.charAt(index);
            if (Character.isDigit(token) || token == '-' && (index == 0 || expression.charAt(index - 1) == '(')) {
                StringBuilder num = new StringBuilder();
                while (index < expression.length() && (Character.isDigit(expression.charAt(index)) || expression.charAt(index) == '.' || (expression.charAt(index) == '-' && num.length() == 0))) {
                    num.append(expression.charAt(index));
                    index++;
                }
                operandStack.push(Double.parseDouble(num.toString()));
            } else if (token == '(') {
                operatorStack.push(token);
                index++;
            } else if (token == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    char operator = operatorStack.pop();
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    operandStack.push(applyOperator(operand1, operand2, operator));
                }
                operatorStack.pop(); // 弹出 '('
                index++;
            } else if (token == '+' || token == '-' || token == '×' || token == '÷') {
                while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek()) >= getPrecedence(token)) {
                    char operator = operatorStack.pop();
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    operandStack.push(applyOperator(operand1, operand2, operator));
                }
                operatorStack.push(token);
                index++;
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            char operator = operatorStack.pop();
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            operandStack.push(applyOperator(operand1, operand2, operator));
        }

        if (operandStack.size() != 1 || !operatorStack.isEmpty()) {
            return Double.MAX_VALUE;
        }

        return operandStack.pop();
    }

    private int getPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '×' || operator == '÷') {
            return 2;
        }
        return 0; // For '(' and ')'
    }

    private double applyOperator(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '×':
                return operand1 * operand2;
            case '÷':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
