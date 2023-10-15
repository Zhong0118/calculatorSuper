package com.example.superjjj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

public class SwitchButton extends View {

    private Paint paint;
    private boolean isChecked = false;
    private CompoundButton.OnCheckedChangeListener mListener;

    private float switchWidth;
    private float switchHeight;
    private float circleDiameter;

    public SwitchButton(Context context) {
        super(context);
        init(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); // 抗锯齿

        // 获取自定义属性
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
            switchWidth = typedArray.getDimension(R.styleable.SwitchButton_switchWidth, dpToPx(70)); // 默认宽度60dp
            switchHeight = typedArray.getDimension(R.styleable.SwitchButton_switchHeight, dpToPx(35)); // 默认高度40dp
            circleDiameter = switchHeight; // 小圆圈直径等于开关的高度
            typedArray.recycle();
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                invalidate();
                notifyOnCheckedChanged();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) switchWidth, (int) switchHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // 绘制底部灰色背景
        paint.setColor(Color.GRAY);
        canvas.drawRoundRect(new RectF(0, 0, switchWidth, switchHeight), switchHeight / 2, switchHeight / 2, paint);

        // 计算小圆圈的位置
        float circleX = isChecked ? width - circleDiameter : 0;
        // 绘制绿色背景
        if (isChecked) {
            paint.setColor(Color.rgb(0, 128, 0)); // 深绿色
            float greenRectWidth = circleX + circleDiameter / 2 + 20;
            canvas.drawRoundRect(new RectF(0, 0, greenRectWidth, switchHeight), switchHeight / 2, switchHeight / 2, paint);
        }

        // 绘制小圆圈
        paint.setColor(Color.WHITE);
        canvas.drawCircle(circleX + circleDiameter / 2, switchHeight / 2, circleDiameter / 2, paint);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        invalidate();
        notifyOnCheckedChanged();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        mListener = listener;
    }

    private void notifyOnCheckedChanged() {
        if (mListener != null) {
            mListener.onCheckedChanged(null, isChecked());
        }
    }

    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
