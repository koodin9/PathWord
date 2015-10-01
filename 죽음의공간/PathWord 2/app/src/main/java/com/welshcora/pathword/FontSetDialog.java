package com.welshcora.pathword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class FontSetDialog extends BaseDialog {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        createDialog(R.layout.dialog, "서체종류");

        mListView = (ListView) view.findViewById(R.id.listViewFont);
        mAdapter1 = new ListViewAdapter((Activity)this.getActivity());
        mAdapter1.addItem("default", "기본서체", NORMALTYPEFACE_TYPE);
        mAdapter1.addItem("HoonWhiteCat.ttf.mp3", "훈하얀고양이", NORMALTYPEFACE_TYPE);
        mAdapter1.addItem("NanumBarunGothic.ttf.mp3", "나눔바른고딕", NORMALTYPEFACE_TYPE);
        mListView.setAdapter(mAdapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                Toast.makeText((Activity)FontSetDialog.this.getActivity(), mData.mTitle + "로 폰트가 변경 되었습니다.", Toast.LENGTH_SHORT).show();
                SharedPreferences setting = getActivity().getSharedPreferences("setting", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = setting.edit();
                if(mData.mTitle.equals("기본서체")){
                    fontChanger.myFont = "default";

                    editor.putString("Font", "default");
                    editor.commit();

                    getActivity().recreate();
                    SettingFragment settingFragment = SettingFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                    dismiss();
                } else if (mData.mTitle.equals("훈하얀고양이")){
                    fontChanger.myFont = "HoonWhiteCat.ttf.mp3";

                    editor.putString("Font", "HoonWhiteCat.ttf.mp3");
                    editor.commit();

                    getActivity().recreate();
                    SettingFragment settingFragment = SettingFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                    dismiss();
                } else if (mData.mTitle.equals("나눔바른고딕")){
                    fontChanger.myFont = "NanumBarunGothic.ttf.mp3";

                    editor.putString("Font", "NanumBarunGothic.ttf.mp3");
                    editor.commit();

                    getActivity().recreate();
                    SettingFragment settingFragment = SettingFragment.newInstance();
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                    dismiss();
                }
            }
        });

        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}