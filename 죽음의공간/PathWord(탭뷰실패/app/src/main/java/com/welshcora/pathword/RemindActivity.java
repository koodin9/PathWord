package com.welshcora.pathword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class RemindActivity extends BaseActivity {

    ImageButton FAB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        //폰트 설정
        setFont();
        //툴바 설정 이 엑티비티는 툴바자체가 없기때문에 따로 안만들어줘도 된다
        //setToolbar("단어연상");
        //Drawer 붙이기
        //makeDrawer();
        //커스텀 속성
        //toolbar.setVisibility(View.GONE);
        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindActivity.this ,SearchActivity.class);
                startActivity(intent);            }
        });
    }
}