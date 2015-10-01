package com.welshcora.pathword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class WordBookTestConfirmDialog extends BaseDialog {
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
        mBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mLaytoutInflater = getActivity().getLayoutInflater();
        view = mLaytoutInflater.inflate(R.layout.dialog_test_confirmresult, null);
        mBuilder.setView(view);
        Button confirm = (Button) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultListener != null)
                mResultListener.setValue(KEY_RESULT, true);
                dismiss();
            }
        });
        Button close = (Button) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mResultListener != null)
                    mResultListener.setValue(KEY_RESULT, false);
                dismiss();
            }
        });
        TextView title = (TextView) view.findViewById(R.id.descriptionlayout);
        title.setText(getArguments().getString("title"));
        return mBuilder.create();
    }
    public void onStop(){
        super.onStop();
    }
}