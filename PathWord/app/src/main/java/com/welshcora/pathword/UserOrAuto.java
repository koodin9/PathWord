package com.welshcora.pathword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HWANG on 15. 9. 18..
 */
public class UserOrAuto extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userorauto);

    }

    public void usermode(View v){
        Intent intent = new Intent(UserOrAuto.this ,RemindUserActivity.class);
        startActivity(intent);
        finish();
    }

    public void automode(View v){
        Intent intent = new Intent(UserOrAuto.this ,RemindActivity.class);
        startActivity(intent);
        finish();
    }
}
