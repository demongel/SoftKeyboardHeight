package com.shakespace.softkeyboarddemo.immersedstatus;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.shakespace.softkeyboarddemo.R;
import com.shakespace.softkeyboarddemo.loginwith5497.AndroidBug5497Workaround;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置了状态栏透明后，同样会导致adjustResize无效，（并且出现了类似adjustNothing的效果）
 * 此时使用5497类处理，对红米note有效。
 */
public class ImmersedStatusActivity extends AppCompatActivity {

    @BindView(R.id.btn_back)
    Button mBackBtn;

    @BindView(R.id.constraint_layout)
    ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        AndroidBug5497Workaround.assistActivity(this);
        mBackBtn.setOnClickListener(v -> onBackPressed());
        // 加上背景图测试一下   键盘弹出时，页面会被压缩。
//        mLayout.setBackgroundResource(R.drawable.welcome);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
