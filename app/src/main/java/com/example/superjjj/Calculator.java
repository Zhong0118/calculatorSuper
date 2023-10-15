package com.example.superjjj;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    BasicLogical basicLogical;

    public double cal(String expression,int per) {
        expression = expression.replace(" ", "");                 //去掉expression中的所有空格
        expression = expression.replace("π", String.valueOf(Math.PI));          //替换π
        expression = expression.replace("e", String.valueOf(Math.exp(1)));      //替换自然指数e

        basicLogical = new BasicLogical();

        while (expression.contains("sin")
                || expression.contains("cos")
                || expression.contains("tan")
                || expression.contains("ln")
                || expression.contains("lg")
                || expression.contains("√")) {

            //1.获取()内运算式并计算出结果，此时假设()不再包含复杂的科学运算
            // 最右边的（
            int beginIndex = expression.lastIndexOf("(");
            // 对应的）
            int endIndex = getRightBracket(expression, beginIndex);
            // 括号内的表达式，包括括号
            String subMath = expression.substring(beginIndex + 1, endIndex);
            // 想得出括号内的结果
            double subResult = basicLogical.calculate(subMath);
            if (subResult == Double.MAX_VALUE) //每次计算要判断是否出现 expression error
                return Double.MAX_VALUE;

            //2.获取scienceOper字符串
            // 继续往左跑
            int i = beginIndex - 1;
            while (i >= 0 && !isOper(expression.charAt(i))) { //向左寻找
                i--;
            } // 此时的特殊运算符就是对应的高级运算符
            String scienceOper = expression.substring(i + 1, beginIndex);

            //3.匹配scienceOper进行科学运算，并替换相应部分
            String tempMath; // 临时的表达式
            double tempResult; // 临时结果

            switch (scienceOper) {
                case "sin":
                    tempMath = "sin(" + subMath + ")";
                    double degreesSin = Double.parseDouble(subMath); // 获取用户输入的角度值
                    double radiansSin = Math.toRadians(degreesSin); // 转换为弧度值
                    tempResult = Math.sin(radiansSin); // 计算sin值
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
                    break;
                case "cos":
                    tempMath = "cos(" + subMath + ")";
                    double degreesCos = Double.parseDouble(subMath); // 获取用户输入的角度值
                    double radiansCos = Math.toRadians(degreesCos); // 转换为弧度值
                    tempResult = Math.cos(radiansCos); // 计算cos值
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
                    break;
                case "tan":
                    tempMath = "tan(" + subMath + ")";
                    double degreesTan = Double.parseDouble(subMath); // 获取用户输入的角度值
                    double radiansTan = Math.toRadians(degreesTan); // 转换为弧度值
                    tempResult = Math.tan(radiansTan); // 计算tan值
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
                    break;

                case "ln":
                    tempMath = "ln(" + subMath + ")";
                    tempResult = Math.log(subResult);
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
                    break;
                case "lg":
                    tempMath = "lg(" + subMath + ")";
                    tempResult = Math.log10(subResult);
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
                    break;
                case "√":
                    tempMath = "√(" + subMath + ")";
                    tempResult = Math.sqrt(subResult);
                    expression = expression.replace(tempMath, String.valueOf(tempResult));
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
        int i;
        for (i = begin; i < math.length(); i++) {
            if (math.charAt(i) == ')')
                break;
        }
        return i;
    }

    private boolean isOper(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '(';
    }


}
