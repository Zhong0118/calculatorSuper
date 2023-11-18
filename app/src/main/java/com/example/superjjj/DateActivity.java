package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.superjjj.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateActivity extends AppCompatActivity {

    private View header;
    private View back;
    private TextView text;

    private Button buttonDate;
    private Button calculate;
    private EditText day;
    private EditText hour;
    private EditText minute;
    private TextView date;

    private Calendar start = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_date);

        header = findViewById(R.id.header);

        text = findViewById(R.id.textView);
        text.setText("Date estimation");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        buttonDate = findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog();
            }
        });
        calculate = findViewById(R.id.calculateDate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDate();
            }
        });

        day = findViewById(R.id.day);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        date = findViewById(R.id.date);
    }

    private void calculateDate() {
        if (buttonDate.getText().toString().equals("Choose Time")){
            ToastUtil.showShort(this, "please choose time");
        }

        try {
            // 获取DatePicker和TimePicker中的日期和时间
            int year = start.get(Calendar.YEAR);
            int month = start.get(Calendar.MONTH);
            int dayOfMonth = start.get(Calendar.DAY_OF_MONTH);
            int hourOfDay = start.get(Calendar.HOUR_OF_DAY);
            int minuteOfHour = start.get(Calendar.MINUTE);

            int addedDays = 0;
            int addedHours = 0;
            int addedMinutes = 0;

            // 从EditText获取输入的天数、小时和分钟
            if (!day.getText().toString().isEmpty()){
                addedDays = Integer.parseInt(day.getText().toString());
            }
            if (!hour.getText().toString().isEmpty()){
                addedHours = Integer.parseInt(hour.getText().toString());
            }
            if (!minute.getText().toString().isEmpty()){
                addedMinutes = Integer.parseInt(minute.getText().toString());
            }


            // 使用Calendar计算新的日期和时间
            Calendar newDateTime = Calendar.getInstance();
            newDateTime.set(year, month, dayOfMonth, hourOfDay, minuteOfHour);
            // 加日期，需要在月的某一天的基础加
            newDateTime.add(Calendar.DAY_OF_MONTH, addedDays);
            // 需要在日期的时间基础加小时
            newDateTime.add(Calendar.HOUR_OF_DAY, addedHours);
            newDateTime.add(Calendar.MINUTE, addedMinutes);

            // 格式化新的日期时间并设置到TextView
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String formattedNewDateTime = format.format(newDateTime.getTime());
            date.setText(formattedNewDateTime);
        } catch (NumberFormatException e) {
            ToastUtil.showShort(this, "you input the num too large!!");
        }

    }



    private void showDateTimeDialog() {
        // 根据是否是开始时间还是结束时间来对应
        final Calendar calendar = start;
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
                        buttonDate.setText(formattedDateTime);
                    }
                })
                .setNegativeButton("cancel", null)
                .show();
    }


}
