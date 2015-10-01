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


public class RingtonDialog extends BaseDialog {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        createDialog(R.layout.dialog, "알림음");
        mListView = (ListView) view.findViewById(R.id.listViewFont);
        mAdapter1 = new ListViewAdapter((Activity)this.getActivity());
        mAdapter1.addItem("알림음1", NORMAL_TYPE);
        mAdapter1.addItem("알림음2", NORMAL_TYPE);
        mAdapter1.addItem("알림음3", NORMAL_TYPE);
        mListView.setAdapter(mAdapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                Toast.makeText((Activity)RingtonDialog.this.getActivity(), mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}