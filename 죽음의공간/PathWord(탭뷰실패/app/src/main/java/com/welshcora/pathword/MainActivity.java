package com.welshcora.pathword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

//빈 액티비티. 프래그먼트들의 온클릭 메소드만 정의되어 있다! 그리고 이 액티비티 하위에있는 프래그먼트들을 위하여 툴바를 생성한다.
public class MainActivity extends BaseActivity {
    boolean waitInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //폰트 설정
        setFont();
        //툴바 설정
        setToolbar("알람설정");
        //Drawer 붙이기
        makeDrawer();
        //커스텀 속성, 시작은 mainFragment 부터
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, mainFragment).addToBackStack(null).commit();
    }
    public void myNameClicked(View v){
        Intent intent = new Intent(this,SetNameActivity.class);
        startActivity(intent);
    }
    public void onRemindButonClicked(View v){
        Intent intent = new Intent(this,RemindActivity.class);
        startActivity(intent);
    }
    public void onVocaButonClicked(View v){
        makeInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, wordBookFragment).addToBackStack(null).commit();
    }
    public void onAlarmButonClicked(View v){
        makeInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, alarmFragment).addToBackStack(null).commit();
    }
    public void onSettingButonClicked(View v) {
        Toast.makeText(this, "메인 메뉴 셋팅 버튼이 눌림", Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mainFrame);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}