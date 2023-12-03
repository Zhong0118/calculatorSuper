package com.example.superjjj.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.superjjj.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixChooseUtil {

    public static final Map<String, Integer> operationMap = new HashMap<>();

    static {
        operationMap.put("plus", 0);
        operationMap.put("minus", 1);
        operationMap.put("multiply", 2);
        operationMap.put("transpose", 3);
        operationMap.put("inverse", 4);
        operationMap.put("rank", 5);
        operationMap.put("determinant", 6);
        operationMap.put("adjoint", 7);
        operationMap.put("dual", 8);
    }

    public static void initSpinner(Context context, Spinner spinner) {
        List<String> operationValues = new ArrayList<>();
        operationValues.add("plus");
        operationValues.add("minus");
        operationValues.add("multiply");
        operationValues.add("transpose");
        operationValues.add("inverse");
        operationValues.add("rank");
        operationValues.add("determinant");
        operationValues.add("adjoint");
        operationValues.add("dual");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.matrixitem, operationValues);
        adapter.setDropDownViewResource(R.layout.matrixdrop);
        spinner.setAdapter(adapter);
    }

    public static int getOperationIndex(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }
}
