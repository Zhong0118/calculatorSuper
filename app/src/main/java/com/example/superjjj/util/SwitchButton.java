package com.example.superjjj.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.ValueAnimator;
import android.widget.CompoundButton;

import com.example.superjjj.R;

public class SwitchButton extends View {
    private Paint paint;
    private boolean isChecked = false;
    private CompoundButton.OnCheckedChangeListener mListener;

    private float switchWidth;
    private float switchHeight;
    private float circleDiameter;
    private float circleX;

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
        paint.setAntiAlias(true);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
            switchWidth = typedArray.getDimension(R.styleable.SwitchButton_switchWidth, dpToPx(70));
            switchHeight = typedArray.getDimension(R.styleable.SwitchButton_switchHeight, dpToPx(35));
            circleDiameter = switchHeight;
            typedArray.recycle();
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleX = isChecked ? getWidth() - circleDiameter : 0;
    }

    private void toggle() {
        isChecked = !isChecked;
        animateCircleX();
        notifyOnCheckedChanged();
    }

    private void animateCircleX() {
        float start = isChecked ? 0 : getWidth() - circleDiameter;
        float end = isChecked ? getWidth() - circleDiameter : 0;

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) switchWidth, (int) switchHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(isChecked ? Color.rgb(0, 128, 0) : Color.GRAY);
        canvas.drawRoundRect(new RectF(0, 0, switchWidth, switchHeight), switchHeight / 2, switchHeight / 2, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(circleX + circleDiameter / 2, switchHeight / 2, circleDiameter / 2 - 2, paint);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        animateCircleX();
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
