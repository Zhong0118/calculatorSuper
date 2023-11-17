package com.example.superjjj.util;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.superjjj.R;

public class ConverUtil {

    private TextView currentSelectedTextView;
    private View currentSelectedLayout;
    private String expression;
    private int selectedColor;
    private int defaultColor;
    private int selectedBackground;
    private int defaultBackground;

    public ConverUtil(int selectedColor, int defaultColor, int selectedBackground, int defaultBackground) {
        this.selectedColor = selectedColor;
        this.defaultColor = defaultColor;
        this.selectedBackground = selectedBackground;
        this.defaultBackground = defaultBackground;
    }

    public void initClickListeners(View layout, final TextView textView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextSelected(textView);
            }
        };

        layout.setOnClickListener(listener);
        textView.setOnClickListener(listener);
    }
    public void changeTextSelected(TextView textView) {
        // 如果有先前选中的 TextView，将其颜色改回黑
        if (currentSelectedTextView != null && currentSelectedTextView != textView) {
            currentSelectedTextView.setTextColor(Color.BLACK);
        }

        // 设置新 TextView 的颜色并更新当前选中的 TextView
        textView.setTextColor(Color.parseColor("#3B67E8"));
        currentSelectedTextView = textView;

        expression = currentSelectedTextView.getText().toString();

        if (currentSelectedLayout != null) {
            currentSelectedLayout.setBackgroundResource(0); // 移除边框
        }

        currentSelectedLayout = (View) textView.getParent();
        currentSelectedLayout.setBackgroundResource(R.drawable.border);

    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public TextView getCurrentSelectedTextView() {
        return currentSelectedTextView;
    }
    public View getCurrentSelectedLayout() {
        return currentSelectedLayout;
    }
}
