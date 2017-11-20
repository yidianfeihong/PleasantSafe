package com.example.cqupt.pleasantsafe.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cqupt.pleasantsafe.R;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ImageView mLogo;
    private GridView mGridView;
    private ImageView mSetting;


    private final String[] TITLES = new String[]{"手机防盗", "骚扰拦截", "软件管家",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具"};
    private final String[] DESCS = new String[]{"远程定位手机", "全面拦截骚扰", "管理您的软件",
            "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全"};
    private final int[] ICONS = new int[]{R.drawable.sjfd, R.drawable.srlj,
            R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
            R.drawable.hcql, R.drawable.cygj};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();

    }

    private void initView() {

        mLogo = (ImageView) findViewById(R.id.home_iv_logo);
        mGridView = (GridView) findViewById(R.id.home_gv_gridview);
        mGridView.setAdapter(new GridViewAdapter());
        mGridView.setOnItemClickListener(this);
        mSetting = (ImageView) findViewById(R.id.home_iv_setting);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSetting(v);
            }
        });
        setAnimation();

    }

    private void enterSetting(View v) {

        Intent intent = new Intent(this, SettingActivity.class);

        startActivity(intent);

    }

    private void setAnimation() {


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLogo, "rotationY", 0f, 90f, 270f, 360f);
        objectAnimator.setDuration(2000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                showSetPasswordDialog();

                break;

        }


    }

    private void showSetPasswordDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.home_setpassword_dialog,null);
        builder.setView(view);
        builder.show();


    }

    private class GridViewAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            View view = View.inflate(getApplicationContext(), R.layout.home_gridview_item, null);
            ImageView mIcon = (ImageView) view.findViewById(R.id.item_iv_icon);
            TextView mTitle = (TextView) view.findViewById(R.id.item_tv_title);
            TextView mDesc = (TextView) view.findViewById(R.id.item_tv_desc);

            mIcon.setImageResource(ICONS[i]);
            mTitle.setText(TITLES[i]);
            mDesc.setText(DESCS[i]);
            return view;
        }
    }


}
