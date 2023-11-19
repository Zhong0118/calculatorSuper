package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Game2048Activity extends AppCompatActivity {

    private View header;
    private View back;
    private TextView text;

    private TextView score;
    private TextView height_score;

    private Button restart;
    private Button undo;

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



    }
}
