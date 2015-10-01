package com.welshcora.pathword;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Created by DuckG on 2015-09-02.
 */
public class TapRootFragment extends BaseFragment {
    TextView recent;
    TextView alphabet;
    DbAdapter mDbAdapter;

    String word;
    GridLayout dynamicGridLayout;

    JSONObject returnJson;
    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tap_root,container,false);
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
        recent = (TextView) getActivity().findViewById(R.id.recent1);
        alphabet = (TextView) getActivity().findViewById(R.id.alphabet1);
        dynamicGridLayout = (GridLayout) getActivity().findViewById(R.id.gridLayout);
        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문

        createButtonList("recent");

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicGridLayout.removeAllViews();
                recent.setTextColor(Color.BLACK);
                alphabet.setTextColor(Color.GRAY);
                createButtonList("recent");
            }
        });
        alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicGridLayout.removeAllViews();
                recent.setTextColor(Color.GRAY);
                alphabet.setTextColor(Color.BLACK);
                createButtonList("alphabet");
            }
        });


    }
    public void createButtonList(String type){
        ts = new ToServer();
        key.add("");
        value.add("");
        returnJson = ts.sendToServer("fetchReminded.php", key, value);

        final ArrayList<getFromServer> getFromServers = new ArrayList<getFromServer>();
        String temp1 = "";
        getFromServer rootObject = new getFromServer();
        for(int i = 0; i < returnJson.length(); i++){
            try {
                temp1 = returnJson.getString(String.valueOf(i));
                if(temp1.equals("")) continue;
                Log.v("템프값 받아옴", temp1);
                if('0' <= temp1.charAt(0) && temp1.charAt(0) <= '9'){ //첫번째 문자가 숫자면
                    if(i != 0){
                        getFromServers.add(rootObject);
                        Log.v("객체 추가", temp1);
                    }
                    rootObject = new getFromServer();
                    Log.v("객체 생성", temp1);
                    rootObject.date = temp1.substring(0, temp1.length()-4);
                    Log.v("날짜", temp1.substring(0, temp1.length()-4));
                } else {
                    rootObject.wordRemind.add(temp1);
                    Log.v("연관단어", temp1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getFromServers.add(rootObject);

        for(int i = 0; i < getFromServers.size(); i++){
            getFromServers.get(i).startword = getFromServers.get(i).wordRemind.get(0);
            Log.v("시작단어",getFromServers.get(i).startword);
            Log.v("생성일", getFromServers.get(i).date);
            for(int j = 0; j < getFromServers.get(i).wordRemind.size(); j++){
                Log.v("루트단어들", getFromServers.get(i).wordRemind.get(j));
            }

        }
        if(type.equals("alphabet")){
            Collections.sort(getFromServers, new Comparator<getFromServer>() {
                @Override
                public int compare(getFromServer obj1, getFromServer obj2) {
                    // TODO Auto-generated method stub
                    return obj1.startword.compareToIgnoreCase(obj2.startword);
                }
            });
        }
        for(int i = 0; i < getFromServers.size(); i++){//사용자의 시작 단어의 수만큼 버튼을 만들어준다.
            Button temp = new Button(getActivity());
            temp.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            String description[] = mDbAdapter.SearchWord2(getFromServers.get(i).startword).get(0).split(", ");
            String resultDescription = "[";
            for (int j = 0; j < description.length; j++) { // 몇번째까지가 단어 부호냐?, 첫번째 설명을 뽑아내기 위한 루틴
                if(0 <= j && j < 4) {//4번까지만 form 검사
                    char first = description[j].charAt(0); char last = description[j].charAt(description[j].length()-1);
                    if (('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') ||
                            ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') || description[j].length() <= 4) {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                    } else {
                        resultDescription = description[j+1];
                        break;
                    }
                } else {
                    resultDescription = description[j+1];
                    break;
                }
            }
            temp.setTypeface(fontChanger.typeface);
            temp.setText(getFromServers.get(i).startword + "\n" + resultDescription);
            final int finalI = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    word = getFromServers.get(finalI).startword;// 버튼을 선택했을때 찾으려는 단어
                    String date = getFromServers.get(finalI).date.substring(0, 10);
                    createList(word, date, getFromServers.get(finalI).wordRemind);
                }
            });
            dynamicGridLayout.addView(temp);
        }
    }
    public void createList(String word, String date, final ArrayList<String> wordRemind){
        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문
        mListView = (ListView) getActivity().findViewById(R.id.listview_tap_root);
        mAdapter1 = new ListViewAdapter(getActivity());//부모의 체크박스를 넘겨준다.

        ArrayList<String> rowDescription;
        for(int i = 0; i < wordRemind.size(); i++){


            TextView dateView = (TextView) getActivity().findViewById(R.id.date);//날자를 입력해준다.
            dateView.setText(date);
            rowDescription = mDbAdapter.SearchWord2(wordRemind.get(i));//단어형, [발음기호], 설명1, 설명2 ...

            String description[] = rowDescription.get(0).split(", "); //설명 부분을 토큰으로 나눈다.
            String pronoun = "";
            String resultDescription = "[";
            for (int j = 0; j < description.length; j++) { // 몇번째까지가 단어 부호냐?
                if(0 <= j && j < 4) {//4번까지만 form 검사
                    char first = description[j].charAt(0); char last = description[j].charAt(description[j].length()-1);
                    if ((('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') ||
                            ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') && description[j].length() <= 2)) {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                    } else {
                        pronoun = description[j];
                        resultDescription = description[j+1];
                        mAdapter1.addItem(wordRemind.get(i), resultDescription, pronoun/*발음*/, WORDBOOK_ROOT_TYPE);
                        break;
                    }
                } else {
                    pronoun = description[j];
                    resultDescription = description[j+1];
                    mAdapter1.addItem(wordRemind.get(i), resultDescription, pronoun/*발음*/, WORDBOOK_ROOT_TYPE);
                    break;
                }
            }
        }

        mAdapter1.notifyDataSetChanged();
        mListView.setAdapter(mAdapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                wordBook_RootData mData = (wordBook_RootData)mAdapter1.tempList.get(position);
                WordBookDialog2 fd = new WordBookDialog2();
                Bundle args = new Bundle();
                args.putString("key2", mData.word);
                args.putStringArrayList("key", wordRemind);
                fd.setArguments(args);
                fd.show(getFragmentManager(), "MYTGAGGG");
                Log.v("선택한 단어", mData.word);
                /*if(mData.mTitle.equals("서체 설정")) {
                    FontSetDialog fd = new FontSetDialog();
                    fd.show(getFragmentManager(), "MYTGAGGG");
                }*/
            }
        });
    }
}
class getFromServer  {
    String startword;
    ArrayList<String> wordRemind = new ArrayList<String>();
    String date;
}