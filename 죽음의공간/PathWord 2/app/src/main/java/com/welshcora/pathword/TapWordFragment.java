package com.welshcora.pathword;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by DuckG on 2015-09-02.
 */
public class TapWordFragment extends BaseFragment {
    DbAdapter mDbAdapter;
    String currentDateOrAlphabet = "";
    TextView recent;
    TextView alphabet;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tap_word,container,false);
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
        createList("recent");
        recent = (TextView) getActivity().findViewById(R.id.recent);
        alphabet = (TextView) getActivity().findViewById(R.id.alphabet);
        recent.setTextColor(Color.BLACK);
        alphabet.setTextColor(Color.GRAY);

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList("recent");
                recent.setTextColor(Color.BLACK);
                alphabet.setTextColor(Color.GRAY);
            }
        });

        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList("alphabet");
                alphabet.setTextColor(Color.BLACK);
                recent.setTextColor(Color.GRAY);
            }
        });

    }
    public void createList(String type){
        mListView = (ListView) getActivity().findViewById(R.id.listview_tap_word);
        mAdapter1 = new ListViewAdapter(getActivity());
        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문
        ArrayList<String> items = mDbAdapter.SelectWordBook(type);
        for(int i = 0; i < items.size() / 3; i++){//0 : 날짜, 1 : 단어, 2 : 설명, 3으로 나눈건 리스트뷰 하나에 들어가는 속성이 3개이기 때문
            String temp = items.get(i*3);
            Log.v("받아온 값", items.get(i*3));
            StringTokenizer token = new StringTokenizer(temp, "/");

            token.nextToken();
            String temp1 = token.nextToken();
            if(temp1.charAt(0) == '0'){
                temp1 = temp1.substring(1);
            }
            String temp2 = token.nextToken();
            temp2 = temp2.substring(0, 2);

            if(temp2.charAt(0) == '0'){
                temp2 = temp2.substring(1);
            }

            Log.v("워드북날짜", temp1 + "/" + temp2);
            Log.v("워드북생생한날짜", items.get(i));
            Log.v("워드북단어", items.get(i*3 + 1));
            Log.v("워드북설명", items.get(i*3 + 2));
            String temp3 = "";
            if(type.equals("recent")){
                temp3 = temp1 + "/" + temp2;
            } else if (type.equals("alphabet")){
                temp3 = Character.toString(items.get(i*3 + 1).charAt(0));
            }
            if(temp3.equals(currentDateOrAlphabet)){
                mAdapter1.addItem(" ", items.get(i*3 + 1), items.get(i*3 + 2).substring(0,10) + "...", WORDBOOK_WORD_TYPE);
            } else {
                mAdapter1.addItem(temp3, items.get(i*3 + 1), items.get(i*3 + 2).substring(0,10) + "...", WORDBOOK_WORD_TYPE);
            }
            currentDateOrAlphabet = temp3;
        }
        mAdapter1.notifyDataSetChanged();
        mListView.setAdapter(mAdapter1);
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                if(mData.mTitle.equals("서체 설정")) {
                    FontSetDialog fd = new FontSetDialog();
                    fd.show(getFragmentManager(), "MYTGAGGG");
                }
            }
        });
        */
    }
}