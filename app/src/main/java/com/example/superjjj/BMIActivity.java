package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.superjjj.util.ToastUtil;

public class BMIActivity extends AppCompatActivity {
    private static final int MAX_BMI_SEEKBAR_VALUE = 1000;

    int thinProgress = (int) ((18.5f - 14) / (34 - 14) * MAX_BMI_SEEKBAR_VALUE);
    int normalProgress = (int) ((24f - 14) / (34 - 14) * MAX_BMI_SEEKBAR_VALUE);
    int overProgress = (int) ((28f - 14) / (34 - 14) * MAX_BMI_SEEKBAR_VALUE);

    private View header;
    private View converButtons;
    private View back;
    private TextView text;

    private EditText heightInput, weightInput;
    private TextView bmiResult, weightSuggestion;
    private Button calculateButton;
    private SeekBar bmiProgressBar, bmiProgressBarF;
    private TextView result, thin, normal, over, fat;

    private String height, weight, bmi;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_bmi);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton1);

        text = findViewById(R.id.textView);
        text.setText("BMI");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);

        bmiResult = findViewById(R.id.bmiResult);
        weightSuggestion = findViewById(R.id.weightSuggestion);

        thin = findViewById(R.id.Thin);
        normal = findViewById(R.id.Normal);
        over = findViewById(R.id.Over);
        fat = findViewById(R.id.Fat);

        calculateButton = findViewById(R.id.calculateButton);

        bmiProgressBar = findViewById(R.id.bmiProgressBar);
        bmiProgressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Ignore touch events
            }
        });

        bmiProgressBarF = findViewById(R.id.bmiProgressBarF);
        bmiProgressBarF.setProgress(bmiProgressBarF.getMax()); // Set progress to max
        bmiProgressBarF.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Ignore touch events
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = heightInput.getText().toString();
                weight = weightInput.getText().toString();

                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        if (height.isEmpty() || weight.isEmpty()){
            ToastUtil.showShort(this, "please input the value");
        }
        if (!height.isEmpty() && !weight.isEmpty()) {
            float heightValue = Float.parseFloat(height) / 100; // 身高转换成米
            float weightValue = Float.parseFloat(weight);

            if (heightValue < 0.01 || weightValue > 300 || heightValue > 400 || weightValue < 0.01){
                ToastUtil.showShort(this, "the value is unreasonable");
            } else {
                float bmiValue = weightValue / (heightValue * heightValue);
                float s1 = (heightValue * heightValue) * 18.5f;
                float s2 = (heightValue * heightValue) * 24.0f;
                bmiResult.setText(String.format("%.2f", bmiValue));
                weightSuggestion.setText(String.format("%.2f", s1) + " ~ "+ String.format("%.2f", s2) + " kg");
                updateTag(bmiValue);
            }

        }
    }

    private void updateTag(float bmiValue) {
        thin.setBackgroundResource(0);
        normal.setBackgroundResource(0);
        over.setBackgroundResource(0);
        fat.setBackgroundResource(0);
        thin.setTextColor(Color.BLACK);
        normal.setTextColor(Color.BLACK);
        fat.setTextColor(Color.BLACK);
        over.setTextColor(Color.BLACK);

        if (bmiValue < 18.5){
            thin.setBackgroundResource(R.drawable.thin);
            thin.setTextColor(Color.WHITE);
        } else if (18.5 <= bmiValue && bmiValue < 24){
            normal.setBackgroundResource(R.drawable.normal);
            normal.setTextColor(Color.WHITE);
        } else if (24 <= bmiValue && bmiValue < 28){
            over.setBackgroundResource(R.drawable.over);
            over.setTextColor(Color.WHITE);
        } else {
            fat.setBackgroundResource(R.drawable.fat);
            fat.setTextColor(Color.WHITE);
        }
        int progress = (int) ((bmiValue - 14) / (34 - 14) * MAX_BMI_SEEKBAR_VALUE);
        bmiProgressBar.setProgress(progress);
    }
}
