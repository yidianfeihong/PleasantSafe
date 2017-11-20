package com.example.cqupt.pleasantsafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cqupt.pleasantsafe.R;
import com.example.cqupt.pleasantsafe.utils.Constans;
import com.example.cqupt.pleasantsafe.utils.SharedPreferencesUtil;
import com.example.cqupt.pleasantsafe.view.SettingView;

public class SettingActivity extends AppCompatActivity {

    private SettingView mUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

    }

    private void initView() {
        mUpdate = (SettingView) findViewById(R.id.setting_sv_update);
        update();

    }

    private void update() {

        boolean isToggledOn = SharedPreferencesUtil.getBoolean(getApplicationContext(), Constans.ISAUTOUPDATE, false);
        mUpdate.setToggleOn(isToggledOn);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUpdate.toggle();
                SharedPreferencesUtil.saveBoolean(getApplicationContext(), Constans.ISAUTOUPDATE, mUpdate.isToggleOn());
            }
        });

    }

}
