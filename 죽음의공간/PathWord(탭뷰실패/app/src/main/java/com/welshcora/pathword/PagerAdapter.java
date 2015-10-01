package com.welshcora.pathword;

/**
 * Created by apple on 15. 9. 24..
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TapWordFragment tab1 = new TapWordFragment();
                return tab1;
            case 1:
                TapRootFragment tab2 = new TapRootFragment();
                return tab2;
            case 2:
                TapTestFragment tab3 = new TapTestFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}