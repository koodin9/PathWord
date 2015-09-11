package com.welshcora.pathword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

/**
 * Created by choijinjoo on 2015. 8. 18..
 */

//앱을 실행했을때 1초간 띄우는 Intro화면

public class Intro1Activity extends Activity {
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets());
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        h = new Handler();
        h.postDelayed(mrun, 2000); // 딜레이 ( 런어블 객체는 mrun, 시간 1초)
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
}

