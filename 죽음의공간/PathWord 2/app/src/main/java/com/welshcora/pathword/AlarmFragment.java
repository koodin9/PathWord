package com.welshcora.pathword;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;


public class AlarmFragment extends BaseFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View connectionView = inflater.inflate(R.layout.fragment_setting,container,false);
        return connectionView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //툴바 설정
        setToolbar("알람");
        //폰트 설정
        setFont();
        //커스텀 속성
        mListView = (ListView) getActivity().findViewById(R.id.listView_setting);

        ArrayList<ListData> settingList = new ArrayList<ListData>();
        mAdapter1 = new ListViewAdapter(getActivity());

        mAdapter1.addItem(ALARMADDBUTTON_TYPE);

        mListView.setAdapter(mAdapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                if (mData.mTitle.equals(Integer.toString(position))){//엑티비티사이의 정보교환 필요
                    Intent intent = new Intent(getActivity(), AlarmSettingActivity.class);
                    startActivityForResult(intent, 1);
                }
                /*if(mData.mTitle.equals("서체 설정")){
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
                }*/
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            mAdapter1.addItem(data.getStringExtra("Hour"), data.getStringExtra("Minute"), "AM", "월,화,목요일마다", ALARMMAIN_TYPE);
            mAdapter1.notifyDataSetChanged();
        }
    }
    public static AlarmFragment newInstance() {
        AlarmFragment fragment = new AlarmFragment();
        return fragment;
    }
}