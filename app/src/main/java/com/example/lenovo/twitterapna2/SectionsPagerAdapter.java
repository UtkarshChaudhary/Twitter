package com.example.lenovo.twitterapna2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by lenovo on 02-08-2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter{


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
      //error
    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return 4;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "My Tweets";
//            case 1:
//                return "SEARCH";
//            case 2:
//                return "RECENT";
//            case 3:
//                return "TRENDS";
//        }
//        return null;
//    }
}
