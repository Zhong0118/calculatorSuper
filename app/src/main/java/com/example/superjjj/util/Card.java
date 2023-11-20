package com.example.superjjj.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.superjjj.R;

public class Card extends FrameLayout {

    private int num = 0;
    private TextView label;



    public Card(@NonNull Context context) {
        super(context);
        this.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gamebtn));

        label = new TextView(getContext());
        label.setTextSize(32);
        // 超级加粗
        label.setTypeface(null, Typeface.BOLD);
        label.getPaint().setFakeBoldText(true);

        label.setGravity(Gravity.CENTER);
        // 填充满父级容器
        LayoutParams layoutParams = new LayoutParams(-1,-1);
        addView(label,layoutParams);
        setNum(0);
    }

    public int getNum(){
        return num;
    }
    public void setNum(int num){
        this.num = num;
        if (num == 0){
            label.setText("");
        } else {
            label.setText(String.valueOf(num));
        }
        updateBackgroundColor();
    }

    private void updateBackgroundColor() {
        if (num == 0) {
            ((GradientDrawable) getBackground()).setColor(Color.parseColor("#cdc1b5")); // 假设这是您的默认背景颜色
            return;
        }
        int color = Color.parseColor(TileColor.getColorByNumber(num));
        // 只改颜色不改样式
        ((GradientDrawable) getBackground()).setColor(color);

        // Change text color based on the background for better visibility
        if (num > 512 && num <= 8192) {
            label.setTextSize(24);
            label.setTextColor(Color.parseColor("#f0f0f0")); // For darker backgrounds
        } else if (num < 100000 && num > 8192){
            label.setTextSize(20);
            label.setTextColor(Color.parseColor("#f0f0f0")); // For darker backgrounds
        } else if (num > 100000){
            label.setTextSize(16);
            label.setTextColor(Color.parseColor("#f0f0f0")); // For darker backgrounds
        } else {
            label.setTextColor(Color.parseColor("#5f5d5d"));
        }
    }
    public boolean equal(Card card){
        return this.getNum() == card.getNum();
    }
}
