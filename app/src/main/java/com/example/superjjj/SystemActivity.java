package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superjjj.util.ToastUtil;

import java.math.BigInteger;

public class SystemActivity extends AppCompatActivity {
    private View header;
    private View converButtons;
    private View back;
    private TextView text;
    private View BIN;
    private View OCT;
    private View DEC;
    private View HEX;

    private TextView bin;
    private TextView oct;
    private TextView dec;
    private TextView hex;

    private TextView currentSelectedTextView = null;
    private View currentSelectedLayout = null;

    private String expression;


    private Button[] function = new Button[3]; // 清0，回退，等号
    private Button[] binary = new Button[3]; // 1，0，00 二进制
    private Button[] octonary = new Button[6]; // 八进制 234567
    private Button[] decimal = new Button[2]; // 十进制 89
    private Button[] hexadecimal = new Button[6]; // 16进制ABCDEF


    Button button0, button00, button1;
    Button button2, button3, button4, button5, button6, button7;
    Button button8, button9;
    Button buttonA, buttonB, buttonC, buttonD, buttonE, buttonF;
    Button clearAll, clear, equal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_system);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton2);
        BIN = findViewById(R.id.BINView);
        OCT = findViewById(R.id.OCTView);
        DEC = findViewById(R.id.DECView);
        HEX = findViewById(R.id.HEXView);

        bin = findViewById(R.id.BINText);
        oct = findViewById(R.id.OCTText);
        dec = findViewById(R.id.DECText);
        hex = findViewById(R.id.HEXText);

        text = findViewById(R.id.textView);
        text.setText("System Scale");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        button0 = findViewById(R.id.button0s);
        button1 = findViewById(R.id.button1s);
        button2 = findViewById(R.id.button2s);
        button3 = findViewById(R.id.button3s);
        button4 = findViewById(R.id.button4s);
        button5 = findViewById(R.id.button5s);
        button6 = findViewById(R.id.button6s);
        button7 = findViewById(R.id.button7s);
        button8 = findViewById(R.id.button8s);
        button9 = findViewById(R.id.button9s);
        button00 = findViewById(R.id.button00s);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);
        buttonE = findViewById(R.id.buttonE);
        buttonF = findViewById(R.id.buttonF);
        clearAll = findViewById(R.id.buttonClearAlls);
        clear = findViewById(R.id.buttonClears);
        equal = findViewById(R.id.buttonEquals);

        function[0] = clearAll;
        function[1] = clear;
        function[2] = equal;

        binary[0] = button1;
        binary[1] = button0;
        binary[2] = button00;

        octonary[0] = button2;
        octonary[1] = button3;
        octonary[2] = button4;
        octonary[3] = button5;
        octonary[4] = button6;
        octonary[5] = button7;

        decimal[0] = button8;
        decimal[1] = button9;

        hexadecimal[0] = buttonA;
        hexadecimal[1] = buttonB;
        hexadecimal[2] = buttonC;
        hexadecimal[3] = buttonD;
        hexadecimal[4] = buttonE;
        hexadecimal[5] = buttonF;

        initClickListeners(BIN, bin);
        initClickListeners(OCT, oct);
        initClickListeners(DEC, dec);
        initClickListeners(HEX, hex);

        changeTextSelected(bin);

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

        if (textView == bin) {
            // BIN 被选中
            /*
            允许functions和0,1,00
             */
            setButtonsEnabled(binary, true);
            setButtonsEnabled(octonary, false);
            setButtonsEnabled(decimal, false);
            setButtonsEnabled(hexadecimal, false);
        } else if (textView == oct) {
            // OCT 被选中
            /*
            允许functions和0,1,00,234567
             */
            setButtonsEnabled(binary, true);
            setButtonsEnabled(octonary, true);
            setButtonsEnabled(decimal, false);
            setButtonsEnabled(hexadecimal, false);
        } else if (textView == dec) {
            // DEC 被选中
            /*
            允许functions和0,1,00,234567,89
             */
            setButtonsEnabled(binary, true);
            setButtonsEnabled(octonary, true);
            setButtonsEnabled(decimal, true);
            setButtonsEnabled(hexadecimal, false);
        } else if (textView == hex) {
            // HEX 被选中
            /*
            允许所有
             */
            setButtonsEnabled(binary, true);
            setButtonsEnabled(octonary, true);
            setButtonsEnabled(decimal, true);
            setButtonsEnabled(hexadecimal, true);
        }

        setButtonsEnabled(function, true);
    }

    private void setButtonsEnabled(Button[] buttons, boolean enabled) {
        for (Button button : buttons) {
            if (button != null) {
                button.setEnabled(enabled);
            }
        }
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
        for (Button button : binary) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
        for (Button button : octonary) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
        for (Button button : decimal) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
        for (Button button : hexadecimal) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
        for (Button button : function) {
            if (button != null) button.setOnClickListener(buttonClickListener);
        }
    }

    private void appendTextToCurrentSelectedTextView(String text) {
        String currentText = currentSelectedTextView.getText().toString();
        // 检查当前文本是否为"0"
        if (currentText.equals("0")) {
            expression = text; // 如果是"0"，则用新输入的文本替换
        } else {
            expression = currentText + text; // 否则，追加新输入的文本
        }
        currentSelectedTextView.setText(expression);
    }


    private void handleButtonClick(Button button) {
        String buttonText = button.getText().toString();
        switch (button.getId()) {
            case R.id.buttonClearAlls:
                clearAllText();
                getResult();
                break;
            case R.id.buttonClears:
                clearLast();
                getResult();
                break;
            case R.id.buttonEquals:
                getResult();
                break;
            default:
                appendTextToCurrentSelectedTextView(buttonText);
                getResult();
                break;
        }
    }

    private void getResult() {
        if (expression.isEmpty()){
            ToastUtil.showShort(this, "please input data");
            return;
        }
        if (currentSelectedTextView != null && !expression.isEmpty()) {
            try {
                long decimalValue;
                if (currentSelectedTextView == bin) {
                    decimalValue = parseLongWithOverflowCheck(expression, 2);
                    oct.setText(Long.toOctalString(decimalValue));
                    dec.setText(Long.toString(decimalValue));
                    hex.setText(Long.toHexString(decimalValue).toUpperCase());
                } else if (currentSelectedTextView == oct) {
                    decimalValue = parseLongWithOverflowCheck(expression, 8);
                    bin.setText(Long.toBinaryString(decimalValue));
                    dec.setText(Long.toString(decimalValue));
                    hex.setText(Long.toHexString(decimalValue).toUpperCase());
                } else if (currentSelectedTextView == dec) {
                    decimalValue = parseLongWithOverflowCheck(expression, 10);
                    bin.setText(Long.toBinaryString(decimalValue));
                    oct.setText(Long.toOctalString(decimalValue));
                    hex.setText(Long.toHexString(decimalValue).toUpperCase());
                } else if (currentSelectedTextView == hex) {
                    decimalValue = parseLongWithOverflowCheck(expression, 16);
                    bin.setText(Long.toBinaryString(decimalValue));
                    oct.setText(Long.toOctalString(decimalValue));
                    dec.setText(Long.toString(decimalValue).toUpperCase());
                }
            } catch (NumberFormatException e) {
                ToastUtil.showShort(this, "Number too large or invalid format");
            }
        }
    }

    /**
     * the value might be too large, when i calculate the lager number the app might be crashed, so i
     * use this to switch the expression
     *
     * @param value
     * @param radix
     * @return
     * @throws NumberFormatException
     */
    private long parseLongWithOverflowCheck(String value, int radix) throws NumberFormatException {
        BigInteger bigInt = new BigInteger(value, radix);
        if (bigInt.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 || bigInt.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
            throw new NumberFormatException("Number too large");
        }
        return bigInt.longValue();
    }

    private void clearAllText() {
        if (currentSelectedTextView != null) {
            expression = "0";
            currentSelectedTextView.setText("0");
        }
    }

    private void clearLast() {
        if (currentSelectedTextView != null && currentSelectedTextView.length() > 0) {
            String currentText = currentSelectedTextView.getText().toString();
            if (currentText.length() == 1) {
                expression = "0";
            } else {
                expression = currentText.substring(0, currentText.length() - 1);
            }
            currentSelectedTextView.setText(expression);
        }
    }
}
