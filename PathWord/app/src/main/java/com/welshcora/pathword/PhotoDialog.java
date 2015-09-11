package com.welshcora.pathword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PhotoDialog extends BaseDialog {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        createDialog(R.layout.dialog2, "사진선택");
        mListView = (ListView) view.findViewById(R.id.listViewFont);
        mAdapter1 = new ListViewAdapter((Activity)this.getActivity());
        mAdapter1.addItem("직접 찍기", NORMAL_TYPE);
        mAdapter1.addItem("앨범에서 선택", NORMAL_TYPE);
        mAdapter1.addItem("페이스북 프로필 사진으로", NORMAL_TYPE);
        mListView.setAdapter(mAdapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                Toast.makeText((Activity)PhotoDialog.this.getActivity(), mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}