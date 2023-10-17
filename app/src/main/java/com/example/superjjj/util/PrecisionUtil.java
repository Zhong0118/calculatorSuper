package com.example.superjjj.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class PrecisionUtil{
    public static void initPrecisionSpinner(Context context, Spinner spinner) {
        List<String> precisionValues = new ArrayList<>();
        precisionValues.add("1");
        precisionValues.add("2");
        precisionValues.add("3");
        precisionValues.add("4");
        precisionValues.add("5");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, precisionValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public static int getPrecision(Spinner spinner) {
        String selectedValue = (String) spinner.getSelectedItem();
        return Integer.parseInt(selectedValue);
    }
}
