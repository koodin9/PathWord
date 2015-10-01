package com.welshcora.pathword;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RankActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("랭크");
        //Drawer 붙이기
        //makeDrawer();
        //커스텀 설정
        mListView = (ListView) findViewById(R.id.listView_rank);
        mAdapter1 = new ListViewAdapter(this);
        for(int i = 0; i < 100; i++){
            mAdapter1.addItem((i+1) +"등", getResources().getDrawable(R.drawable.aka), "김지현", "멍청이","고수",RANKLIST_TYPE);
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