package com.welshcora.pathword;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SettingFragment extends  BaseFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View connectionView = inflater.inflate(R.layout.fragment_setting,container,false);
        return connectionView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //툴바 설정
        setToolbar("환경설정");
        //폰트 설정
        setFont();
        //커스텀 속성
        mListView = (ListView) getActivity().findViewById(R.id.listView_setting);

        ArrayList<ListData> settingList = new ArrayList<ListData>();
        mAdapter1 = new ListViewAdapter(getActivity());

        mAdapter1.addItem("서체", LABLE_TYPE);
        mAdapter1.addItem("서체 설정", NORMAL_TYPE);
        mAdapter1.addItem("서체 크기", NORMAL_TYPE);
        mAdapter1.addItem("알림 설정", LABLE_TYPE);
        mAdapter1.addItem("알림 받기", SWITCH_TYPE);
        mAdapter1.addItem("알림음", NORMAL_TYPE);
        mAdapter1.addItem("기본 정보", LABLE_TYPE);
        mAdapter1.addItem("버전정보", NORMAL_TYPE);
        mAdapter1.addItem("공지사항", NORMAL_TYPE);
        mAdapter1.addItem("자동로그인", SWITCH_TYPE);
        mListView.setAdapter(mAdapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                if(mData.mTitle.equals("서체 설정")){
                    FontSetDialog fd = new FontSetDialog();
                    fd.show(getFragmentManager(), "MYTGAGGG");
                } else if(mData.mTitle.equals("서체 크기")){
                    FontSizeDialog fd = new FontSizeDialog();
                    fd.show(getFragmentManager(), "MYTGAGGG");
                } else if(mData.mTitle.equals("알림음")){
                    RingtonDialog rd = new RingtonDialog();
                    rd.show(getFragmentManager(), "MYTGAGGG");
                } else if(mData.mTitle.equals("버전정보")){
                    Intent intent = new Intent(getActivity(), VersionActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

}