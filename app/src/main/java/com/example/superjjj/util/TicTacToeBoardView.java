package com.example.superjjj.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TicTacToeBoardView extends View {

    private Paint paint = new Paint();

    public Rect[] cells = new Rect[9];

    public char[] boardState = new char[9];

    private int currentPlayer;
    private boolean over;

    // 设置当前玩家
    public void setCurrentPlayer(int player) {
        this.currentPlayer = player;
    }
    public void setOver(boolean over){
        this.over = over;
    }

    public boolean[] linesToDraw = new boolean[8];



    private OnGameStateChangeListener gameStateChangeListener;

    public interface OnGameStateChangeListener {
        void onGameStateChange(char[] boardState);
    }

    public void setOnGameStateChangeListener(OnGameStateChangeListener listener) {
        this.gameStateChangeListener = listener;
    }



    public TicTacToeBoardView(Context context) {
        super(context);
        init();
    }

    public TicTacToeBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToeBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        // 初始化 boardState 数组
        for (int i = 0; i < boardState.length; i++) {
            boardState[i] = ' '; // 假设 ' ' 表示空格子
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float thirdWidth = getWidth() / 3f;
        float thirdHeight = getHeight() / 3f;

        // Draw vertical lines
        canvas.drawLine(thirdWidth, 0, thirdWidth, getHeight(), paint);
        canvas.drawLine(thirdWidth * 2, 0, thirdWidth * 2, getHeight(), paint);

        // Draw horizontal lines
        canvas.drawLine(0, thirdHeight, getWidth(), thirdHeight, paint);
        canvas.drawLine(0, thirdHeight * 2, getWidth(), thirdHeight * 2, paint);


        Paint oPaint = new Paint();
        oPaint.setColor(Color.parseColor("#FF04DF4F")); // Aqua color for 'O'
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(20f);
        oPaint.setAntiAlias(true);

        Paint xPaint = new Paint();
        xPaint.setColor(Color.parseColor("#FFA9FF29")); // Aqua color for 'X'
        xPaint.setStrokeWidth(20f);
        xPaint.setStrokeCap(Paint.Cap.ROUND);
        xPaint.setAntiAlias(true);

        for (int i = 0; i < 9; i++) {
            float cx = cells[i].centerX();
            float cy = cells[i].centerY();
            float cellHalf = thirdWidth / 2; // Assuming square cells

            if (boardState[i] == 'O') {
                canvas.drawCircle(cx, cy, cellHalf - xPaint.getStrokeWidth(), oPaint);
            } else if (boardState[i] == 'X') {
                float startX = cx - cellHalf + xPaint.getStrokeWidth();
                float startY = cy - cellHalf + xPaint.getStrokeWidth();
                float endX = cx + cellHalf - xPaint.getStrokeWidth();
                float endY = cy + cellHalf - xPaint.getStrokeWidth();

                canvas.drawLine(startX, startY, endX, endY, xPaint);
                canvas.drawLine(startX, endY, endX, startY, xPaint);
            }
        }

        if (over){

            Paint lpaint = new Paint();
            lpaint.setColor(Color.parseColor("#FF0000"));
            lpaint.setStrokeWidth(15f);
            lpaint.setStrokeCap(Paint.Cap.ROUND);
            lpaint.setAntiAlias(true);

            if (linesToDraw[0]){
                canvas.drawLine(0, thirdHeight/2f , getWidth() , thirdHeight/2f , lpaint);
            } else if (linesToDraw[1]){
                canvas.drawLine(0, thirdHeight/2f * 3, getWidth(), thirdHeight/2f * 3, lpaint);

            } else if (linesToDraw[2]){
                canvas.drawLine(0, thirdHeight/2f * 5 , getWidth(), thirdHeight/2f * 5 , lpaint);

            } else if (linesToDraw[3]){
                canvas.drawLine(thirdWidth/2f, 0, thirdWidth/2f, getWidth(), lpaint);

            } else if (linesToDraw[4]){
                canvas.drawLine(thirdWidth/2f * 3, 0, thirdWidth/2f * 3, getWidth() , lpaint);

            } else if (linesToDraw[5]){
                canvas.drawLine(thirdWidth/2f * 5, 0, thirdWidth/2f * 5 , getWidth(), lpaint);

            } else if (linesToDraw[6]){
                canvas.drawLine(0, 0, getWidth(), getHeight(), lpaint);

            } else if (linesToDraw[7]){
                canvas.drawLine(getWidth(), 0, 0, getHeight(), lpaint);

            }

            for (int i = 0; i < 8; i++){
                linesToDraw[i] = false;
            }
        }
    }


    /**
     * 初始化的时候自动填好的
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cellWidth = w / 3;
        int cellHeight = h / 3;

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
            cells[i] = new Rect(col * cellWidth, row * cellHeight, (col + 1) * cellWidth, (row + 1) * cellHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !over) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            for (int i = 0; i < 9; i++) {
                if (cells[i].contains(x, y) && boardState[i] == ' ') { // 确保格子是空的
                    boardState[i] = (currentPlayer == 0) ? 'O' : 'X'; // 根据当前玩家设置格子状态
                    gameStateChangeListener.onGameStateChange(boardState); // 通知游戏状态改变
                    invalidate(); // 重新绘制视图
                    break;
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

}

