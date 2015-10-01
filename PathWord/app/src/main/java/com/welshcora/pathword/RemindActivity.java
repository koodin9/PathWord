package com.welshcora.pathword;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RemindActivity extends Activity implements OnTouchListener{
    Boolean send=false,first=false;
    ImageButton FAB;
    DbAdapter mDbAdapter;
    Button add,word1,word2,word3,word4,word5,next;
    Button b;
    int count = 0;
    String[] reminded = new String[10];
    String rootName;

    private int mTouchSlop;

    private boolean mHasPerformedLongPress;
    private CheckForLongPress mPendingCheckForLongPress;

    private Handler mHandler = null;

    JSONObject returnJson;
    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    private float offset_x, offset_y, first_x, first_y;
    private boolean start_yn = true;
    int[] location = new int[2];

    RootDialog rootDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);

        mHandler = new Handler();

        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        FAB = (ImageButton) findViewById(R.id.imageButton);
        add = (Button)findViewById(R.id.remind_add);
        word1 = (Button) findViewById(R.id.word1);
        word2 = (Button) findViewById(R.id.word2);
        word3 = (Button) findViewById(R.id.word3);
        word4 = (Button) findViewById(R.id.word4);
        word5 = (Button) findViewById(R.id.word5);
        next = (Button) findViewById(R.id.root);

        word1.setOnTouchListener(this);
        word2.setOnTouchListener(this);
        word3.setOnTouchListener(this);
        word4.setOnTouchListener(this);
        word5.setOnTouchListener(this);
        add.setOnTouchListener(this);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        rootDialog = new RootDialog(RemindActivity.this);
        rootDialog.setTitle("루트단어 선택");

        rootDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                rootName = rootDialog.getRootName().toString();

                next.setText(rootName);

                if (!mHasPerformedLongPress) {
                    removeLongPressCallback();

                    key.add("selected");
                    value.add(rootName);
                    reminded[count] = rootName;

                    ts = new ToServer();
                    returnJson = ts.sendToServer("fetchWord.php", key, value);

                    try {
                        String high1 = returnJson.getString("high1");
                        String high2 = returnJson.getString("high2");
                        String low1 = returnJson.getString("low1");
                        String low2 = returnJson.getString("low2");
                        String low3 = returnJson.getString("low3");

                        int index;
                        if (!high1.equals(" ")) {
                            high1 = high1.trim();
                            index = high1.indexOf("/");
                            high1 = high1.substring(0, index);
                        }

                        if (!high2.equals(" ")) {
                            high2 = high2.trim();
                            index = high2.indexOf("/");
                            high2 = high2.substring(0, index);
                        }
                        if (!low1.equals(" ")) {
                            low1 = low1.trim();
                            index = low1.indexOf("/");
                            low1 = low1.substring(0, index);
                        }
                        if (!low2.equals(" ")) {
                            low2 = low2.trim();
                            index = low2.indexOf("/");
                            low2 = low2.substring(0, index);
                        }
                        if (!low3.equals(" ")) {
                            low3 = low3.trim();
                            index = low3.indexOf("/");
                            low3 = low3.substring(0, index);
                        }


                        next.setText(value.get(0));
                        word1.setText(high1);
                        word2.setText(high2);
                        word3.setText(low1);
                        word4.setText(low2);
                        word5.setText(low3);

                        count++;

                        for (int i = 0; i < key.size(); i++) {
                            key.remove(i);
                            value.remove(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        rootDialog.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            mHasPerformedLongPress = false;

            postCheckForLongClick(0);

            first_x = v.getX();
            first_y = v.getY();

            if(start_yn){
                offset_x = event.getX();
                offset_y = event.getY();
                start_yn = false;
            }
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            if (!mHasPerformedLongPress) {
                removeLongPressCallback();

                b = (Button) v;
                key.add("selected");
                value.add(b.getText().toString());
                reminded[count] = b.getText().toString();

                ts = new ToServer();
                returnJson = ts.sendToServer("fetchWord.php", key, value);

                try {
                    String high1 = returnJson.getString("high1");
                    String high2 = returnJson.getString("high2");
                    String low1 = returnJson.getString("low1");
                    String low2 = returnJson.getString("low2");
                    String low3 = returnJson.getString("low3");

                    int index;
                    if(!high1.equals(" ")) {
                        high1 = high1.trim();
                        index = high1.indexOf("/");
                        high1 = high1.substring(0, index);
                    }

                    if(!high2.equals(" ")) {
                        high2 = high2.trim();
                        index = high2.indexOf("/");
                        high2 = high2.substring(0, index);
                    }
                    if(!low1.equals(" ")) {
                        low1 = low1.trim();
                        index = low1.indexOf("/");
                        low1 = low1.substring(0, index);
                    }
                    if(!low2.equals(" ")) {
                        low2 = low2.trim();
                        index = low2.indexOf("/");
                        low2 = low2.substring(0, index);
                    }
                    if(!low3.equals(" ")) {
                        low3 = low3.trim();
                        index = low3.indexOf("/");
                        low3 = low3.substring(0, index);
                    }


                    next.setText(value.get(0));
                    word1.setText(high1);
                    word2.setText(high2);
                    word3.setText(low1);
                    word4.setText(low2);
                    word5.setText(low3);

                    for (int i = 0; i < key.size(); i++) {
                        key.remove(i);
                        value.remove(i);
                    }

                    count++;

                    if(count==10){
                        key.add("mail");
                        value.add(PathWord.getGlobalString());
                        for(int i=0;i<10;i++){
                            key.add(String.valueOf(i));
                            value.add(reminded[i]);
                        }
                        ts.sendToServer("saveWords.php",key,value);

                        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
                        alert_confirm.setMessage("수고하셨습니다!!").setCancelable(false).setNegativeButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
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

            if(send){
                v.setX(first_x);
                v.setY(first_y);
                start_yn = true;
                Drawable plus = getResources().getDrawable(R.drawable.circle_plus);
                add.setBackground(plus);
                send = false;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
                mDbAdapter = new DbAdapter(this);
                mDbAdapter.open();
                Date today = new Date();
                mDbAdapter.addWordAtBook(b.getText().toString(), mDbAdapter.SearchWord2(b.getText().toString()).get(0), formatter.format(today));
                Toast.makeText(getApplicationContext(),b.getText().toString() + "가 단어장에 추가되었습니다",Toast.LENGTH_LONG).show();
            }else{
                v.setX(first_x);
                v.setY(first_y);
            }

        } else if(event.getAction() == MotionEvent.ACTION_MOVE) {

            if(mHasPerformedLongPress) {
                v.setX((int) event.getRawX() - offset_x);
                v.setY((int) event.getRawY() - offset_y);

                if (event.getRawX() > 300 && event.getRawX() < 1000 && event.getRawY() > 200 && event.getRawY() < 1200) {
                    send = true;
                    Drawable checking = getResources().getDrawable(R.drawable.check2);
                    add.setBackground(checking);
                } else {
                    send = false;
                    Drawable plus = getResources().getDrawable(R.drawable.circle_plus);
                    add.setBackground(plus);
                }
            }

        }
        return false;
    }

    class CheckForLongPress implements Runnable {

        public void run() {
            if (performLongClick()) {
                mHasPerformedLongPress = true;
            }
        }
    }

    private void postCheckForLongClick(int delayOffset) {
        mHasPerformedLongPress = false;

        if (mPendingCheckForLongPress == null) {
            mPendingCheckForLongPress = new CheckForLongPress();
        }

        mHandler.postDelayed(mPendingCheckForLongPress,
                ViewConfiguration.getLongPressTimeout() - delayOffset);
    }

    private void removeLongPressCallback() {
        if (mPendingCheckForLongPress != null) {
            mHandler.removeCallbacks(mPendingCheckForLongPress);
        }
    }

    public boolean performLongClick() {
        send = true;
        return true;
    }
}