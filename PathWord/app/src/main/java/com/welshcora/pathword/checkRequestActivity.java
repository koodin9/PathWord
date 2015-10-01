package com.welshcora.pathword;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class checkRequestActivity extends BaseActivity {
    public static final int FRIEND_TYPE = 6;


    JSONObject returnJson;
    ToServer ts;
    String name,mail;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_friend);
        setToolbar("요청 목록");
        ts = new ToServer();
        key.add("mail");
        value.add(PathWord.getGlobalString());

        returnJson = ts.sendToServer("pushRequest.php", key, value);

        for (int i = 0; i < key.size(); i++) {
            key.remove(i);
            value.remove(i);
        }


        mListView = (ListView) findViewById(R.id.listView_friendrequest);
        mAdapter1 = new ListViewAdapter(this);
        for(int i = 0; i < returnJson.length(); i+=2){
            try {
                name = returnJson.getString(String.valueOf(i));
                mail = returnJson.getString(String.valueOf(i+1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapter1.addItem(getResources().getDrawable(R.drawable.aka), name, mail ,FRIENDREQUEST_TYPE);
        }
        mListView.setAdapter(mAdapter1);

    }
}