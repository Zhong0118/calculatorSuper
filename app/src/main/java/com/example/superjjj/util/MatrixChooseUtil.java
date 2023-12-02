package com.example.superjjj.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixChooseUtil {

    public static final Map<String, Integer> operationMap = new HashMap<>();

    static {
        operationMap.put("plus - 矩阵相加", 0);
        operationMap.put("minus - 矩阵相减", 1);
        operationMap.put("multiply - 矩阵相乘", 2);
        operationMap.put("transpose - 矩阵转置", 3);
        operationMap.put("inverse - 矩阵的逆", 4);
        operationMap.put("rank - 矩阵的秩", 5);
        operationMap.put("determinant - 行列式", 6);
        operationMap.put("adjoint - 伴随矩阵", 7);
        operationMap.put("dual - 对偶矩阵", 8);
    }

    public static void initSpinner(Context context, Spinner spinner) {
        List<String> operationValues = new ArrayList<>();
        operationValues.add("plus - 矩阵相加");
        operationValues.add("minus - 矩阵相减");
        operationValues.add("multiply - 矩阵相乘");
        operationValues.add("transpose - 矩阵转置");
        operationValues.add("inverse - 矩阵的逆");
        operationValues.add("rank - 矩阵的秩");
        operationValues.add("determinant - 行列式");
        operationValues.add("adjoint - 伴随矩阵");
        operationValues.add("dual - 对偶矩阵");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, operationValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public static int getOperationIndex(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }
}
