package com.welshcora.pathword;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyPageFragment extends BaseFragment {

    JSONObject returnJson;
    ToServer ts;

    ArrayList<String> key = new ArrayList<String>();
    ArrayList<String> value = new ArrayList<String>();

    String mail;
    int point,ranking,friendsNum;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ts = new ToServer();
        key.add("mail");
        value.add(PathWord.getGlobalString());

        returnJson = ts.sendToServer("myinform.php", key, value);

        for(int i=0;i<key.size();i++){
            key.remove(i);
            value.remove(i);
        }

        try {
            point = returnJson.getInt("point");
            ranking = returnJson.getInt("ranking");
            friendsNum = returnJson.getInt("friendsNum");
        }catch(Exception e){e.printStackTrace();}
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View connectionView = inflater.inflate(R.layout.fragment_mypage,container,false);
        return connectionView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //툴바 설정
        setToolbar("마이페이지");
        //폰트 설정
        setFont();
        //커스텀 속성
        CircleImageView ProfilePic = (CircleImageView) getActivity().findViewById(R.id.profilePic); //프로필사진 셋팅 드로어 말고
        Drawable pic = getResources().getDrawable(R.drawable.aka);
        ProfilePic.setImageDrawable(pic);
        ProfilePic.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(v.getId() ==R.id.profilePic){
                    PhotoDialog fd = new PhotoDialog();
                    fd.show(getFragmentManager(), "MYTGAGGG");
                }
            }
        });

        mListView = (ListView) getActivity().findViewById(R.id.listView_myPage);
        mAdapter1 = new ListViewAdapter(getActivity());
        mAdapter1.addItem("포인트", "포인트요만큼P", POINT_TYPE);
        mAdapter1.addItem("58", "102","1023","89", COMBINE_TYPE);
        mAdapter1.addItem("",LABLE_TYPE);
        mAdapter1.addItem("랭킹", NORMAL_TYPE);
        mAdapter1.addItem("친구 목록", NORMAL_TYPE);
        mListView.setAdapter(mAdapter1);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //나중에 버튼 순서대로 정렬해놔
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = mAdapter1.tempList.get(position);
                if(mData.mTitle.equals("랭킹")){
                    Intent intent = new Intent(getActivity(), RankActivity.class);
                    getActivity().startActivity(intent);
                } else if (mData.mTitle.equals("친구 목록")){
                    Intent intent = new Intent(getActivity(), FriendActivity.class);
                    getActivity().startActivity(intent);
                } else if (mData.mTitle.equals("포인트")){
                    Intent intent = new Intent(getActivity(), PointActivity.class);
                    getActivity().startActivity(intent);
                } else if (mData.mTitle.equals("친구 찾기")){
                    Intent intent = new Intent(getActivity(), FindFriend.class);
                    getActivity().startActivity(intent);
                }
            }
        });
    }
    public static MyPageFragment newInstance() {
        MyPageFragment fragment = new MyPageFragment();
        return fragment;
    }
}