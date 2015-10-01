package com.welshcora.pathword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DuckG on 2015-09-02.
 */
public class TapTestFragment extends BaseFragment {
    DbAdapter mDbAdapter;
    TextView wordTest;
    TextView rootTest;
    TextView wrongNote;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tap_test,container,false);
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

        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문

        mListView = (ListView) getActivity().findViewById(R.id.listView_tap_test);

        mAdapter1 = new ListViewAdapter(getActivity());

        ArrayList<String> testData = mDbAdapter.getTest();//날짜0, 문제1, 정답2, 오답3, 타입4, 랭크5
        for(int i = 0; i < testData.size() / 6; i++){
            if(testData.get(6*i+4).equals("word")){
                mAdapter1.addItem(testData.get(6*i).substring(0, 10), Integer.parseInt(testData.get(6*i+5)), testData.get(6*i+1), testData.get(6*i+2), testData.get(6*i+3), WORDBOOK_TEST_TYPE_WORD);
            } else {
                mAdapter1.addItem(testData.get(6*i).substring(0, 10), Integer.parseInt(testData.get(6*i+5)), testData.get(6*i+1), testData.get(6*i+2), testData.get(6*i+3), WORDBOOK_TEST_TYPE_ROOT);
            }
        }
        mListView.setAdapter(mAdapter1);


        wordTest = (TextView) getActivity().findViewById(R.id.WordTest);
        wordTest.setText(String.valueOf(mDbAdapter.SelectWordBook("recent").size() / 3));
        wordTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordBookDialogWordTest fd = new WordBookDialogWordTest();
                fd.show(getFragmentManager(), "MYTGAGGG");
            }
        });

        rootTest = (TextView) getActivity().findViewById(R.id.RootTest);
        rootTest.setText(String.valueOf(mDbAdapter.SelectWordBook("recent").size() / 3));
        rootTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordBookDialogRootTest fd = new WordBookDialogRootTest();
                fd.show(getFragmentManager(), "MYTGAGGG");
            }
        });
        wrongNote = (TextView) getActivity().findViewById(R.id.WrongNote);
        rootTest.setText(String.valueOf(mDbAdapter.getWrongNote().size() / 3));
        rootTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordBookDialogRootTest fd = new WordBookDialogRootTest();
                fd.show(getFragmentManager(), "MYTGAGGG");
            }
        });

    }
}