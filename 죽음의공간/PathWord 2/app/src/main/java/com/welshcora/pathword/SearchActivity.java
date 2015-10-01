package com.welshcora.pathword;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.welshcora.pathword.R.drawable.circle;

/**
 * Created by DuckG on 2015-09-02.
 */
public class SearchActivity extends BaseActivity {
    EditText search;
    DbAdapter mDbAdapter;
    private AnimatedExpandableListView listView;
    private ExpandableListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //폰트 설정
        setFont();
        //툴바 설정
        //setToolbar("단어 검색");
        //Drawer 붙이기
        //makeDrawer();
        //커스텀 속성
        search = (EditText) findViewById(R.id.search);

        mDbAdapter = new DbAdapter(this);
        mDbAdapter.open();

        TextWatcher watcher = new TextWatcher()
        {
            List<GroupItem> items = new ArrayList<GroupItem>();//그룹아이템에 대한 리스트 자료형을 선언;

            int queryCount = 0;

            public List<GroupItem> makeGroupItems(CharSequence s){
                s = search.getText().toString();//EditText에서 텍스트를 뽑아낸다
                Log.v("서치값", s.toString());

                ArrayList<String> resultQuery = mDbAdapter.SearchWord(s.toString(), queryCount);
                int k = 0;
                while(k < (resultQuery.size() / 2)  ) {
                    GroupItem item = new GroupItem();
                    item.word = resultQuery.get(2 * k);//짝수는 이름 홀수는 description
                    Log.v("item.word", item.word);
                    ChildItem child = new ChildItem();
                    child.word = item.word; //단어장 추가를위해 인자를 전달.
                    String token[] = resultQuery.get(2 * k + 1).split(", ");
                    String temp = "";
                    int j = 1; //설명부분에 줄번호를 위해
                    for (int i = 0; i < token.length; i++) {
                        if(0 <= i && i < 4) {//4번까지만 form 검사
                            char first = token[i].charAt(0); char last = token[i].charAt(token[i].length()-1);
                            if (('A' <= first && first <= 'Z' || 'a' <= first && first <= 'z') &&
                                    ('A' <= last && last <= 'Z' || 'a' <= last && last <= 'z') && token[i].length() <= 4) {//토큰의 첫번째 글자와 마지막 글자가 영어거나 글자수가 4이하라면
                                item.form.add(token[i]);
                                ++item.formCount;
                            } else {
                                if (i == token.length - 1) { //마지막이면 토큰이면
                                    temp += j + ". " + token[i];
                                } else {
                                    temp += j + ". " + token[i] + "\n";
                                }
                                j++;
                            }
                        } else {
                            if (i == token.length - 1) { //마지막이면 토큰이면
                                temp += j + ". " + token[i];
                            } else {
                                temp += j + ". " + token[i] + "\n";
                            }
                            j++;
                        }
                    }
                    child.description = temp;
                    Log.v("item description", temp);
                    item.items.add(child);
                    items.add(item);
                    queryCount++;
                    k++;
                }
                return items;
            }
            @Override
            public void afterTextChanged(final Editable s) {
                items = new ArrayList<GroupItem>(); //기존에 잇던 리스트뷰의 아이템들을 초기화 해준다.
                queryCount = 0; //리스트뷰에 나와있는 쿼리카운트도 초기화

                adapter = new ExpandableListViewAdapter(getBaseContext(), mDbAdapter);
                adapter.setData(makeGroupItems(s));
                listView = (AnimatedExpandableListView) findViewById(R.id.exlistView);
                listView.setAdapter(adapter);

                //mDbAdapter.close();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문

                // In order to show animations, we need to use a custom click handler
                // for our ExpandableListView.
                listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (listView.isGroupExpanded(groupPosition)) {
                            listView.collapseGroupWithAnimation(groupPosition);
                        } else {
                            listView.expandGroupWithAnimation(groupPosition);
                        }
                        return true;
                    }
                });
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    boolean lastItemVisibleFlag = false;        //화면에 리스트의 마지막 아이템이 보여지는지 체크
                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
                    }

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                        //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                            //TODO 화면이 바닦에 닿을때 처리
                            adapter.setData(makeGroupItems(s));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }
        };
        search.addTextChangedListener(watcher);
    }
}
