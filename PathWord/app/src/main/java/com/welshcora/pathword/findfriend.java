package com.welshcora.pathword;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HWANG on 15. 9. 10..
 */
public class FindFriend extends ActionBarActivity {
    EditText friendMail;
    JSONObject returnJson;
    String friendMail2,myMail;

    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findfriend);
        ts = new ToServer();
    }

    public void findFriend(View v){
        friendMail = (EditText) findViewById(R.id.friendMail);

        key.add("friendMail");
        value.add(friendMail.getText().toString());

        returnJson = ts.sendToServer("findFriend.php", key, value);

        for(int i=0;i<key.size();i++){
            key.remove(i);
            value.remove(i);
        }

        try {
            String str = returnJson.getString("flag");
            if (str.equals("N")) {
                Toast.makeText(getApplicationContext(), "존재하지 않은 메일주소입니다.", Toast.LENGTH_LONG).show();
            }else {
                friendMail2 = returnJson.getString("mail");
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
                alert_confirm.setTitle("친구맺기").setMessage(str + "님과 친구를 맺으시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                key.add("myMail");
                                value.add(MyApplication.getGlobalString());
                                key.add("friendMail");
                                value.add(friendMail2);
                                returnJson = ts.sendToServer("makefriend.php", key, value);
                                Toast.makeText(getApplicationContext(), "친구맺기성공", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}


