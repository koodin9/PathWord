package com.welshcora.pathword;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by DuckG on 2015-09-02.
 */
public class TapWordFragment extends BaseFragment {
    DbAdapter mDbAdapter;
    TextView recent;
    TextView alphabet;
    TextView selectAll;
    TextView delete;
    TextView test;
    CheckBox checkBox;
    LinearLayout showUpLayout;
    int itemCount;

    ArrayList<String> wordList = new ArrayList<String>(); //리스트뷰를 클릭했을때 단어정보 띄우게 하는 단어들의 집합

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

        showUpLayout = (LinearLayout) getActivity().findViewById(R.id.showuplayout);
        selectAll = (TextView) getActivity().findViewById(R.id.selectall);
        delete = (TextView) getActivity().findViewById(R.id.delete);
        checkBox = (CheckBox) getActivity().findViewById(R.id.checkBox);
        recent = (TextView) getActivity().findViewById(R.id.recent);
        alphabet = (TextView) getActivity().findViewById(R.id.alphabet);
        test = (TextView) getActivity().findViewById(R.id.test);
        //showUpLayout.setVisibility(View.GONE);//단어장 들어갔다가 나와서 환경설정 들어간다음에 폰트바꾸면 에러남


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> victimWord = new ArrayList<String>();
                for(int i = 0; i < mAdapter1.tempList.size(); i++){
                    if(mAdapter1.selectedWord.get(i)){
                        Log.v("선택된 아이템들", ((wordBook_WordData) mAdapter1.tempList.get(i)).word);
                        victimWord.add(((wordBook_WordData) mAdapter1.tempList.get(i)).word);
                    }
                }
                if(victimWord.size() == 0){
                    Toast.makeText(getActivity(), "테스트할 단어를 선택해주세요!", Toast.LENGTH_LONG).show();
                } else {
                    WordBookDialogWordTest fd = new WordBookDialogWordTest();
                    Bundle args = new Bundle();
                    args.putStringArrayList("key", victimWord);
                    fd.setArguments(args);
                    fd.show(getFragmentManager(), "MYTGAGGG");
                }
            }
        });

        selectAll.setOnClickListener(new View.OnClickListener() {
            boolean select = true;

            @Override
            public void onClick(View v) {
                if(select){
                    selectAll.setText("선택취소");
                    Log.v("전체야전체", String.valueOf(mAdapter1.selectedWord.size()));
                    for(int i = 0; i < mAdapter1.selectedWord.size(); i++){
                        mAdapter1.selectedWord.put(i, true);
                    }
                    select = !select;
                } else {
                    selectAll.setText("전체선택");
                    Log.v("전체야전체", String.valueOf(mAdapter1.selectedWord.size()));
                    for(int i = 0; i < mAdapter1.selectedWord.size(); i++){
                        mAdapter1.selectedWord.put(i, false);
                    }
                    select = !select;
                }
                mAdapter1.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> victimWord = new ArrayList<String>();
                //다이얼로그 dismiss 리스너를 이용해 사용자의 인풋을 다시한번 확인한다.
                class CustomDismissListener extends DialogDismissListener {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(getValueForBool(WordBookDialog.KEY_RESULT, false)) {
                            for(int i = 0; i < mAdapter1.tempList.size(); i++){
                                if(mAdapter1.selectedWord.get(i)){
                                    Log.v("선택된 아이템들", ((wordBook_WordData) mAdapter1.tempList.get(i)).word);
                                    victimWord.add(((wordBook_WordData) mAdapter1.tempList.get(i)).word);
                                }
                            }
                            for(int i = 0; i < victimWord.size(); i++){
                                mDbAdapter.DeletedWordBook(victimWord.get(i));
                                for(int j = 0; j < mAdapter1.tempList.size(); j++){
                                    if(victimWord.get(i).equals(((wordBook_WordData)mAdapter1.tempList.get(j)).word)){ //실제 리스트뷰에서 단어를 삭제하기 위한 루틴. 먼저 지워질 단어와 templist의 워드라 비교한다.
                                        if(!((wordBook_WordData) mAdapter1.tempList.get(j)).dateOrAlphabet.equals(" ")){//만약 그 해당 리스트 뷰의 헤더가 날짜나 알파벳이 아닌 공백이 아니면
                                            if(j+1 != mAdapter1.tempList.size() && ((wordBook_WordData) mAdapter1.tempList.get(j+1)).dateOrAlphabet.equals(" ")){ //j+1은 자료형의 사이즈 보다 작아야 하며 그 다음에 오는 j+1번째의 리스트뷰가 공백이면
                                                ((wordBook_WordData) mAdapter1.tempList.get(j+1)).dateOrAlphabet = ((wordBook_WordData) mAdapter1.tempList.get(j)).dateOrAlphabet;//그 공백을 날짜나 알파벳으로 치환해준다.
                                            }
                                        }
                                        Log.v("지워진 아이템들", ((wordBook_WordData) mAdapter1.tempList.get(j)).word);
                                        mAdapter1.tempList.remove(j);
                                        wordList.remove(j);
                                    }
                                }
                            }
                            mAdapter1.notifyDataSetChanged();
                        }

                        checkBox.setChecked(false);
                        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, showUpLayout.getHeight());
                        animate.setDuration(500);
                        animate.setFillAfter(true);
                        showUpLayout.startAnimation(animate);
                        showUpLayout.setVisibility(View.GONE);

                        }
                    }
                if(mAdapter1.selectedWord.size() == 0){
                    Toast.makeText(getActivity(), "삭제할 단어를 선택해주세요!", Toast.LENGTH_LONG).show();
                } else {
                    WordBookDialog fragment = new WordBookDialog();
                    fragment.setDismissListener(new CustomDismissListener()); //리스너 넣기
                    fragment.show(getFragmentManager(), "dialog");
                }
            };
        });

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

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, 0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    showUpLayout.startAnimation(animate);
                    showUpLayout.setVisibility(View.VISIBLE);
                } else {
                    TranslateAnimation animate = new TranslateAnimation(0, 0, 0, showUpLayout.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    showUpLayout.startAnimation(animate);
                    showUpLayout.setVisibility(View.GONE);
                }
                mAdapter1.notifyDataSetChanged();
            }
        });

        createList("recent");
    }
    public void createList(String type){
        mDbAdapter = new DbAdapter(getActivity()); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문
        ArrayList<String> items = mDbAdapter.SelectWordBook(type);
        mListView = (ListView) getActivity().findViewById(R.id.listview_tap_word);
        itemCount = items.size();
        mAdapter1 = new ListViewAdapter(getActivity(), checkBox, itemCount);//부모의 체크박스를 넘겨준다.
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String currentDateOrAlphabet = "";

        for(int i = 0; i < items.size() / 3; i++){//0 : 날짜, 1 : 단어, 2 : 설명, 3으로 나눈건 리스트뷰 하나에 들어가는 속성이 3개이기 때문
            wordList.add(items.get(i*3 + 1));
            String temp = items.get(i*3);
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

            String temp3 = "";
            if(type.equals("recent")){
                temp3 = temp1 + "/" + temp2;
            } else if (type.equals("alphabet")){
                temp3 = Character.toString(items.get(i*3 + 1).charAt(0));
            }
            Log.v("temp 3",temp3);
            String description[] = items.get(i*3 + 2).split(", "); //설명 부분을 토큰으로 나눈다.
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
                        break;
                    }
                } else {
                    pronoun = description[j];
                    resultDescription = description[j+1];
                    break;
                }
            }
            if(temp3.equalsIgnoreCase(currentDateOrAlphabet)){
                mAdapter1.addItem(" ", items.get(i*3 + 1), resultDescription, pronoun/*발음*/, WORDBOOK_WORD_TYPE);
            } else {
                mAdapter1.addItem(temp3, items.get(i*3 + 1), resultDescription/*첫번째 설명*/, pronoun/*발음*/, WORDBOOK_WORD_TYPE);
            }
            currentDateOrAlphabet = temp3;
        }
        mAdapter1.notifyDataSetChanged();
        mListView.setAdapter(mAdapter1);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            boolean lastItemVisibleFlag = false;        //화면에 리스트의 마지막 아이템이 보여지는지 체크
            private int mLastFirstVisibleItem;
            boolean mIsScrollingUp;
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                ListView lw = mListView;

                if (view.getId() == lw.getId()) {
                    final int currentFirstVisibleItem = lw.getFirstVisiblePosition();
                    if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        mIsScrollingUp = true;
                        Log.i("a", "scrolling up...");
                        if(checkBox.isChecked()){
                            TranslateAnimation animate = new TranslateAnimation(0, 0, 0, 0);
                            animate.setDuration(500);
                            animate.setFillAfter(true);
                            showUpLayout.startAnimation(animate);
                            showUpLayout.setVisibility(View.VISIBLE);
                        }
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    //TODO 화면이 바닦에 닿을때 처리
                    if(checkBox.isChecked()){
                        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, showUpLayout.getHeight());
                        animate.setDuration(500);
                        animate.setFillAfter(true);
                        showUpLayout.startAnimation(animate);
                        showUpLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                wordBook_WordData mData = (wordBook_WordData)mAdapter1.tempList.get(position);
                WordBookDialog2 fd = new WordBookDialog2();
                Bundle args = new Bundle();
                args.putString("key2", mData.word);
                args.putStringArrayList("key", wordList);
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
abstract class DialogDismissListener implements DialogInterface.OnDismissListener {

    private HashMap<String, Boolean> mBoolMap;

    public void setValue(String key, boolean value) {
        if(mBoolMap == null)
            mBoolMap = new HashMap<String, Boolean>();

        mBoolMap.put(key, value);
    }

    public boolean getValueForBool(String key, boolean defaultvalue) {
        if(mBoolMap == null)
            return false;
        else {
            if(mBoolMap.get(key) == null)
                return defaultvalue;
            else
                return mBoolMap.get(key);
        }
    }
}