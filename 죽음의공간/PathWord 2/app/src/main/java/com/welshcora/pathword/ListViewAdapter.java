package com.welshcora.pathword;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public static final int WORDBOOK_WORD_TYPE = 14;


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
        } else if(type == WORDBOOK_WORD_TYPE){
            wordBook_WordHolder holder = new wordBook_WordHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_wordbook_word, null);

            holder.word = (TextView) convertView.findViewById(R.id.word);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.dynamicLayout = (RelativeLayout) convertView.findViewById(R.id.dynamicLayout);

            convertView.setTag(holder);

            wordBook_WordData mData = (wordBook_WordData) tempList.get(position);
            char check = mData.dateOrAlphabet.charAt(0);
            holder.dynamicLayout = (RelativeLayout) convertView.findViewById(R.id.dynamicLayout);
            TextView temp = new TextView(convertView.getContext());
            if(('a' <= check && check <= 'z') ||('A' <= check && check <= 'Z') || Character.isDigit(check)){//들어온 데이터가 알파벳이나 날짜이면?
                holder.dynamicLayout.addView(temp);
                temp.setText(mData.dateOrAlphabet);
                temp.setGravity(Gravity.CENTER);
                temp.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                temp.setTextAppearance(convertView.getContext(), android.R.style.TextAppearance_Medium);
            } else if (mData.dateOrAlphabet.equals("MakeCheckBox")){ //체크박스가 오면?
                CheckBox checkBox = new CheckBox(convertView.getContext());
                holder.dynamicLayout.addView(checkBox);
                checkBox.setGravity(Gravity.CENTER);
                checkBox.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                checkBox.setOnClickListener(new myOnClick()); //나중에 어댑터가 정의된 프래그먼트에서 홀더로 가져와야함.
            }
            holder.word.setText(mData.word);
            //holder.description.setText(mData.description);

            fontChanger = new FontChangeCrawler(convertView.getContext().getAssets());
            fontChanger.replaceFonts((ViewGroup) convertView);

            holder.word.setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
            temp.setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));
        }
        if((type != NORMALTYPEFACE_TYPE) && type != WORDBOOK_WORD_TYPE){ //환경설정에서 서체 고를때 안에있는게 바뀌지 않게 하기 위해서, 또 단어장 단어 에서 굵은거 안없어지게
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
        } else if (type == NORMALTYPEFACE_TYPE){
            NormalTypefaceListData addInfo;
            addInfo = new NormalTypefaceListData();
            addInfo.setType(type);
            addInfo.path = mTitle; //이게 타입페이스 경로야 여기 헷갈리지마
            addInfo.mTitle = point;//폰트 이름
            tempList.add(addInfo);
        }
    }

    public void addItem(String dateOrAlphabet, String word, String description,int type){
        wordBook_WordData addInfo;
        addInfo = new wordBook_WordData();
        addInfo.setType(type);
        addInfo.dateOrAlphabet = dateOrAlphabet;
        addInfo.word = word;
        addInfo.description = description;
        tempList.add(addInfo);
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
class wordBook_WordHolder{
    RelativeLayout dynamicLayout;
    TextView description;
    TextView word;
}
//여기서부터는 Expandable Listview를 위한 홀더 와 어뎁터
class ExpandableListViewAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;

    private List<GroupItem> items;

    FontChangeCrawler fontChanger;

    Context parentContext;

    String word;
    String descrtiption;
    DbAdapter mDbAdapter;

    public ExpandableListViewAdapter(Context context, DbAdapter db) {
        mDbAdapter = db;
        parentContext = context; inflater = LayoutInflater.from(context);
    }

    public void setData(List<GroupItem> items) {
        this.items = items;
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        ChildItem item = getChild(groupPosition, childPosition);

        descrtiption = item.description;
        word = item.word;

        holder = new ChildHolder();
        convertView = inflater.inflate(R.layout.listview_wordchild, parent, false);
        holder.description = (TextView) convertView.findViewById(R.id.description);
        holder.addButton = (Button) convertView.findViewById(R.id.addButton);
        convertView.setTag(holder);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
                Log.v("추가한 단어", word);
                Log.v("추가한 설명", descrtiption);
                Log.v("날짜", formatter.format(today));
                if(mDbAdapter.addWordAtBook(word, descrtiption, formatter.format(today))){
                    Toast.makeText(parentContext, "'" + word + "'가 단어장에 추가되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(parentContext, "이미 단어장에 있는 단어입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.description.setText(item.description);
        fontChanger = new FontChangeCrawler(convertView.getContext().getAssets());
        fontChanger.replaceFonts((ViewGroup) convertView);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).items.size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        GroupItem item = getGroup(groupPosition);
        holder = new GroupHolder();
        convertView = inflater.inflate(R.layout.listview_word, parent, false);
        holder.word = (TextView) convertView.findViewById(R.id.word);
        holder.dynamicLayout = (LinearLayout) convertView.findViewById(R.id.dynamicLayout);
        for(int i = 0; i < item.formCount; i++){
            holder.form.add(new TextView(convertView.getContext()));
            holder.dynamicLayout.addView(holder.form.get(i));
        }
        convertView.setTag(holder);

        holder.word.setText(item.word);
        fontChanger = new FontChangeCrawler(convertView.getContext().getAssets());
        fontChanger.replaceFonts((ViewGroup) convertView);//먼저 다른 요소들을 설정된 서체로 설정하고 나머지 specific한 요소들을 굵게 해준다.
        for(int i = 0; i<item.formCount; i++) {
            try{
                holder.form.get(i).setText(item.form.get(i));
                holder.form.get(i).setHeight(130);
                holder.form.get(i).setWidth(130);
                holder.form.get(i).setTextColor(Color.WHITE);
                holder.form.get(i).setGravity(Gravity.CENTER);
                holder.form.get(i).setBackgroundResource(R.drawable.circle);
                holder.form.get(i).setPadding(10, 10, 10, 10);
                holder.form.get(i).setTypeface(Typeface.create(fontChanger.typeface, Typeface.BOLD));//굵게 하려고
            } catch (IndexOutOfBoundsException e){
            }

        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}
class ChildHolder {
    Button addButton;
    TextView description;
}

class GroupHolder {
    TextView word;
    ArrayList<TextView> form = new ArrayList<TextView>();
    LinearLayout dynamicLayout;
}