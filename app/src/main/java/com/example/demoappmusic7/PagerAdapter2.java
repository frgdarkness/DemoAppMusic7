package com.example.demoappmusic7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter2 extends FragmentStatePagerAdapter {

    private Fragment[] childFragments;

    public PagerAdapter2(FragmentManager fm){
        super(fm);
        childFragments = new Fragment[]{
                new FragmentSearch(),
                new FragmentListSong3()
        };
    }

    @Override
    public Fragment getItem(int i) {
        return childFragments[i];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = getItem(position).getClass().getName();
        return title.subSequence(title.lastIndexOf(".")+1,title.length());
    }
}
