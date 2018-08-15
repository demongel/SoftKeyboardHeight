package com.shakespace.softkeyboarddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shakespace.softkeyboarddemo.immersedstatus.ImmersedStatusActivity;
import com.shakespace.softkeyboarddemo.login.NoFullScreenLoginActivity;
import com.shakespace.softkeyboarddemo.loginwith5497.FullScreen5497Activity;
import com.shakespace.softkeyboarddemo.loginwithpopwindow.LoginWithPopWindowActivity;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.no_actionbar,R.id.fullscreen_5497,R.id.immersed_status_5497,R.id.fullscreen_with_popwindow})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.no_actionbar:
                Intent intent1 = new Intent(MainActivity.this, NoFullScreenLoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.fullscreen_5497:
                Intent intent2 = new Intent(MainActivity.this, FullScreen5497Activity.class);
                startActivity(intent2);
                break;

            case R.id.immersed_status_5497:
                Intent intent3 = new Intent(MainActivity.this, ImmersedStatusActivity.class);
                startActivity(intent3);
                break;

            case  R.id.fullscreen_with_popwindow:
                Intent intent4 = new Intent(MainActivity.this, LoginWithPopWindowActivity.class);
                startActivity(intent4);
                break;
        }
    }


}
