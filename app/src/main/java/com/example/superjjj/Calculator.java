package com.example.superjjj;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    BasicLogical basicLogical;

    public double cal(String expression,int per) {
        // 右括号自动补全
        int leftNum = 0;
        int rightNum = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                leftNum++;
            } else if (expression.charAt(i) == ')') {
                rightNum++;
            }
        }

        int missingNum = leftNum - rightNum; // 缺失的 ) 数量
        StringBuilder stringBuilder = new StringBuilder(expression);
        while (missingNum > 0) {
            stringBuilder.append(')');
            missingNum--;
        }

        expression = stringBuilder.toString();

        expression = expression.replace(" ", "");

        basicLogical = new BasicLogical();

        while (expression.contains("sin")
                || expression.contains("cos")
                || expression.contains("tan")
                || expression.contains("ln")
                || expression.contains("lg")
                || expression.contains("√")) {

            //1.获取()内运算式并计算出结果，此时假设()不再包含复杂的科学运算
            // 最右边的（
            int lastLeftBr = expression.lastIndexOf("(");
            // 对应的）
            int lastRightBr = getRightBracket(expression, lastLeftBr);
            // 括号内的表达式，包括括号
            String subExpression = expression.substring(lastLeftBr + 1, lastRightBr);
            // 想得出括号内的结果
            double subResult = basicLogical.calculate(subExpression);
            if (subResult == Double.MAX_VALUE) //每次计算要判断是否出现 expression error
                return Double.MAX_VALUE;

            //2.获取someSuperOper字符串
            // 继续往左跑
            int i = lastLeftBr - 1;
            while (i >= 0 && !isOper(expression.charAt(i))) { //向左寻找
                i--;
            } // 此时的特殊运算符就是对应的高级运算符
            String someSuperOper = expression.substring(i + 1, lastLeftBr);



            //3.匹配someSuperOper进行科学运算，并替换相应部分
            String tempExpression; // 临时的表达式
            double tempResult; // 临时结果

            switch (someSuperOper) {
                case "sin":
                    tempExpression = "sin(" + subExpression + ")";
                    double degreesSin = Double.parseDouble(subExpression); // 获取用户输入的角度值
                    double radiansSin = Math.toRadians(degreesSin); // 转换为弧度值
                    tempResult = Math.sin(radiansSin); // 计算sin值
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;
                case "cos":
                    tempExpression = "cos(" + subExpression + ")";
                    double degreesCos = Double.parseDouble(subExpression); // 获取用户输入的角度值
                    double radiansCos = Math.toRadians(degreesCos); // 转换为弧度值
                    tempResult = Math.cos(radiansCos); // 计算cos值
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;
                case "tan":
                    tempExpression = "tan(" + subExpression + ")";
                    double degreesTan = Double.parseDouble(subExpression); // 获取用户输入的角度值
                    double radiansTan = Math.toRadians(degreesTan); // 转换为弧度值
                    tempResult = Math.tan(radiansTan); // 计算tan值
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;

                case "ln":
                    tempExpression = "ln(" + subExpression + ")";
                    tempResult = Math.log(subResult);
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;
                case "lg":
                    tempExpression = "lg(" + subExpression + ")";
                    tempResult = Math.log10(subResult);
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;
                case "√":
                    tempExpression = "√(" + subExpression + ")";
                    tempResult = Math.sqrt(subResult);
                    expression = expression.replace(tempExpression, String.valueOf(tempResult));
                    break;
                default:
                    break;
            }
        }

        while(expression.contains("!")){
            String reg = "([0-9]+)!";
            Pattern p=Pattern.compile(reg);
            Matcher m=p.matcher(expression);
            while(m.find()) {
                String str=m.group(0),str1=m.group(1);
                int a=Integer.parseInt(str1);
                double b=1;
                for(int i=1;i<=a;i++){
                    b=b*i;
                }
                expression = expression.replace(str, b + "");
            }
        }
        while(expression.contains("%")){
            String reg = "((\\d*\\.*\\d+)%)";
            Pattern p=Pattern.compile(reg);
            Matcher m = p.matcher(expression);
            while(m.find()){
                double a=0.01*Double.parseDouble(m.group(2));
                expression=expression.replace(m.group(1),a+"");
            }
        }
        while(expression.contains("^-1")){
            String reg = "((\\d*\\.*\\d+)\\^-1)";
            Pattern p=Pattern.compile(reg);
            Matcher m = p.matcher(expression);
            while(m.find()){
                double a=1/Double.parseDouble(m.group(2));
                expression=expression.replace(m.group(1),a+"");
            }
        }


        if (basicLogical.calculate(expression) == Double.MAX_VALUE)
            return Double.MAX_VALUE;
        else {
            BigDecimal b = new BigDecimal(basicLogical.calculate(expression));
            return b.setScale(per, BigDecimal.ROUND_HALF_UP).doubleValue(); //四舍五入保留相应位数小数
        }
    }

    private int getRightBracket(String math, int begin) {
        int count = 0;
        for (int i = begin; i < math.length(); i++) {
            char c = math.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        return -1; // 没有匹配的右括号，返回-1
    }


    private boolean isOper(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '(';
    }


}
