package com.welshcora.pathword;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


public class Intro2Activity extends ActionBarActivity {
    EditText mail,password;
    JSONObject returnJson;
    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro2);

        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);
        ts = new ToServer();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onLoginButton(View v){
        key.add("mail");
        value.add(mail.getText().toString());
        key.add("password");
        value.add(password.getText().toString());

        returnJson = ts.sendToServer("login.php", key, value);

        for(int i=0;i<key.size();i++){
            key.remove(i);
            value.remove(i);
        }

        try {
            String aa = returnJson.getString("flag");

            if (aa.equals("Y")) {
                MyApplication myApp = (MyApplication) getApplication();
                myApp.setGlobalString(mail.getText().toString());

                Toast.makeText(getApplicationContext(), "로그인성공", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intro2Activity.this, MainActivity.class);
                startActivity(i);

            } else
                Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onMembershipButton(View v) {
        Intent intent = new Intent(getApplicationContext(), Membership.class);
        startActivity(intent);
    }
}
