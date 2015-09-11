package com.welshcora.pathword;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class FriendActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("친구 목록");
        //Drawer 붙이기
        //makeDrawer();
        //커스텀 속성
        mListView = (ListView) findViewById(R.id.listView_friend);
        mAdapter1 = new ListViewAdapter(this);
        for(int i = 0; i < 50; i++){
            mAdapter1.addItem(getResources().getDrawable(R.drawable.aka), "친구" + (i+1), "(멍청이)",FRIEND_TYPE);
        }
        mListView.setAdapter(mAdapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //ListData mData = mAdapter1.tempList.get(position);
            }
        });
    }
}