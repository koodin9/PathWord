package com.welshcora.pathword;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends BaseFragment {

    ToServer ts;
    JSONObject returnJson;
    Button requested;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View connectionView = inflater.inflate(R.layout.fragment_main,container,false);
        return connectionView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //툴바 설정
        setToolbar("메인화면");
        toolbar.setVisibility(View.GONE);
        //폰트 설정
        setFont();
        //커스텀 속성
        requested = (Button) getActivity().findViewById(R.id.requested);


        key.add("myMail");
        value.add(PathWord.getGlobalString());
        ts = new ToServer();
        returnJson = ts.sendToServer("friendRequest.php",key,value);
        try {
            if(returnJson.getString("flag").equals("exist")) {
                Log.v("aaaa", returnJson.getString("flag"));
                Drawable request_new = getResources().getDrawable(R.drawable.request_new);
                requested.setBackground(request_new);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void onResume() { //메인 프래그먼트가 종료되면 메인 엑티비티가 종료되면서 어플이 종료된다.
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}