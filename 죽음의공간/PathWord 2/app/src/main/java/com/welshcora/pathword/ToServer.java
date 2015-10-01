package com.welshcora.pathword;

import android.app.Activity;
import android.util.Log;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by HWANG on 15. 9. 12..
 */
public class ToServer extends Activity {
    private final String urlPath = "http://192.168.0.6:8888/PathWord/";

    JSONObject sendToServer(String url,ArrayList<String> key,ArrayList<String> value){
        HttpClient http = new DefaultHttpClient();
        String returnValue = null;
        JSONObject js = null;

        try {
            ArrayList<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>();

            for(int i=0;i<key.size();i++) {
                nameValuePairs.add(new BasicNameValuePair(key.get(i), value.get(i)));
            }

            HttpParams params = http.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            String u = urlPath.concat(url);
            HttpPost httpPost = new HttpPost(u);
            UrlEncodedFormEntity entityRequest =
                    new UrlEncodedFormEntity(nameValuePairs, "EUC-KR");

            httpPost.setEntity(entityRequest);

            HttpResponse responsePost = http.execute(httpPost);
            HttpEntity responseResultEntity = responsePost.getEntity();

            InputStream is = null;

            if(responseResultEntity != null) {
                is = responseResultEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);

                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                js = new JSONObject(sb.toString());
            }
        }catch(Exception e){e.printStackTrace();}

        return js;
    }
}
