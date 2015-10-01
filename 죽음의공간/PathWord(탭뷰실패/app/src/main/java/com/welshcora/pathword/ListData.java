package com.welshcora.pathword;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DuckG on 2015-08-25.
 */
public class ListData {
        public String mTitle;
        protected int type;
        public void setType(int _type)
        {
                type = _type;
        }
        public int getType()
        {
                return type;
        }
        public String getmTitle() {return mTitle;}
}

class LableORNormalData extends ListData {
}
class NormalTypefaceListData extends ListData {
    //서체설정, 서체크기 다이얼로그에서 필요함
    public String path;
}
class SwitchData extends ListData {
        public Switch aSwitch;
}
class RankListData extends ListData {
        public String personName;
        public String nickName;
        public String rank;
        public String ability;
        public Drawable ProfilePic;
}
class PointListData extends ListData {
        public String point;
}
class CombineListData extends ListData {
        public String rank;
        public String friend;
        public String wordbook;
        public String myPath;
}
class FriendListData extends ListData {
        public String personName;
        public String nickName;
        public Drawable ProfilePic;
}
class AlarmMainListData extends ListData {
        public String Hour;
        public String Minute;
        public String AMPM;
        public String Date;
        public ImageView AlarmIc;
}
class AlarmDateListData extends ListData {
        public String mon;
        public String tue;
        public String wed;
        public String thr;
        public String fri;
        public String sat;
        public String sun;
}
class wordBook_WordData extends ListData{
        String dateOrAlphabet;
        String description;
        String word;
}
//여기서 부터는 expandable Listview를 위한 Data
class GroupItem {
        String word;
        int formCount = 0;
        ArrayList<String> form = new ArrayList<String>();
        List<ChildItem> items = new ArrayList<ChildItem>();//ChildData는 하나만 들어갈 예정.
}

class ChildItem {
        String description;
        String word;
}

