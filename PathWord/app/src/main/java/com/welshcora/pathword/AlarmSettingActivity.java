package com.welshcora.pathword;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;


public class AlarmSettingActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmsetting);

        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("알람설정");
        //Drawer 붙이기
        //makeDrawer();

        //커스텀 속성
        mListView = (ListView) findViewById(R.id.listView_alarmSetting);
        ArrayList<ListData> settingList = new ArrayList<ListData>();
        mAdapter1 = new ListViewAdapter(this);
        mAdapter1.addItem(ALARMTIMEPICKER_TYPE);
        mAdapter1.addItem("요일 반복", LABLE_TYPE);
        mAdapter1.addItem(ALARMDATE_TYPE);
        mAdapter1.addItem("알람 방식", LABLE_TYPE);
        mAdapter1.addItem("벨소리", NORMAL_TYPE);
        mAdapter1.addItem("알람음", LABLE_TYPE);
        mAdapter1.addItem("아침이 우는 소리", NORMAL_TYPE);
        mAdapter1.addItem("소리", LABLE_TYPE);
        mAdapter1.addItem(ALARMSOUND_TYPE);
        mAdapter1.addItem("알람 문항 갯수", LABLE_TYPE);
        mAdapter1.addItem("5문항", NORMAL_TYPE);
        mAdapter1.addItem("다시 울림 간격", LABLE_TYPE);
        mAdapter1.addItem("5분", NORMAL_TYPE);
        mAdapter1.addItem(ALARMDELBUTTON_TYPE);

        mListView.setAdapter(mAdapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                /*if(mData.mTitle.equals("서체 설정")){
                    FontSetDialog fd = new FontSetDialog();
                } else if(mData.mTitle.equals("서체 크기")){
                    FontSizeDialog fd = new FontSizeDialog();
                } else if(mData.mTitle.equals("알림음")){
                    RingtonDialog rd = new RingtonDialog();
                } else if(mData.mTitle.equals("버전정보")){
                }*/
            }
        });
    }
}