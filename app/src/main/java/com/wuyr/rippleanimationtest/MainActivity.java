package com.wuyr.rippleanimationtest;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wuyr.rippleanimation.RippleAnimation;

/**
 * Created by wuyr on 3/15/18 5:21 PM.
 */

public class MainActivity extends AppCompatActivity {

    private View mLeftNavigationView, mRightNavigationView;
    private View[] mChildViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_view);
        initStatusBar();
        mLeftNavigationView = findViewById(R.id.navigation_left);
        mRightNavigationView = findViewById(R.id.navigation_right);
        ViewGroup viewGroup = findViewById(R.id.root_view);
        mChildViews = new View[viewGroup.getChildCount()];
        for (int i = 0; i < mChildViews.length; i++) {
            mChildViews[i] = viewGroup.getChildAt(i);
        }
    }

    public void onClick(View view) {

        RippleAnimation.create().setDuration().start().setOnAnimationEndListener();

        //关键代码
        RippleAnimation.create(view).setDuration(250).start();


        int color;
        switch (view.getId()) {
            case R.id.red:
                color = Color.RED;
                break;
            case R.id.green:
                color = Color.GREEN;
                break;
            case R.id.blue:
                color = Color.BLUE;
                break;
            case R.id.yellow:
                color = Color.YELLOW;
                break;
            case R.id.black:
                color = Color.DKGRAY;
                break;
            case R.id.cyan:
                color = Color.CYAN;
                break;
            default:
                color = Color.TRANSPARENT;
                break;
        }
        updateColor(color);
    }

    private void updateColor(int color) {
        mLeftNavigationView.setBackgroundColor(color);
        mRightNavigationView.setBackgroundColor(color);
        for (View view : mChildViews) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            } else {
                view.setBackgroundColor(color);
            }
        }
    }

    private void initStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }
}
