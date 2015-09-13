package com.welshcora.pathword;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DuckG on 2015-08-25.
 */
public class ListViewAdapter extends BaseAdapter {
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

    public int alarmCount = 0;

    public ArrayList<ListData> tempList = new ArrayList<ListData>();
    private Context mContext = null;

    //폰트
    FontChangeCrawler fontChanger;

    public ListViewAdapter(Context mContext)
    {
        super();
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return tempList.size();
    }

    @Override
    public Object getItem(int position) {
        return tempList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = tempList.get(position).getType();
        if (type == LABLE_TYPE) {
            LableORNormalHolder holder = new LableORNormalHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_lable, null);

            holder.mText = (TextView) convertView.findViewById(R.id.mText);

            convertView.setTag(holder);

            LableORNormalData mData = (LableORNormalData) tempList.get(position);
            holder.mText.setText(mData.mTitle);
        } else if (type == NORMAL_TYPE) {
            LableORNormalHolder holder = new LableORNormalHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_normal, null);

            holder.mText = (TextView) convertView.findViewById(R.id.mText);

            convertView.setTag(holder);

            LableORNormalData mData = (LableORNormalData) tempList.get(position);
            holder.mText.setText(mData.mTitle);
        } else if (type == SWITCH_TYPE) {
            SwitchHolder holder = new SwitchHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_switch, null);

            holder.mText = (TextView) convertView.findViewById(R.id.mText);

            convertView.setTag(holder);

            SwitchData mData = (SwitchData) tempList.get(position);
            holder.mText.setText(mData.mTitle);
        } else if(type == RANKLIST_TYPE) {
            RankListHolder holder;
            holder = new RankListHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_ranklist, null);

            holder.rank = (TextView) convertView.findViewById(R.id.rank);
            holder.personName = (TextView) convertView.findViewById(R.id.personName);
            holder.nickName = (TextView) convertView.findViewById(R.id.nickName);
            holder.ability = (TextView) convertView.findViewById(R.id.ability);
            holder.ProfilePic = (CircleImageView) convertView.findViewById(R.id.ProfilePic);

            convertView.setTag(holder);

            RankListData mData = (RankListData) tempList.get(position);
            holder.rank.setText(mData.rank);
            holder.personName.setText(mData.personName);
            holder.nickName.setText(mData.nickName);
            holder.ability.setText(mData.ability);
            holder.ProfilePic.setImageDrawable(mData.ProfilePic);
        } else if (type == POINT_TYPE) {
            PointHolder holder = new PointHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_point, null);

            holder.mText = (TextView) convertView.findViewById(R.id.mText);
            holder.point = (TextView) convertView.findViewById(R.id.point);

            convertView.setTag(holder);

            PointListData mData = (PointListData) tempList.get(position);
            holder.mText.setText(mData.mTitle);
            holder.point.setText(mData.point);
        } else if (type == COMBINE_TYPE){
            CombineHolder holder = new CombineHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_combine, null);

            holder.rank = (TextView) convertView.findViewById(R.id.rank);
            holder.friend = (TextView) convertView.findViewById(R.id.friend);
            holder.wordbook = (TextView) convertView.findViewById(R.id.wordbook);
            holder.myPath = (TextView) convertView.findViewById(R.id.myPath);

            convertView.setTag(holder);

            CombineListData mData = (CombineListData) tempList.get(position);
            holder.rank.setText(mData.rank);
            holder.friend.setText(mData.friend);
            holder.wordbook.setText(mData.wordbook);
            holder.myPath.setText(mData.myPath);
        } else if (type == FRIEND_TYPE){
            FriendHolder holder = new FriendHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_friendlist, null);

            holder.personName = (TextView) convertView.findViewById(R.id.personName);
            holder.nickName = (TextView) convertView.findViewById(R.id.nickName);
            holder.ProfilePic = (CircleImageView) convertView.findViewById(R.id.ProfilePic);

            convertView.setTag(holder);

            FriendListData mData = (FriendListData) tempList.get(position);
            holder.personName.setText(mData.personName);
            holder.nickName.setText(mData.nickName);
            holder.ProfilePic.setImageDrawable(mData.ProfilePic);

        } else if (type==ALARMMAIN_TYPE) {
            final AlarmMainHolder holder = new AlarmMainHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarm_main, null);

            holder.Hour = (TextView) convertView.findViewById(R.id.Hour);
            holder.Minute = (TextView) convertView.findViewById(R.id.Minute);
            holder.AMPM = (TextView) convertView.findViewById(R.id.AMPM);
            holder.Date = (TextView) convertView.findViewById(R.id.Date);
            holder.AlarmIc = (ImageView) convertView.findViewById(R.id.alarmic);

            convertView.setTag(holder);

            holder.AlarmIc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmMainListData mData = (AlarmMainListData) tempList.get(position);
                    if (position == Integer.parseInt(mData.mTitle)) {
                        if (holder.AlarmIc.isSelected()) {
                            holder.AlarmIc.setSelected(false);
                        } else {
                            holder.AlarmIc.setSelected(true);
                        }
                    }
                }
            });
            AlarmMainListData mData = (AlarmMainListData) tempList.get(position);
            holder.Hour.setText(mData.Hour);
            holder.Minute.setText(mData.Minute);
            holder.AMPM.setText(mData.AMPM);
            holder.Date.setText(mData.Date);
        } else if (type == ALARMADDBUTTON_TYPE){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarmadd, null);
        } else if (type == ALARMDELBUTTON_TYPE){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarmdelete, null);
        } else if (type == ALARMDATE_TYPE){
            final AlarmDateHolder holder = new AlarmDateHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarm_choicedate, null);
            holder.Mon = (TextView) convertView.findViewById(R.id.monday);
            holder.Tue = (TextView) convertView.findViewById(R.id.tuesday);
            holder.Wed = (TextView) convertView.findViewById(R.id.wednesday);
            holder.Thr = (TextView) convertView.findViewById(R.id.thursday);
            holder.Fri = (TextView) convertView.findViewById(R.id.friday);
            holder.Sat = (TextView) convertView.findViewById(R.id.saturday);
            holder.Sun = (TextView) convertView.findViewById(R.id.sunday);
            convertView.setTag(holder);
            holder.Mon.setOnClickListener(new myOnClick());
            holder.Tue.setOnClickListener(new myOnClick());
            holder.Wed.setOnClickListener(new myOnClick());
            holder.Thr.setOnClickListener(new myOnClick());
            holder.Fri.setOnClickListener(new myOnClick());
            holder.Sat.setOnClickListener(new myOnClick());
            holder.Sun.setOnClickListener(new myOnClick());

        } else if (type == ALARMSOUND_TYPE){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarm_sound, null);
        } else if (type == ALARMTIMEPICKER_TYPE){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_alarm_timepicker, null);

        } else if(type == NORMALTYPEFACE_TYPE) {
            NormalTypefaceHolder holder = new NormalTypefaceHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_normal, null);

            holder.mText = (TextView) convertView.findViewById(R.id.mText);

            convertView.setTag(holder);

            NormalTypefaceListData mData = (NormalTypefaceListData) tempList.get(position);
            if(mData.mTitle.equals("기본서체")){
                holder.mText.setText(mData.mTitle);
                holder.mText.setTypeface(Typeface.MONOSPACE);
            } else if(mData.mTitle.equals("훈하얀고양이")){
                holder.mText.setText(mData.mTitle);
                Typeface temp = Typeface.createFromAsset(convertView.getContext().getAssets(), "HoonWhiteCat.ttf.mp3");
                holder.mText.setTypeface(temp);
            } else if(mData.mTitle.equals("나눔바른고딕")){
                holder.mText.setText(mData.mTitle);
                Typeface temp = Typeface.createFromAsset(convertView.getContext().getAssets(), "NanumBarunGothic.ttf.mp3");
                holder.mText.setTypeface(temp);
            }
        }
        if(type != NORMALTYPEFACE_TYPE){
            fontChanger = new FontChangeCrawler(convertView.getContext().getAssets());
            fontChanger.replaceFonts((ViewGroup) convertView);
        }
        return convertView;
    }
    public void addItem(int type){
        ListData addInfo;
        addInfo = new ListData();
        addInfo.setType(type);
        if(type == ALARMADDBUTTON_TYPE){
            addInfo.mTitle = Integer.toString(alarmCount);
        }
        alarmCount++;
        tempList.add(addInfo);
    }
    public void addItem(String mTitle, int type){
        if (type == LABLE_TYPE || type == NORMAL_TYPE){
            LableORNormalData addInfo;
            addInfo = new LableORNormalData();
            addInfo.setType(type);
            addInfo.mTitle = mTitle;
            tempList.add(addInfo);
        } else if (type == SWITCH_TYPE) {
            SwitchData addInfo;
            addInfo = new SwitchData();
            addInfo.setType(type);
            addInfo.mTitle = mTitle;
            tempList.add(addInfo);
        }
    }

    public void addItem(String mTitle, String point, int type){
        if(type == POINT_TYPE){ //마이페이지에서 잔여 포인트량 리스트뷰
            PointListData addInfo;
            addInfo = new PointListData();
            addInfo.setType(type);
            addInfo.mTitle = mTitle;
            addInfo.point = point;
            tempList.add(addInfo);
        } else {
            NormalTypefaceListData addInfo;
            addInfo = new NormalTypefaceListData();
            addInfo.setType(type);
            addInfo.path = mTitle; //이게 타입페이스 경로야 여기 헷갈리지마
            addInfo.mTitle = point;//폰트 이름
            tempList.add(addInfo);
        }
    }
    public void addItem(Drawable profilepic, String personname, String nickname,int type){
        FriendListData addInfo;
        addInfo = new FriendListData();
        addInfo.setType(type);
        addInfo.personName = personname;
        addInfo.nickName = nickname;
        addInfo.ProfilePic = profilepic;
        tempList.add(addInfo);
    }

    public void addItem(String rank, String friend, String wordbook, String myPath, int type){//마이페이지의 짬뽕리스트랑 알람 메인
        if(type == ALARMMAIN_TYPE){
            AlarmMainListData addInfo;
            addInfo = new AlarmMainListData();
            addInfo.setType(type);
            addInfo.mTitle = Integer.toString(alarmCount);
            addInfo.Hour = rank;
            addInfo.Minute = friend;
            addInfo.AMPM = wordbook;
            addInfo.Date = myPath;
            alarmCount++;
            tempList.add(addInfo);
        } else{
            CombineListData addInfo;
            addInfo = new CombineListData();
            addInfo.setType(type);
            addInfo.rank = rank;
            addInfo.friend = friend;
            addInfo.wordbook = wordbook;
            addInfo.myPath = myPath;
            tempList.add(addInfo);
        }
    }
    public void addItem(String rank, Drawable profilepic, String personname, String nickname, String ability, int type){
        RankListData addInfo;
        addInfo = new RankListData();
        addInfo.setType(type);
        addInfo.rank = rank;
        addInfo.ProfilePic = profilepic;
        addInfo.personName = personname;
        addInfo.nickName = nickname;
        addInfo.ability = ability;
        tempList.add(addInfo);
    }

}
class ViewHolder {
    public TextView mText;
}
class LableORNormalHolder extends ViewHolder{
}
class NormalTypefaceHolder extends ViewHolder{
    Typeface typeface;
}
class SwitchHolder extends ViewHolder{
    public Switch aSwitch;
}
class RankListHolder extends ViewHolder{
    public TextView rank;
    public CircleImageView ProfilePic;
    public TextView personName;
    public TextView nickName;
    public TextView ability;
}
class PointHolder extends ViewHolder{
    public TextView point;
}

class CombineHolder extends ViewHolder {
    public TextView rank;
    public TextView friend;
    public TextView wordbook;
    public TextView myPath;
}
class FriendHolder extends ViewHolder {
    public CircleImageView ProfilePic;
    public TextView personName;
    public TextView nickName;
}

class AlarmMainHolder extends ViewHolder {
    public TextView Hour;
    public TextView Minute;
    public TextView AMPM;
    public TextView Date;
    public ImageView AlarmIc;
}
class AlarmDateHolder extends ViewHolder {
    public TextView Mon;
    public TextView Tue;
    public TextView Wed;
    public TextView Thr;
    public TextView Fri;
    public TextView Sat;
    public TextView Sun;
}
