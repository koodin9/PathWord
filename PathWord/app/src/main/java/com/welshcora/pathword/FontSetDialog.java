package com.welshcora.pathword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
        mListView.setAdapter(mAdapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                Toast.makeText((Activity)FontSetDialog.this.getActivity(), mData.mTitle, Toast.LENGTH_SHORT).show();
                if(mData.mTitle.equals("기본서체")){
                    fontChanger.myFont = "default";
                    SettingFragment settingFragment = SettingFragment.newInstance();
                    getFragmentManager().beginTransaction().detach(settingFragment);
                    getFragmentManager().beginTransaction().attach(settingFragment);
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                } else if (mData.mTitle.equals("훈하얀고양이")){
                    fontChanger.myFont = "HoonWhiteCat.ttf.mp3";
                    SettingFragment settingFragment = SettingFragment.newInstance();
                    getFragmentManager().beginTransaction().detach(settingFragment);
                    getFragmentManager().beginTransaction().attach(settingFragment);
                    getFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                }
            }
        });

        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}