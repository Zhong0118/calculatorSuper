package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.superjjj.util.ToastUtil;

import java.text.DecimalFormat;

public class AreaActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;


    private View M2;
    private View HA;
    private View A;
    private View AC;
    private View RD2;
    private View QING;
    private View MU;

    private TextView m2;
    private TextView ha;
    private TextView a;
    private TextView ac;
    private TextView rd2;
    private TextView qing;
    private TextView mu;


    private TextView currentSelectedTextView = null;
    private View currentSelectedLayout = null;

    private String expression;
    private boolean shouldResetInput = false;



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

        setContentView(R.layout.activity_area);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("AREA");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        M2 = findViewById(R.id.m2View);
        HA = findViewById(R.id.haView);
        A = findViewById(R.id.aView);
        AC = findViewById(R.id.acView);
        RD2 = findViewById(R.id.rdView);
        QING = findViewById(R.id.qingView);
        MU = findViewById(R.id.muView);

        m2 = findViewById(R.id.m2Text);
        ha = findViewById(R.id.haText);
        a = findViewById(R.id.aText);
        ac = findViewById(R.id.acText);
        rd2 = findViewById(R.id.rdText);
        qing = findViewById(R.id.qingText);
        mu = findViewById(R.id.muText);



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

        initClickListeners(M2, m2);
        initClickListeners(HA, ha);
        initClickListeners(A, a);
        initClickListeners(AC, ac);
        initClickListeners(RD2, rd2);
        initClickListeners(QING, qing);
        initClickListeners(MU, mu);

        changeTextSelected(m2);

        setupButtonListeners();
    }



    private void initClickListeners(View layout, final TextView textView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextSelected(textView);
            }
        };

        layout.setOnClickListener(listener);
        textView.setOnClickListener(listener);
    }

    /**
     * 不断更换选中的textView
     *
     * @param textView
     */
    private void changeTextSelected(TextView textView) {
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

    private void setupButtonListeners() {
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectedTextView != null) {
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
        String currentText = currentSelectedTextView.getText().toString();

        if (shouldResetInput || currentText.equals("0")) {
            expression = text; // 重置文本
            shouldResetInput = false;
        } else if (text.equals(".") && expression.contains(".")){
            ToastUtil.showShort(this, "can't input dot again");
            return;
        } else {
            expression = currentText + text; // 否则，追加新输入的文本
        }

        currentSelectedTextView.setText(expression);
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
        if (expression.isEmpty()){
            ToastUtil.showShort(this, "please input data");
            return;
        }
        if (currentSelectedTextView != null && !expression.isEmpty()) {
            double inputTemp = Double.parseDouble(expression);
            double length;
            if (currentSelectedTextView == m2){
                length = inputTemp;
            } else if (currentSelectedTextView == ha){
                length = inputTemp * 10000;
            } else if (currentSelectedTextView == a){
                length = inputTemp * 100;
            } else if (currentSelectedTextView == ac){
                length = inputTemp * 4046.8564224;
            }else if (currentSelectedTextView == rd2){
                length = inputTemp * 25.29285264;
            }else if (currentSelectedTextView == qing){
                length = inputTemp * 6666.66666667;
            }else {
                length = inputTemp * 666.6666667;
            }

            setResult(length);
            shouldResetInput = true;

        }
    }
    private void setResult(double length) {
        DecimalFormat df = new DecimalFormat("#.####"); // 四位小数格式
        m2.setText(df.format(length));
        ha.setText(df.format(1e-4 * length));
        a.setText(df.format(0.01 * length));
        ac.setText(df.format(length / 4046.8564224));
        rd2.setText(df.format(length / 25.29285264));
        qing.setText(df.format(length / 6666.66666667));
        mu.setText(df.format(length / 666.666666667));
    }

    private void clearAllText() {
        if (currentSelectedTextView != null) {
            expression = "0";
            currentSelectedTextView.setText("0");
            shouldResetInput = false;
        }
    }

    private void clearLast() {
        if (currentSelectedTextView != null && currentSelectedTextView.length() > 0) {
            String currentText = currentSelectedTextView.getText().toString();
            if (currentText.length() == 1) {
                expression = "0";
                shouldResetInput = false;
            } else {
                expression = currentText.substring(0, currentText.length() - 1);
            }
            currentSelectedTextView.setText(expression);
        }
    }
}
