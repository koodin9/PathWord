package com.welshcora.pathword;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HWANG on 15. 8. 31..
 */
public class Membership extends Activity{
    EditText name,mail,password;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PASSWOLD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    private final String urlPath = "http://192.168.1.103:8888/PathWord/membership.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership);

        name = (EditText) findViewById(R.id.memberName);
        mail = (EditText) findViewById(R.id.memberMail);
        password = (EditText) findViewById(R.id.memberPassword);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onSenderToServer(View v){
        HttpClient http = new DefaultHttpClient();
        try {
            if(!validateEmail(mail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "이메일형식불일치", Toast.LENGTH_LONG).show();
            }else if(!validatePassword(password.getText().toString())){
                Toast.makeText(getApplicationContext(), "비밀번호형식불일치", Toast.LENGTH_LONG).show();
            }else {
                ArrayList<NameValuePair> nameValuePairs =
                        new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("mail", mail.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString()));

                HttpParams params = http.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);

                HttpPost httpPost = new HttpPost(urlPath);
                UrlEncodedFormEntity entityRequest =
                        new UrlEncodedFormEntity(nameValuePairs, "EUC-KR");

                httpPost.setEntity(entityRequest);

                HttpResponse responsePost = http.execute(httpPost);
                HttpEntity resEntity = responsePost.getEntity();

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

    }

}
