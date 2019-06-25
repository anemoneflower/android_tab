package com.example.android_tab_sy;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;


public class MyPagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public MyPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FirstTab tab1 = new FirstTab();
                return tab1;
            case 1:
                SecondTab tab2 = new SecondTab();
                return tab2;
            case 2:
                ThirdTab tab3 = new ThirdTab();
                return tab3;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
