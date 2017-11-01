package com.ali.question.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ali.question.R;
import com.rey.material.widget.Slider;

public class SettingActivity extends Activity {

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Slider slider =(Slider)findViewById(R.id.slider);
        final TextView sample =(TextView)findViewById(R.id.sample);
        btnSave = (Button)findViewById(R.id.btn_seting_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        slider.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider slider, boolean b, float v, float v1, int i, int i1) {
                Log.i("value","value = "+i1);
                assert sample != null;
                sample.setTextSize((float)i1);
                SharedPreferences.Editor editor = getSharedPreferences("font_size",MODE_PRIVATE).edit();
                editor.putInt("fontsize",i1);
                editor.commit();
            }
        });
    }
}
