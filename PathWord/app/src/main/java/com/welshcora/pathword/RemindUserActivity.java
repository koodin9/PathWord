package com.welshcora.pathword;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HWANG on 15. 9. 18..
 */
public class RemindUserActivity extends Activity {
    ImageButton fixRoot;
    Button preBranch;
    EditText root,afterbranch;
    int count = 0;

    boolean flag=false;

    ToServer ts = new ToServer();
    JSONObject returnJson;

    DbAdapter mDbAdapter;


    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_remind);

        root = (EditText) findViewById(R.id.root);

        fixRoot = (ImageButton) findViewById(R.id.fixroot);
        preBranch = (Button) findViewById(R.id.prebranch);
        afterbranch = (EditText) findViewById(R.id.afterbranch);

        key.add("mail");
        value.add(PathWord.getGlobalString());



    }

    public void fixRoot(View v){
        mDbAdapter = new DbAdapter(this); //디비를 사용할것이다!
        mDbAdapter.open();//디비는 쓸대만 열어두고 아닐땐 닫아야 함. 커서의 버퍼가 차기 때문
        if(!flag) {
            if(mDbAdapter.SearchWord2(root.getText().toString()).isEmpty()){
                Toast.makeText(this, root.getText().toString()+"사전에 없는 단어입니다!", Toast.LENGTH_LONG).show();
                Log.v("루트", "트루");
            } else {
                key.add(String.valueOf(count));
                value.add(root.getText().toString());
                count++;
                Log.v("루트", "뽈스");

                root.setEnabled(false);
                preBranch.setText(root.getText());
                flag=true;
            }
        }else{
            if(mDbAdapter.SearchWord2(afterbranch.getText().toString()).isEmpty()){
                Toast.makeText(this, afterbranch.getText().toString()+"사전에 없는 단어입니다!", Toast.LENGTH_LONG).show();
                Log.v("브랜치", "트루");
            } else {
                key.add(String.valueOf(count));
                value.add(afterbranch.getText().toString());

                preBranch.setText(afterbranch.getText());
                afterbranch.setText("");
                count++;
                Log.v("브랜치", "뽈스");

                if (count == 5) endRemind();
            }
        }
        mDbAdapter.close();
    }

    void endRemind(){
        ts.sendToServer("saveWords.php",key,value);
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("수고하셨습니다!!").setCancelable(false).setNegativeButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}
