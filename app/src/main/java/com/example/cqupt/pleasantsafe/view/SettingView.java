package com.example.cqupt.pleasantsafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cqupt.pleasantsafe.R;

/**
 * Created by Ming on 2017/11/18.
 */


public class SettingView extends RelativeLayout {


    private final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private ImageView mIcon;
    private TextView mTitle;
    private boolean mIsToggled;

    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        String title = attrs.getAttributeValue(NAMESPACE, "title");
        mTitle.setText(title);

    }

    private void initView() {

        View view = View.inflate(getContext(), R.layout.settingview, null);
        mIcon = (ImageView) view.findViewById(R.id.setting_iv_icon);
        mTitle = (TextView) view.findViewById(R.id.setting_tv_title);

        this.addView(view);
    }


    public void setToggleOn(boolean isToggled) {

        mIsToggled = isToggled;

        if (isToggled) {

            mIcon.setImageResource(R.drawable.on);

        } else {

            mIcon.setImageResource(R.drawable.off);
        }
    }


    public void toggle() {
        setToggleOn(!mIsToggled);
    }

    public boolean isToggleOn() {

        return mIsToggled;
    }


}
