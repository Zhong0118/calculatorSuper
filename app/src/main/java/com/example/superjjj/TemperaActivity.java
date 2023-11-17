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

import java.math.BigInteger;

public class TemperaActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;

    private View Cen;
    private View Fah;
    private View Kel;
    private View Ran;
    private View Rea;

    private TextView cen;
    private TextView fah;
    private TextView kel;
    private TextView ran;
    private TextView rea;

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

        setContentView(R.layout.activity_tempera);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("TEMPERATURE");

        Cen = findViewById(R.id.CenView);
        Fah = findViewById(R.id.FahView);
        Kel = findViewById(R.id.KelView);
        Ran = findViewById(R.id.RanView);
        Rea = findViewById(R.id.ReaView);

        cen = findViewById(R.id.CenText);
        fah = findViewById(R.id.FahText);
        kel = findViewById(R.id.KelText);
        ran = findViewById(R.id.RanText);
        rea = findViewById(R.id.ReaText);


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

        button00.setText("-");

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
        initClickListeners(Cen, cen);
        initClickListeners(Fah, fah);
        initClickListeners(Kel, kel);
        initClickListeners(Ran, ran);
        initClickListeners(Rea, rea);

        changeTextSelected(cen);

        setupButtonListeners();
    }

    /**
     * 得到对应的选中的layout和textview
     *
     * @param layout
     * @param textView
     */

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
        if (expression.charAt(0) != '-' && expression.contains("-")){
            ToastUtil.showShort(this, "the negative symbol should be first");
            return;
        }
        if (currentSelectedTextView != null && !expression.isEmpty()) {
            double inputTemp = Double.parseDouble(expression);
            double degree;
            if (currentSelectedTextView == cen) {
                degree = inputTemp;
            } else if (currentSelectedTextView == fah) {
                degree = (inputTemp - 32) * 5 / 9;
            } else if (currentSelectedTextView == kel) {
                degree = inputTemp - 273.15;
            } else if (currentSelectedTextView == ran) {
                degree = (inputTemp - 491.67) * 5 / 9;
            } else {
                degree = inputTemp * 5 / 4;
            }

            setResult(degree);
            shouldResetInput = true;

        }
    }
    private void setResult(double degree) {
        cen.setText(String.format("%.2f", degree));
        fah.setText(String.format("%.2f", 1.8 * degree + 32));
        kel.setText(String.format("%.2f", degree + 273.15));
        ran.setText(String.format("%.2f", degree * 9 / 5 + 491.67));
        rea.setText(String.format("%.2f", degree * 4 / 5));
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
