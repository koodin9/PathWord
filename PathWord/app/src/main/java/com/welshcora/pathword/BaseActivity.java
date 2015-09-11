package com.welshcora.pathword;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class BaseActivity extends ActionBarActivity {
    //리스트뷰
    public static final int LABLE_TYPE = 0;
    public static final int NORMAL_TYPE = 1;
    public static final int SWITCH_TYPE = 2;
    public static final int RANKLIST_TYPE = 3;
    public static final int POINT_TYPE = 4;
    public static final int COMBINE_TYPE = 5;
    public static final int FRIEND_TYPE = 6;
    public static final int ALARMMAIN_TYPE = 7;
    public static final int ALARMADDBUTTON_TYPE = 8;
    public static final int ALARMDELBUTTON_TYPE = 9;
    public static final int ALARMDATE_TYPE = 10;
    public static final int ALARMSOUND_TYPE = 11;
    public static final int ALARMTIMEPICKER_TYPE = 12;
    public static final int NORMALTYPEFACE_TYPE = 13;

    //드로어로 조정할 프래그먼트
    SettingFragment settingFragment;
    MyPageFragment myPageFragment;
    WordBookFragment wordBookFragment;
    AlarmFragment alarmFragment;
    MainFragment mainFragment;

    //네비게이션 드로어
    String TITLES[] = {"단어연상","단어장","알람","게임","검색","환경설정"};
    int ICONS[] = {R.drawable.drawer_ic_remind,R.drawable.drawer_ic_voca,R.drawable.drawer_ic_alarm,R.drawable.drawer_ic_games,R.drawable.drawer_ic_search,R.drawable.drawer_ic_setting};
    String LEVEL = "Lv. 99";
    String NAME = "포도지현";
    int PROFILE = R.drawable.aka; //프로필 사진
    RecyclerView mRecyclerView;                           // RecyclerView를 정의
    RecyclerView.Adapter mAdapter;                        // Recycler View의 어뎁터를 정의
    RecyclerView.LayoutManager mLayoutManager;            // 레이아웃 메니저를 리니어 레이아웃으로 정의
    DrawerLayout Drawer;                                  // DrawerLayout를 정의
    ActionBarDrawerToggle mDrawerToggle;                  // Action Bar Drawer Toggle를 정의

    //툴바
    protected Toolbar toolbar;                              // Toolbar 오브젝트를 정의
    protected FrameLayout content;

    //폰트
    FontChangeCrawler fontChanger;

    //리스트뷰를 위한 객체정의
    public ListView mListView = null;
    public ListViewAdapter mAdapter1 = null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //프래그 먼트 객체를 생성함
        makeInstance();
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
    public void makeInstance(){
        //프래그먼트
        fontChanger = new FontChangeCrawler(getAssets());
        settingFragment = SettingFragment.newInstance();
        myPageFragment = MyPageFragment.newInstance();
        wordBookFragment = WordBookFragment.newInstance();
        alarmFragment = AlarmFragment.newInstance();
        mainFragment = MainFragment.newInstance();
    }
    public void setFont(){
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }
    public void setToolbar(String toolbartitle){
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        Typeface temp = Typeface.create(fontChanger.typeface, Typeface.BOLD);//tool_bar에 들어가는 타이틀은 Bold체로 만들기 위하여
        toolbar_title.setText(toolbartitle);
        toolbar_title.setTypeface(temp);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(getApplicationContext(), "검색메뉴 선택!", Toast.LENGTH_SHORT).show();//toolbar 에서 호출
                        Intent intent = new Intent(BaseActivity.this, SearchActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }
    public void makeDrawer(){
        //네비게이션 드로어
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // RecyclerView Object에 xml View를 넣어 정의한다
        mRecyclerView.setHasFixedSize(true);                            // 시스템에게 오브젝트들이 fixed size임을 알게한다.
        mAdapter = new DrawerAdapter(TITLES,ICONS,LEVEL,NAME,PROFILE, this);       // MyAdapter class의 인스턴스를 생성한다.
        mRecyclerView.setAdapter(mAdapter);                              // RecyclerViewk에 MyAdapter를 넣는다.
        //드로어 선택 각 액티비티마다 추가해줘야 하는듯
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Drawer.closeDrawers();
                    for(int i = 0; i < recyclerView.getChildCount(); i++){
                        View temp = recyclerView.getChildAt(i);
                        temp.setSelected(false);
                    }
                    child.setSelected(true);
                    Toast.makeText(BaseActivity.this,"The Item Clicked is: "+recyclerView.getChildPosition(child),Toast.LENGTH_SHORT).show();
                    switch (recyclerView.getChildPosition(child)) {
                        case 0:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, myPageFragment).addToBackStack(null).commit();
                            break;
                        case 1:
                            Intent intent = new Intent(BaseActivity.this ,RemindActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, wordBookFragment).addToBackStack(null).commit();
                            break;
                        case 3:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, alarmFragment).addToBackStack(null).commit();
                            break;
                        case 4:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                            break;
                        case 5:
                            intent = new Intent(BaseActivity.this ,SearchActivity.class);
                            startActivity(intent);
                            break;
                        case 6:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, settingFragment).addToBackStack(null).commit();
                            break;
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }
        });

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }
}