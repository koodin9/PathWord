package com.welshcora.pathword;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HWANG on 15. 8. 31..
 */
public class Membership extends Activity{
    EditText name,mail,password;
    JSONObject returnJson;
    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);

        name = (EditText) findViewById(R.id.memberName);
        mail = (EditText) findViewById(R.id.memberMail);
        password = (EditText) findViewById(R.id.memberPassword);
        ts = new ToServer();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onSenderToServer(View v){
        HttpClient http = new DefaultHttpClient();
        try {
            if(!validateEmail(mail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "이메일 형식 불일치", Toast.LENGTH_LONG).show();
            }else if(!validatePassword(password.getText().toString())){
                Toast.makeText(getApplicationContext(), "비밀번호 형식 불일치", Toast.LENGTH_LONG).show();
            }else {
                key.add("name");
                value.add(name.getText().toString());
                key.add("mail");
                value.add(mail.getText().toString());
                key.add("password");
                value.add(password.getText().toString());

                returnJson = ts.sendToServer("membership.php", key, value);

                for(int i=0;i<key.size();i++){
                    key.remove(i);
                    value.remove(i);
                }

                String aa = returnJson.getString("flag");

                if (aa.equals("N"))
                    Toast.makeText(getApplicationContext(), "메일주소중복", Toast.LENGTH_LONG).show();
                else if(aa.equals("Y"))
                    Toast.makeText(getApplicationContext(), "가입완료", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }

    public void onCancelButton(View v){
        this.onBackPressed();
    }

}
