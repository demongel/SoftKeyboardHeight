package com.shakespace.softkeyboarddemo.loginwithpopwindow;

import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shakespace.softkeyboarddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginWithPopWindowActivity extends AppCompatActivity implements KeyboardHeightObserver {
    private static final String TAG = "LoginWithPopWindow";

    private KeyboardHeightProvider keyboardHeightProvider;


    @BindView(R.id.btn_back)
    Button mBackBtn;

    @BindView(R.id.constraint_layout)
    ConstraintLayout mLayout;
    private int oriBottomMargin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        //  设置了背景 弹出会有延迟，效果不佳（所有方法都是如此）  使用纯色或者头部背景比较方便
//        mLayout.setBackgroundResource(R.drawable.welcome);
        mLayout.post(new Runnable() {
            public void run() {
                keyboardHeightProvider.start();

                //  获取元素的初始位置
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBackBtn.getLayoutParams();
                oriBottomMargin = layoutParams.bottomMargin;
            }
        });

        mBackBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        String or = orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Log.i(TAG, "onKeyboardHeightChanged in pixels: " + height + " " + or);

        if(height>0) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBackBtn.getLayoutParams();
            layoutParams.bottomMargin = height;
            mBackBtn.setLayoutParams(layoutParams);
        }else {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBackBtn.getLayoutParams();
            layoutParams.bottomMargin = oriBottomMargin;
            mBackBtn.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }
}
