package com.welshcora.pathword;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuckG on 2015-09-02.
 */
public class TapSearchFriend extends BaseFragment {

    EditText friendMail;
    JSONObject returnJson;
    String friendMail2,myMail;
    TextView nameNmail;
    Button addFriend;

    EditText search;

    String name;
    String mail;

    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tap_searchfriend,container,false);
        ts = new ToServer();
        addFriend = (Button) getActivity().findViewById(R.id.search_button);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //툴바 설정
        //setToolbar("메인화면");
        //toolbar.setVisibility(View.GONE);
        //폰트 설정
        setFont();
        //커스텀 속성
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = (EditText) getActivity().findViewById(R.id.search);
                key.add("myMail");
                value.add(PathWord.getGlobalString());
                key.add("friendMail");
                value.add(search.getText().toString());

                returnJson = ts.sendToServer("findFriend.php", key, value);

                for (int i = 0; i < key.size(); i++) {
                    key.remove(i);
                    value.remove(i);
                }

                try {
                    final String str = returnJson.getString("flag");
                    if (str.equals("N")) {
                        Toast.makeText(getActivity(), "존재하지 않은 메일주소입니다.", Toast.LENGTH_LONG).show();
                    } else if (str.equals("pre")) {
                        Toast.makeText(getActivity(), "이미 친구입니다.", Toast.LENGTH_LONG).show();
                    } else {
                        friendMail2 = returnJson.getString("mail");

                        mListView = (ListView) getActivity().findViewById(R.id.listView_friend);
                        mAdapter1 = new ListViewAdapter(getActivity());
                        mAdapter1.addItem(getResources().getDrawable(R.drawable.aka), str, friendMail2,FRIENDREQUEST_TYPE2);
                        mListView.setAdapter(mAdapter1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
