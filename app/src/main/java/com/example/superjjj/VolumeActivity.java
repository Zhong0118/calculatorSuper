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

public class VolumeActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;


    private View M3;
    private View HL;
    private View L;
    private View AF3;
    private View FT3;
    private View IN3;
    private View UKGAL;
    private View USGAL;
    private View UKOZ;
    private View USOZ;

    private TextView m3;
    private TextView hl;
    private TextView l;
    private TextView af3;
    private TextView ft3;
    private TextView in3;
    private TextView ukgal;
    private TextView usgal;
    private TextView ukoz;
    private TextView usoz;


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

        setContentView(R.layout.activity_volume);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("VOLUME");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        M3 = findViewById(R.id.m3View);
        m3 = findViewById(R.id.m3Text);
        HL = findViewById(R.id.hlView);
        hl = findViewById(R.id.hlText);
        L = findViewById(R.id.lView);
        l = findViewById(R.id.lText);
        AF3 = findViewById(R.id.af3View);
        af3 = findViewById(R.id.af3Text);
        FT3 = findViewById(R.id.ft3View);
        ft3 = findViewById(R.id.ft3Text);
        IN3 = findViewById(R.id.in3View);
        in3 = findViewById(R.id.in3Text);
        UKGAL = findViewById(R.id.ukgalView);
        ukgal = findViewById(R.id.ukgalText);
        USGAL = findViewById(R.id.usgalView);
        usgal = findViewById(R.id.usgalText);
        UKOZ = findViewById(R.id.ukozView);
        ukoz = findViewById(R.id.ukozText);
        USOZ = findViewById(R.id.usozView);
        usoz = findViewById(R.id.usozText);





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

        initClickListeners(M3, m3);
        initClickListeners(HL,hl);
        initClickListeners(L,l);
        initClickListeners(AF3,af3);
        initClickListeners(FT3,ft3);
        initClickListeners(IN3,in3);
        initClickListeners(UKGAL,ukgal);
        initClickListeners(USGAL,usgal);
        initClickListeners(UKOZ,ukoz);
        initClickListeners(USOZ,usoz);

        changeTextSelected(m3);

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
            if (currentSelectedTextView == m3){
                length = inputTemp;
            } else if (currentSelectedTextView == hl){
                length = inputTemp / 10;
            } else if (currentSelectedTextView == l){
                length = inputTemp / 1000;
            } else if (currentSelectedTextView == af3){
                length = inputTemp * 1233.48184;
            }else if (currentSelectedTextView == ft3){
                length = inputTemp / 35.31467;
            }else if (currentSelectedTextView == in3){
                length = inputTemp / 61023.74409;
            }else if (currentSelectedTextView == ukgal){
                length = inputTemp / 219.96916;
            }else if (currentSelectedTextView == usgal){
                length = inputTemp / 264.17205;
            }else if (currentSelectedTextView == ukoz){
                length = inputTemp / 35198.87364;
            }else{
                length = inputTemp / 33818.05884;
            }

            setResult(length);
            shouldResetInput = true;

        }
    }
    private void setResult(double length) {
        DecimalFormat df = new DecimalFormat("#.####"); // 四位小数格式
        m3.setText(df.format(length));
        hl.setText(df.format(length * 10));
        l.setText(df.format(length * 1000));
        af3.setText(df.format(length / 1233.48184));
        ft3.setText(df.format(length * 35.31467));
        in3.setText(df.format(length * 61023.74409));
        ukgal.setText(df.format(length * 219.96916));
        usgal.setText(df.format(length * 264.17205));
        ukoz.setText(df.format(length * 35198.87364));
        usoz.setText(df.format(length * 33818.05884));
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
