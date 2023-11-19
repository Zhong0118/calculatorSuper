package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class Game2048Activity extends AppCompatActivity {

    private View header;
    private View back;
    private TextView text;

    private TextView score;
    private TextView height_score;

    private Button restart;
    private Button undo;

    private GridLayout gridLayout;
    private final int GRID_SIZE = 4;

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
        gridLayout.post(new Runnable() {
            @Override
            public void run() {
                createGrid(gridLayout.getWidth());
            }
        });

    }

    private void createGrid(int width) {

        // 格子间的margin
        int tileMargin = getResources().getDimensionPixelSize(R.dimen.tile_margin); // 需要在dimens.xml定义
        int paddingTotal = 2 * getResources().getDimensionPixelSize(R.dimen.padding); // 获取总padding（左右两侧）
        int tileSize = (width - paddingTotal - tileMargin *  3) / GRID_SIZE;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                TextView tile = new TextView(this);
                tile.setWidth(tileSize);
                tile.setHeight(tileSize);
                tile.setGravity(Gravity.CENTER);
                tile.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                tile.setBackground(ContextCompat.getDrawable(this, R.drawable.gamebtn));

                // 可以为每个tile设置标签或值
                // 00 01 02 03
                // 10 11 12 13
                // 20 21 22 23
                // 30 31 32 33
                tile.setTag(R.id.tag_row, row);
                tile.setTag(R.id.tag_col, col);

                // 添加到GridLayout
                gridLayout.addView(tile);

                // 设置边距
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) tile.getLayoutParams();
                int halfMargin = tileMargin / 2;
                params.setMargins(halfMargin, halfMargin, halfMargin, halfMargin );
                tile.setLayoutParams(params);
            }
        }
    }
}
