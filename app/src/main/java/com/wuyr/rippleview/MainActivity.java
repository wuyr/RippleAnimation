package com.wuyr.rippleview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by wuyr on 3/15/18 5:21 PM.
 */

public class MainActivity extends AppCompatActivity {

    private View mNavigationView, mButton1, mButton2;
    private TextView mTextView1, mTextView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_view);
        initStatusBar();
        mNavigationView = findViewById(R.id.navigation_view);
        mButton1 = findViewById(R.id.btn_1);
        mButton2 = findViewById(R.id.btn_2);
        mTextView1 = findViewById(R.id.text);
        mTextView2 = findViewById(R.id.text2);
    }

    public void onClick(View view) {
        RippleAnimation.create(view).setDuration(200).start();
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
            default:
                color = Color.TRANSPARENT;
                break;
        }
        mNavigationView.setBackgroundColor(color);
        mButton1.setBackgroundColor(color);
        mButton2.setBackgroundColor(color);
        mTextView1.setTextColor(color);
        mTextView2.setTextColor(color);
    }

    private void initStatusBar() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
