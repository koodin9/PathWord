package com.welshcora.pathword;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Intro2Activity extends ActionBarActivity {
    EditText mail,password;
    private final String urlPath = "http://192.168.1.103:8888/PathWord/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro2);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets());
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        mail = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onLoginButton(View v){
        HttpClient http = new DefaultHttpClient();
        try {

            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();
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
            HttpEntity responseResultEntity=responsePost.getEntity();

            InputStream is = null;
            JSONObject JS;

            if(responseResultEntity != null) {
                is = responseResultEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                String result = sb.toString();
                JSONArray temp = new JSONArray(result);
                JS = temp.getJSONObject(0);
                String aa = JS.getString("flag");

                if (aa.equals("Y")) {
                    Toast.makeText(getApplicationContext(), "로그인성공", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Intro2Activity.this, MainActivity.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
                    startActivity(i);
                    finish();

                } else
                    Toast.makeText(getApplicationContext(), "로그인실패", Toast.LENGTH_LONG).show();

            }

        }catch(Exception e){e.printStackTrace();}
    }

    public void onMembershipButton(View v) {
        Intent intent = new Intent(getApplicationContext(), Membership.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
