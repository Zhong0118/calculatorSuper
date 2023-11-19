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

public class WeightActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;


    private View KG;
    private View T;
    private View G;
    private View Q;
    private View LB;
    private View OZ;
    private View CT;
    private View ST;
    private View DAN;
    private View JIN;
    private View QIAN;
    private View LIANG;

    private TextView kg;
    private TextView t;
    private TextView g;
    private TextView q;
    private TextView lb;
    private TextView oz;
    private TextView ct;
    private TextView st;
    private TextView dan;
    private TextView jin;
    private TextView qian;
    private TextView liang;


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

        setContentView(R.layout.activity_weight);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("WEIGHT");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        KG = findViewById(R.id.kgView);
        T = findViewById(R.id.tView);
        G = findViewById(R.id.gView);
        Q = findViewById(R.id.qView);
        LB = findViewById(R.id.lbView);
        OZ = findViewById(R.id.ozView);
        CT = findViewById(R.id.ctView);
        ST = findViewById(R.id.stView);
        DAN = findViewById(R.id.danView);
        JIN = findViewById(R.id.jinView);
        QIAN = findViewById(R.id.qianView);
        LIANG = findViewById(R.id.liangView);

        kg = findViewById(R.id.kgText);
        t = findViewById(R.id.tText);
        g = findViewById(R.id.gText);
        q = findViewById(R.id.qText);
        lb = findViewById(R.id.lbText);
        oz = findViewById(R.id.ozText);
        ct = findViewById(R.id.ctText);
        st = findViewById(R.id.stText);
        dan = findViewById(R.id.danText);
        jin = findViewById(R.id.jinText);
        qian = findViewById(R.id.qianText);
        liang = findViewById(R.id.liangText);



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

        initClickListeners(KG, kg);
        initClickListeners(T, t);
        initClickListeners(G, g);
        initClickListeners(Q, q);
        initClickListeners(LB, lb);
        initClickListeners(OZ, oz);
        initClickListeners(CT, ct);
        initClickListeners(ST, st);
        initClickListeners(DAN, dan);
        initClickListeners(JIN, jin);
        initClickListeners(QIAN, qian);
        initClickListeners(LIANG, liang);

        changeTextSelected(kg);

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
            double weight;
            if (currentSelectedTextView == kg){
                weight = inputTemp;
            } else if (currentSelectedTextView == t){
                weight = inputTemp * 1000;
            } else if (currentSelectedTextView == g){
                weight = inputTemp / 1000;
            } else if (currentSelectedTextView == q){
                weight = inputTemp * 100;
            } else if (currentSelectedTextView == lb){
                weight = inputTemp / 2.20462;
            }else if (currentSelectedTextView == oz){
                weight = inputTemp / 35.27396;
            }else if (currentSelectedTextView == ct){
                weight = inputTemp / 5000;
            }else if (currentSelectedTextView == st){
                weight = inputTemp / 0.15747;
            }else if (currentSelectedTextView == dan){
                weight = inputTemp / 0.02;
            }else if (currentSelectedTextView == jin){
                weight = inputTemp / 2;
            }else if (currentSelectedTextView == qian){
                weight = inputTemp / 200;
            }else {
                weight = inputTemp / 20;
            }

            setResult(weight);
            shouldResetInput = true;

        }
    }
    private void setResult(double weight) {
        DecimalFormat df = new DecimalFormat("#.####"); // 四位小数格式
        kg.setText(df.format(weight));
        t.setText(df.format(0.001 * weight));
        g.setText(df.format(1000 * weight));
        q.setText(df.format(weight * 0.01));
        lb.setText(df.format(weight * 2.20462));
        oz.setText(df.format(weight * 35.27396));
        ct.setText(df.format(weight * 5000));
        st.setText(df.format(weight * 0.15747));
        dan.setText(df.format(weight * 0.02));
        jin.setText(df.format(weight * 2));
        qian.setText(df.format(weight * 200));
        liang.setText(df.format(weight * 20));
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
