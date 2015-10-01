package com.welshcora.pathword;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PointActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("나의 포인트");
        //Drawer 붙이기
        //makeDrawer();
        TextView point = (TextView) findViewById(R.id.point);
        point.setText("123,456");
    }
}