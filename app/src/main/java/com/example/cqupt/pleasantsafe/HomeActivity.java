package com.example.cqupt.pleasantsafe;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private ImageView mLogo;
    private GridView mGridView;

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

        ininView();

    }

    private void ininView() {

        mLogo = (ImageView) findViewById(R.id.home_iv_logo);
        mGridView = (GridView) findViewById(R.id.home_gv_gridview);
        mGridView.setAdapter(new GridViewAdapter());
//        setAnimation();

    }

    private void setAnimation() {


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLogo, "rotationY", 0f, 180f, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.start();
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
