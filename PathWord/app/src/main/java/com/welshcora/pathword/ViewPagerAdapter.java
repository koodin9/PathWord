package com.welshcora.pathword;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by DuckG on 2015-09-02.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    String Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    String type;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,String mTitles[], int mNumbOfTabsumb, String type) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.type = type;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if(type.equals("wordbook")){
            if(position == 0) {// if the position is 0 we are returning the First tab
                TapWordFragment tab1 = new TapWordFragment();
                return tab1;
            }
            else if(position == 1)  {           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
                TapRootFragment tab2 = new TapRootFragment();
                return tab2;
            } else {
                TapTestFragment tab3 = new TapTestFragment();
                return tab3;
            }
        } else {
            if(position == 0) {// if the position is 0 we are returning the First tab
                TapSearchEngkor tab1 = new TapSearchEngkor();
                return tab1;
            }
            else if (position == 1) {           // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
                TapSearchFriend tab2 = new TapSearchFriend();
                return tab2;
            }
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}