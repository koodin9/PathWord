package com.welshcora.pathword;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class WordBookDialog extends BaseDialog {
    public static final String KEY_RESULT = "Result";
    private DialogDismissListener mResultListener;

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        //받은 리스너를 등록한다
        getDialog().setOnDismissListener(mResultListener);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        //반드시 여기서도 종료를 해주어야 한다. DismissListener를 등록할 경우 back키를 누르면 종료되지 않고 cancel만 되기 때문이다
        dismiss();
    }

    //리스너를 받는다
    public void setDismissListener(DialogDismissListener listener) {
        mResultListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        createDialog(R.layout.dialog_wordbook_word, "알림");
        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultListener != null)
                mResultListener.setValue(KEY_RESULT, true);
                dismiss();
            }
        });
        Button cancle = (Button) view.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultListener != null)
                    mResultListener.setValue(KEY_RESULT, false);
                dismiss();
            }
        });
        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}