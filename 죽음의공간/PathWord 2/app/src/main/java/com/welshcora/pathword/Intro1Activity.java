package com.welshcora.pathword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by choijinjoo on 2015. 8. 18..
 */

//앱을 실행했을때 1초간 띄우는 Intro화면

public class Intro1Activity extends Activity {
    Handler h;
    private DbAdapter mDbHelper;
    //static int value;
    ProgressBar progress;
    TextView progressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets());
        progress = (ProgressBar) findViewById(R.id.progress);
        progressText= (TextView) findViewById(R.id.progresstext);
        h = new Handler();
        h.postDelayed(mrun, 2000); // 딜레이 ( 런어블 객체는 mrun, 시간 1초)

        //폰트 초기셋팅 로컬로 저장된 정보를 가져온다.
        SharedPreferences setting = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        if(setting.getString("Font", "") == null || setting.getString("Font", "").equals("") ){
            editor.putString("Font", "default");
            editor.commit();
            Log.v("이프절", setting.getString("Font", ""));
        }
        fontChanger.myFont = setting.getString("Font", "");
        Log.v("지금은 이폰트야", fontChanger.myFont);
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));


        //초기에 단어 디비를 확인해서 없으면 만들어준다.

        String filePath = "/data/data/com.welshcora.pathword/databases/Test6.db";
        File file = new File(filePath);
        boolean isCreated = file.exists(); //파일이 있으면 true, 없으면 false
        if(!isCreated){
            mDbHelper = new DbAdapter(this);
            mDbHelper.open();
            try{
                loadProfile();
            }catch(Exception e){}
        }
        //Log.v("단어 추출 테스트 : ", mDbHelper.test());
    }

    private void loadProfile() throws IOException {
        // TODO Auto-generated method stub
        final Resources resources = getResources();

        InputStream inputStream = resources.openRawResource(R.raw.dictionary);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            //Progress task = new Progress();
            //task.execute(97656);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = TextUtils.split(line, " : ");
                if (strings.length < 2) continue;
                String key_name1 = strings[0].trim();
                String key_name2 = strings[1].trim();
                long id = mDbHelper.createData(key_name1, key_name2);
                if (id < 0) {
                    Log.e("MIN", "unable to add profile: " + strings[0].trim());
                }else{
                    Log.d("MIN", "Insert to add profile: " + strings[0].trim());
                }
                //value++;
            }
        }catch(IOException e){
            Log.d("db error",e.toString());
        }finally {
            reader.close();
        }
        Log.d("MIN", "DONE loading profiles.");
    }

    Runnable mrun = new Runnable(){
        @Override
        public void run(){
            Intent i = new Intent(Intro1Activity.this, Intro2Activity.class); //인텐트 생성(현 액티비티, 새로 실행할 액티비티)
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
        }
    };
    //인트로 중에 뒤로가기를 누를 경우 핸들러를 끊어버려 아무일 없게 만드는 부분
    //미 설정시 인트로 중 뒤로가기를 누르면 인트로 후에 홈화면이 나옴.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        h.removeCallbacks(mrun);
    }
    /*class Progress extends AsyncTask<Integer, Integer, Integer>{
        protected void onPreExecute(){
            value = 0;
            progress.setProgress(value);
        }
        @Override
        protected Integer doInBackground(Integer... params) {
            while(isCancelled()==false){
                if(value >= 97656){
                    break;
                } else {
                    publishProgress(value);
                }
            }
            return value;
        }
        protected void onProgressUpdate(Integer ... value){
            progress.setProgress(value[0].intValue());
            progressText.setText("현재 진행률 : " + value[0].toString());
        }
        protected void onPostExecute(Integer result){
            progress.setProgress(0);
        }
    }*/
}

