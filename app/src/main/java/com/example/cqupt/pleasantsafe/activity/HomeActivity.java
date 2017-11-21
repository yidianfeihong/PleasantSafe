package com.example.cqupt.pleasantsafe.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cqupt.pleasantsafe.R;
import com.example.cqupt.pleasantsafe.utils.Constans;
import com.example.cqupt.pleasantsafe.utils.SharedPreferencesUtil;

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
    private AlertDialog mDialog;


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

                String password = SharedPreferencesUtil.getString(getApplicationContext(), Constans.SJFDPSW, null);

                if (TextUtils.isEmpty(password)) {
                    showSetPasswordDialog();

                } else {
                    showCheckPasswordDialog();
                }

                break;

        }


    }

    private void showCheckPasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.home_checkpassword_dialog, null);

        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);


        builder.setView(view);
        mDialog = builder.create();
        builder.show();


        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = mPsw.getText().toString();
                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;

                }
                String sp_psw = SharedPreferencesUtil.getString(getApplicationContext(), Constans.SJFDPSW, null);

                if (sp_psw.equals(password)) {

                    Toast.makeText(HomeActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();

                } else {

                    Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }


            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


    }

    private void showSetPasswordDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.home_setpassword_dialog, null);

        final EditText mPsw = (EditText) view.findViewById(R.id.dialog_et_psw);
        final EditText mConfirm = (EditText) view.findViewById(R.id.dialog_et_confirm);
        Button mOk = (Button) view.findViewById(R.id.dialog_ok);
        Button mCancel = (Button) view.findViewById(R.id.dialog_cancel);

        builder.setView(view);

        mDialog = builder.create();
        mDialog.show();


        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String password = mPsw.getText().toString();
                String confirm = mConfirm.getText().toString();


                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (confirm.equals(password)) {

                    Toast.makeText(HomeActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtil.saveString(getApplicationContext(), Constans.SJFDPSW, password);
                    mDialog.dismiss();

                } else {

                    Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                }


            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });


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
