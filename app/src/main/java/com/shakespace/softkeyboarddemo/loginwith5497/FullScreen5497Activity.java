package com.shakespace.softkeyboarddemo.loginwith5497;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.shakespace.softkeyboarddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实现全屏效果，不显示statusbar
 * 全屏效果与adjustResize冲突，
 * 使用AndroidBug5497Workaround，大部分机型可以适配，但是红米note2不行。
 * 缺点： 除了个别适配问题外，也可能出现错误判定，例如如果底部view有大小调整或者消失之类，可能出现误调整。
 */
public class FullScreen5497Activity extends AppCompatActivity {


    @BindView(R.id.btn_back)
    Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        AndroidBug5497Workaround.assistActivity(this);
        //  没加返回键  用登录当返回
        mBackBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
