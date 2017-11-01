package com.ali.question.start;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */
public class AdapterFragment extends FragmentPagerAdapter {

    final int PAGE_TAB =2;
    private String tabTitle []=new String[]{"حل شده ها","سوالات"};

    public AdapterFragment(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return PAGE_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return  tabTitle[position];
    }

}
