package com.example.superjjj.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class TicTacToeWinView extends View {
    private Paint paint;
    private boolean[] linesToDraw = new boolean[8];

    public TicTacToeWinView(Context context) {
        super(context);
        init();
    }

    public TicTacToeWinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TicTacToeWinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Use Color.RED for win lines
        paint.setStrokeWidth(10f); // Set the stroke width to 10f
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float thirdWidth = getWidth() / 6f;
        float thirdHeight = getHeight() / 6f;

        if (linesToDraw[0]){
            canvas.drawLine(0, thirdHeight , getWidth() , thirdHeight , paint);
        } else if (linesToDraw[1]){
            canvas.drawLine(0, thirdHeight * 3, getWidth(), thirdHeight * 3, paint);

        } else if (linesToDraw[2]){
            canvas.drawLine(0, thirdHeight * 5 , getWidth(), thirdHeight * 5 , paint);

        } else if (linesToDraw[3]){
            canvas.drawLine(thirdWidth, 0, thirdWidth, getWidth(), paint);

        } else if (linesToDraw[4]){
            canvas.drawLine(thirdWidth * 3, 0, thirdWidth * 3, getWidth() , paint);

        } else if (linesToDraw[5]){
            canvas.drawLine(thirdWidth * 5, 0, thirdWidth * 5 , getWidth(), paint);

        } else if (linesToDraw[6]){
            canvas.drawLine(0, 0, getWidth(), getHeight(), paint);

        } else if (linesToDraw[7]){
            canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);

        }

    }
    public void setLineToDraw(int index, boolean shouldDraw) {
        for (int i = 0; i < 9; i++){
            linesToDraw[i] = false;
        }
        if (index >= 0 && index < linesToDraw.length) {
            linesToDraw[index] = shouldDraw;
        }
        invalidate(); // 通知视图重绘
    }
}

