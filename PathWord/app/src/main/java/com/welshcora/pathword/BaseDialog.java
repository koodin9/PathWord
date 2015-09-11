package com.welshcora.pathword;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by DuckG on 2015-09-08.
 */
public class BaseDialog extends DialogFragment {
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

    //리스트뷰를 위한 객체 정의
    protected ListView mListView = null;
    protected ListViewAdapter mAdapter1 = null;

    //폰트
    FontChangeCrawler fontChanger;

    //뷰
    View view;

    //AlertDialog 객체 나중에 리턴해야되서
    AlertDialog.Builder mBuilder;

    public void createDialog(int layoutID, String dialogTitle){
        mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLaytoutInflater = getActivity().getLayoutInflater();
        view = mLaytoutInflater.inflate(layoutID, null);
        mBuilder.setView(view);
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(dialogTitle);
        fontChanger = new FontChangeCrawler(getActivity().getAssets());
        Typeface temp = Typeface.create(fontChanger.typeface, Typeface.BOLD);//타이틀에 들어가는 Type
        title.setTypeface(temp);
    }
}
