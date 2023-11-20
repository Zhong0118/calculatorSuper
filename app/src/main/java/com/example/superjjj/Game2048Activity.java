package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.superjjj.util.GameView;
import com.example.superjjj.util.ToastUtil;

public class Game2048Activity extends AppCompatActivity {

    private View header;
    private View back;
    private TextView text;

    private TextView score;
    private TextView height_score;

    private Button restart;
    private Button undo;

    private GameView gridLayout;
    private final int GRID_SIZE = 4;
    private static final String PREFS_NAME = "Game2048Prefs";
    private static final String HIGH_SCORE = "HighScore";
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_game2048);

        header = findViewById(R.id.header);

        text = findViewById(R.id.textView);
        text.setText("2048");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        score = findViewById(R.id.score);
        height_score = findViewById(R.id.highest_score);
        restart = findViewById(R.id.restart);
        undo = findViewById(R.id.undo);

        gridLayout = findViewById(R.id.gameGrid);

        setupListeners();

        highScore = getHighScore();
        height_score.setText(String.valueOf(highScore));

    }

    private void setupListeners() {
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.start(); // 调用GameView中的startNewGame方法
                // 更新得分等UI逻辑
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.undo();
            }
        });
    }

    public void updateScore(int newScore) {
        score.setText(String.valueOf(newScore));
        if (highScore < newScore) {
            highScore = newScore;
            height_score.setText(String.valueOf(newScore));
            setHighScore(newScore); // 保存新的最高分
        }
    }


    private int getHighScore() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(HIGH_SCORE, 0); // 默认为0
    }

    private void setHighScore(int score) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(HIGH_SCORE, score);
        editor.apply();
    }

}
