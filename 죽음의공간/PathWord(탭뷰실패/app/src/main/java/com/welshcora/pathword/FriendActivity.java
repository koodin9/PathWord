package com.welshcora.pathword;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FriendActivity extends BaseActivity {
    JSONObject returnJson;
    ToServer ts;
    String name,mail;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

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
        ts = new ToServer();
        key.add("mail");
        value.add(PathWord.getGlobalString());

        returnJson = ts.sendToServer("pushFriends.php", key, value);

        for (int i = 0; i < key.size(); i++) {
            key.remove(i);
            value.remove(i);
        }
        mListView = (ListView) findViewById(R.id.listView_friend);
        mAdapter1 = new ListViewAdapter(this);
        for(int i = 0; i < returnJson.length(); i+=2){
            try {
                name = returnJson.getString(String.valueOf(i));
                mail = returnJson.getString(String.valueOf(i+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapter1.addItem(getResources().getDrawable(R.drawable.aka), name +" ", mail,FRIEND_TYPE);
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