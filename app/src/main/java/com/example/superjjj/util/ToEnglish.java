package com.example.superjjj.util;

public class ToEnglish {
    private static final String[] oneToTwenty = {"", "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine ", "Ten ", "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen ", "Eighteen ", "Nineteen ", "Twenty "};
    private static final String[] tens = {"", "Ten ", "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "};
    private static final String[] units = {"Billion ", "Million ", "Thousand ", ""};
    private static final int[] mod = {1000000000, 1000000, 1000, 1}; // 确定数值在不同的数量级上的分割

    public String numberToWords(String numberStr) {
        String[] parts = numberStr.split("\\."); // 分割整数和小数部分
        String integerPart = parts.length > 0 ? parts[0] : "";
        String decimalPart = parts.length > 1 ? parts[1] : "";

        StringBuilder stringBuilder = new StringBuilder();

        // 处理整数部分
        if (!integerPart.isEmpty()) {
            int integerNumber = Integer.parseInt(integerPart);
            if (integerNumber == 0) stringBuilder.append("Zero");
            else {
                for (int i = 0; i < mod.length; i++) {
                    int segment = integerNumber / mod[i];
                    if (segment > 0) {
                        appendSegment(stringBuilder, segment);
                        stringBuilder.append(units[i]);
                    }
                    integerNumber %= mod[i];
                }
            }
        }

        // 处理小数部分
        if (!decimalPart.isEmpty()) {
            stringBuilder.append("point ");
            for (char digit : decimalPart.toCharArray()) {
                stringBuilder.append(oneToTwenty[Character.getNumericValue(digit)]).append(" ");
            }
        }

        return stringBuilder.toString().trim();
    }


    private void appendSegment(StringBuilder stringBuilder, int segment) {
        if (segment >= 100) {
            stringBuilder.append(oneToTwenty[segment / 100]).append("Hundred and ");
            segment %= 100;
        }
        if (segment > 20) {
            stringBuilder.append(tens[segment / 10]);
            segment %= 10;
        }
        if (segment > 0) {
            stringBuilder.append(oneToTwenty[segment]);
        }
    }
}
