package com.welshcora.pathword;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;


public class VersionActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("버전정보");
        //Drawer 붙이기
        //makeDrawer();
        //커스텀 속성
    }
}