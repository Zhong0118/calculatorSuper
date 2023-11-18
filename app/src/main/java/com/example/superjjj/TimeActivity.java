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
import java.util.Date;
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
    private TextView weekT;
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
        weekT = findViewById(R.id.weekText);
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
        if (start.getText().equals("Select Start Time") || end.getText().equals("Select End Time")) {
            ToastUtil.showShort(this, "please choose time both");
            return;
        }
        long millisecondsDifference;
        // 计算两个时间点之间的毫秒差
        if (endTime.getTimeInMillis() > startTime.getTimeInMillis()) {
            millisecondsDifference = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        } else {
            millisecondsDifference = startTime.getTimeInMillis() - endTime.getTimeInMillis();
        }

        long totalMinutes = TimeUnit.MILLISECONDS.toMinutes(millisecondsDifference);
        minuteT.setText(totalMinutes + " minute" + (totalMinutes == 1 || totalMinutes == 0 ? "" : "s"));

        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        hourT.setText(hours + " hour" + (hours == 1 || hours == 0 ? "" : "s")
                + " " + minutes + " minute" + (minutes == 1 || minutes == 0 ? "" : "s"));

        int startYear = startTime.get(Calendar.YEAR);
        int startMonth = startTime.get(Calendar.MONTH) + 1;
        int startDay = startTime.get(Calendar.DAY_OF_MONTH);

        int endYear = endTime.get(Calendar.YEAR);
        int endMonth = endTime.get(Calendar.MONTH) + 1;
        int endDay = endTime.get(Calendar.DAY_OF_MONTH);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(startYear, startMonth - 1, startDay);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(endYear, endMonth - 1, endDay);
        Date date2 = calendar2.getTime();


        if (date1.after(date2)) {
            Date temp = date1;
            date1 = date2;
            date2 = temp;
        }
        // date1是较早的时间，date2是较晚的时间

        // 获取date1和date2的时间戳
        long timestamp1 = date1.getTime();  // 较短的天的时间戳
        long timestamp2 = date2.getTime();  // 较长的天的时间戳

        long differenceInMilliseconds = Math.abs(timestamp2 - timestamp1);

        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);

        calendar1.setTime(date1);
        calendar2.setTime(date2);

        int year1 = calendar1.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH) + 1;
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);

        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        dayT.setText(differenceInDays + " day" + (differenceInDays == 1 || differenceInDays == 0 ? "" : "s"));

        long weeks = differenceInDays / 7;
        long weekDays = differenceInDays % 7;

        weekT.setText(weeks + " week" + (weeks == 1 || weeks == 0 ? "" : "s")
                + " " + weekDays + " day" + (weekDays == 1 || weekDays == 0 ? "" : "s"));

        int yearGap = year2 - year1;

        if (month2 < month1 || (month2 == month1 && day2 < day1)) { // 表示不足一年的时候
            yearGap--;
        }

        // 计算两年之间有多少闰年
        int leapYears = 0;
        for (int year = year1; year < year2; year++) {
            if (leapYear(year)) {
                leapYears++;
            }
        }

        // 处理
        if (leapYear(year1) && (month1 > 2)) {
            leapYears--;
        }
        if (leapYear(year2) && (month2 > 2 || (month2 == 2 && day2 == 29))) {
            leapYears++;
        }
        long yearDayGap = differenceInDays - 365 * yearGap - leapYears;
        // 应该要减去leapYear，这样才能对应上，如果是加就越来越远了
        yearT.setText(yearGap + " year" + (yearGap == 1 || yearGap == 0 ? "" : "s")
                + " " + yearDayGap + " day" + (yearDayGap == 1 || yearDayGap == 0 ? "" : "s"));



    }

    private boolean leapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        return false;
    }



}
