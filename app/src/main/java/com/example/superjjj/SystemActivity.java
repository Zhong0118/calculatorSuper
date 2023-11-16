package com.example.superjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SystemActivity extends AppCompatActivity {
    private View header;
    private View converButtons;
    private View back;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        // 设置进入动画
        getWindow().setEnterTransition(new Slide());
        // 设置退出动画
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_system);

        header = findViewById(R.id.header);
        converButtons = findViewById(R.id.converbutton2);

        text = findViewById(R.id.textView);
        text.setText("System Scale");

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

}
