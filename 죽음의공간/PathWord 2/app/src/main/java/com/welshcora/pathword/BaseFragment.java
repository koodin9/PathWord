package com.welshcora.pathword;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by DuckG on 2015-09-08.
 */
public class BaseFragment extends Fragment {
    //리스트뷰
    public static final int LABLE_TYPE = 0;
    public static final int NORMAL_TYPE = 1;
    public static final int SWITCH_TYPE = 2;
    public static final int RANKLIST_TYPE = 3;
    public static final int POINT_TYPE = 4;
    public static final int COMBINE_TYPE = 5;
    public static final int FRIEND_TYPE = 6;
    public static final int ALARMMAIN_TYPE = 7;
    public static final int ALARMADDBUTTON_TYPE = 8;
    public static final int ALARMDELBUTTON_TYPE = 9;
    public static final int ALARMDATE_TYPE = 10;
    public static final int ALARMSOUND_TYPE = 11;
    public static final int ALARMTIMEPICKER_TYPE = 12;
    public static final int NORMALTYPEFACE_TYPE = 13;
    public static final int WORDBOOK_WORD_TYPE = 14;


    //리스트뷰를 위한 객체 정의
    protected ListView mListView = null;
    protected ListViewAdapter mAdapter1 = null;

    //폰트
    FontChangeCrawler fontChanger;

    //툴바
    Toolbar toolbar;

    public void setToolbar(String toolbarTitle){
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);//툴바에 대한 객체를 만든다.
        toolbar.setVisibility(View.VISIBLE);//툴바를 보이게 한다. 이는 맨처음에 메인 프래그먼트가 툴바를 안보이게 하기 때문이다
        TextView toolbar_title = (TextView) getActivity().findViewById(R.id.toolbar_title);//툴바 타이틀에 대한 객체를 만든다.
        toolbar_title.setText(toolbarTitle);
    }
    public void setFont(){
        fontChanger= new FontChangeCrawler(getActivity().getAssets());
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }
}
