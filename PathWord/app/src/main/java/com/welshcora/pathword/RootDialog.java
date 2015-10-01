package com.welshcora.pathword;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by HWANG on 15. 9. 24..
 */
public class RootDialog extends Dialog implements View.OnTouchListener {
    EditText root;
    Button ok;
    String rootName;

    public RootDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_dialog);

        root = (EditText) findViewById(R.id.rootWord);
        ok = (Button) findViewById(R.id.ok);

        ok.setOnTouchListener(this);

    }

    public String getRootName(){
        return rootName;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==ok){
            rootName = root.getText().toString();
            dismiss();
        }
        return true;
    }
}
