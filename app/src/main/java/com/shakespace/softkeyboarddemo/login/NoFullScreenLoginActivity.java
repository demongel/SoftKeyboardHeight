package com.shakespace.softkeyboarddemo.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.shakespace.softkeyboarddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 非全屏，直接设置adjustResize即可
 * 只需要将中间空间留足即可
 * 把背景和主题色改成一样，使用AppTheme（MD）系列主题，可以实现类似沉浸式效果
 * AppTheme没有NoAcionBar主题，需要自己新建
 *
 * 缺点：当背景是图片时，无法实现头部透明效果，使图片充满屏幕
 */
public class NoFullScreenLoginActivity extends AppCompatActivity {

    //
    @BindView(R.id.btn_back)
    Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //  没加返回键  用登录当返回
        mBackBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
