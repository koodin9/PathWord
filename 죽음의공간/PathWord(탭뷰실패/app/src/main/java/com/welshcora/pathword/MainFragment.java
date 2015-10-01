package com.welshcora.pathword;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View connectionView = inflater.inflate(R.layout.fragment_main,container,false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
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