package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.superjjj.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeActivity extends AppCompatActivity {

    private View header;
    private View back;
    private TextView text;

    private Button start;
    private Button end;
    private Button calculate;

    private TextView dayT;
    private TextView monthT;
    private TextView yearT;
    private TextView hourT;
    private TextView minuteT;

    private String day;
    private String month;
    private String year;
    private String hour;
    private String minute;

    private Calendar startTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_time);

        header = findViewById(R.id.header);

        text = findViewById(R.id.textView);
        text.setText("Time Gap");

        start = findViewById(R.id.buttonStartDate);
        end = findViewById(R.id.buttonEndDate);
        calculate = findViewById(R.id.calculateTime);

        dayT = findViewById(R.id.dayText);
        monthT = findViewById(R.id.monthText);
        yearT = findViewById(R.id.yearText);
        hourT = findViewById(R.id.hourText);
        minuteT = findViewById(R.id.minuteText);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(true);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(false);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTimeGap();
            }
        });
    }



    private void showDateTimeDialog(final boolean isStart) {
        // 根据是否是开始时间还是结束时间来对应
        final Calendar calendar = isStart ? startTime : endTime;
        // 得到自定义的picker视图
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_date_time_picker, null);

        final DatePicker datePicker = view.findViewById(R.id.date_picker);
        final TimePicker timePicker = view.findViewById(R.id.time_picker);

        // 日期picker初始化对应的日子，并且根据api版本的不同需要设置不同的方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            datePicker.setCalendarViewShown(false);
            datePicker.setSpinnersShown(true);
        }
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));
        } else {
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
        // time为24小时制
        timePicker.setIs24HourView(true);

        // 为此时的dialog设置confirm和cancel
        new AlertDialog.Builder(this, R.style.CustomDatePickerDialog)
                .setView(view)
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // 获得对应的日期数据
                        calendar.set(Calendar.YEAR, datePicker.getYear());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        // 获得对应的时间数据
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                            calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        } else {
                            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        }
                        // 得到时间的基本样式，然后为按钮的文本设置
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        String formattedDateTime = format.format(calendar.getTime());
                        if (isStart) {
                            start.setText(formattedDateTime);
                        } else {
                            end.setText(formattedDateTime);
                        }
                    }
                })
                .setNegativeButton("cancel", null)
                .show();
    }



    @SuppressLint("SetTextI18n")
    private void calculateTimeGap() {

        if (start.getText().equals("Select Start Time") || end.getText().equals("Select End Time")){
            ToastUtil.showShort(this, "please choose time both");
            return;
        }

        // 计算年、月、日差异
        int yearsDifference = endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR);
        int monthsDifference = endTime.get(Calendar.MONTH) - startTime.get(Calendar.MONTH);
        int daysDifference = endTime.get(Calendar.DAY_OF_MONTH) - startTime.get(Calendar.DAY_OF_MONTH);

        // 如果结束月份小于开始月份，表示跨年了
        if (monthsDifference < 0) {
            yearsDifference--;
            monthsDifference += 12; // 跨年调整
        }

        // 如果结束日期小于开始日期，表示跨月了
        if (daysDifference < 0) {
            monthsDifference--;
            // 使用结束时间的上一个月最后一天，减去开始时间的日期，来得到跨月的日差异
            Calendar tempCalendar = (Calendar) endTime.clone();
            tempCalendar.add(Calendar.MONTH, -1);
            daysDifference += tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        // 如果月份差异小于0，表示跨年调整后仍然跨年了
        if (monthsDifference < 0) {
            yearsDifference--;
            monthsDifference += 12; // 再次跨年调整
        }

        // 更新 TextViews
        yearT.setText(yearsDifference + " year" + (yearsDifference == 1 ? "" : "s"));
        monthT.setText(monthsDifference + " month" + (monthsDifference == 1 ? "" : "s"));
        dayT.setText(daysDifference + " day" + (daysDifference == 1 ? "" : "s"));


    }
}
