package com.example.minor2;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapterOwner extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public void addFragment(Fragment f, String title){
        mFragmentList.add(f);
        mFragmentTitleList.add(title);
    }

    public SectionsPageAdapterOwner(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
