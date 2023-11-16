package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LengthActivity extends AppCompatActivity {

    private View header;
    private View converButtons;
    private View back;
    private TextView text;


    private View M;
    private View KM;
    private View DM;
    private View CM;
    private View MM;
    private View µM;
    private View NM;
    private View PM;
    private View NMI;
    private View MI;
    private View YD;
    private View FTM;
    private View FT;
    private View IN;
    private View LI;
    private View ZHANG;
    private View CHI;
    private View CUN;
    private View FEN;
    private View LII;
    private View HAO;

    private TextView m;
    private TextView km;
    private TextView dm;
    private TextView cm;
    private TextView mm;
    private TextView µm;
    private TextView nm;
    private TextView pm;
    private TextView nmi;
    private TextView mi;
    private TextView yd;
    private TextView ftm;
    private TextView ft;
    private TextView in;
    private TextView li;
    private TextView zhang;
    private TextView chi;
    private TextView cun;
    private TextView fen;
    private TextView lii;
    private TextView hao;




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

        setContentView(R.layout.activity_length);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);


        M = findViewById(R.id.mView);
        m = findViewById(R.id.mText);

        KM = findViewById(R.id.kmView);
        km = findViewById(R.id.kmText);

        DM = findViewById(R.id.dmView);
        dm = findViewById(R.id.dmText);

        CM = findViewById(R.id.cmView);
        cm = findViewById(R.id.cmText);

        MM = findViewById(R.id.mmView);
        mm = findViewById(R.id.mmText);

        µM = findViewById(R.id.µmView);
        µm = findViewById(R.id.µmText);

        NM = findViewById(R.id.nmView);
        nm = findViewById(R.id.nmText);

        PM = findViewById(R.id.pmView);
        pm = findViewById(R.id.pmText);

        NMI = findViewById(R.id.nmiView);
        nmi = findViewById(R.id.nmiText);

        MI = findViewById(R.id.miView);
        mi = findViewById(R.id.miText);

        YD = findViewById(R.id.ydView);
        yd = findViewById(R.id.ydText);

        FTM = findViewById(R.id.ftmView);
        ftm = findViewById(R.id.ftmText);

        FT = findViewById(R.id.ftView);
        ft = findViewById(R.id.ftText);

        IN = findViewById(R.id.inView);
        in = findViewById(R.id.inText);

        LI = findViewById(R.id.liView);
        li = findViewById(R.id.liText);

        ZHANG = findViewById(R.id.zhangView);
        zhang = findViewById(R.id.zhangText);

        CHI = findViewById(R.id.chiView);
        chi = findViewById(R.id.chiText);

        CUN = findViewById(R.id.cunView);
        cun = findViewById(R.id.cunText);

        FEN = findViewById(R.id.fenView);
        fen = findViewById(R.id.fenText);

        LII = findViewById(R.id.liiView);
        lii = findViewById(R.id.liiText);

        HAO = findViewById(R.id.haoView);
        hao = findViewById(R.id.haoText);



        text = findViewById(R.id.textView);
        text.setText("LENGTH");

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

        initClickListeners(M, m);
        initClickListeners(KM, km);
        initClickListeners(DM, dm);
        initClickListeners(CM,cm);
        initClickListeners(MM,mm);
        initClickListeners(µM, µm);
        initClickListeners(NM,nm);
        initClickListeners(PM,pm);
        initClickListeners(NMI,nmi);
        initClickListeners(MI,mi);
        initClickListeners(YD,yd);
        initClickListeners(FTM,ftm);
        initClickListeners(FT,ft);
        initClickListeners(IN,in);
        initClickListeners(LI,li);
        initClickListeners(ZHANG,zhang);
        initClickListeners(CHI,chi);
        initClickListeners(CUN,cun);
        initClickListeners(FEN,fen);
        initClickListeners(LII,lii);
        initClickListeners(HAO,hao);

        changeTextSelected(m);

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
        } else {
            expression = currentText + text; // 追加文本
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
        if (currentSelectedTextView != null && !expression.isEmpty()) {
            double inputTemp = Double.parseDouble(expression);
            double mi;

            shouldResetInput = true;

        }
    }
    private void setResult(double degree) {

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
