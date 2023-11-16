package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.superjjj.util.ToEnglish;
import com.example.superjjj.util.ToastUtil;

public class EnglishActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;

    private TextView num;
    private TextView english;
    private String expression;

    private Button[] buttons = new Button[12];
    private Button[] function = new Button[3];

    Button button0, button00, button1;
    Button button2, button3, button4, button5, button6, button7;
    Button button8, button9;
    Button buttonDot;
    Button clearAll, clear, equal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_english);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("ENGLISH");

        num = findViewById(R.id.NumText);
        english = findViewById(R.id.EngText);

        num.setMovementMethod(new ScrollingMovementMethod());
        english.setMovementMethod(new ScrollingMovementMethod());

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button00 = findViewById(R.id.button00);
        buttonDot = findViewById(R.id.buttonDot);
        clearAll = findViewById(R.id.buttonClearAll);
        clear = findViewById(R.id.buttonClear);
        equal = findViewById(R.id.buttonEqual);

        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;
        buttons[4] = button4;
        buttons[5] = button5;
        buttons[6] = button6;
        buttons[7] = button7;
        buttons[8] = button8;
        buttons[9] = button9;
        buttons[10] = button00;
        buttons[11] = buttonDot;

        function[0] = clearAll;
        function[1] = clear;
        function[2] = equal;

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num != null) {
                    Button clickedButton = (Button) v;
                    handleButtonClick(clickedButton);
                }
            }
        };

        // 遍历所有按钮数组并设置监听器
        for (Button button : buttons) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
        for (Button button : function) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
    }

    private void appendTextToCurrentSelectedTextView(String text) {
        String currentText = num.getText().toString();
        expression = currentText + text;
        num.setText(expression);
    }

    private void handleButtonClick(Button button) {
        String buttonText = button.getText().toString();
        switch (button.getId()) {
            case R.id.buttonClearAll:
                clearAllText();
                break;
            case R.id.buttonClear:
                clearLast();
                break;
            case R.id.buttonEqual:
                getResult();
                break;
            default:
                appendTextToCurrentSelectedTextView(buttonText);
                break;
        }
    }

    private void getResult() {
        // 分割整数和小数部分
        String[] parts = expression.split("\\.");
        String integerPart = parts.length > 0 ? parts[0] : "";

        // 检查整数部分的长度
        if (integerPart.length() >= 13){
            ToastUtil.showShort(this, "The result will break Trillion!!");
        } else {
            english.setText(new ToEnglish().numberToWords(expression));
        }
    }
    private void clearAllText() {
        if (num != null) {
            expression = "";
            num.setText("");
        }
    }

    private void clearLast() {
        if (num != null && num.length() > 0) {
            String currentText = num.getText().toString();
            if (currentText.length() == 1) {
                expression = "0";
            } else {
                expression = currentText.substring(0, currentText.length() - 1);
            }
            num.setText(expression);
        }
    }

}
