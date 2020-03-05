package com.oc.eliott.mynews.Utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oc.eliott.mynews.Controller.Fragments.RecyclerViewFragment;

// Class that configure the PageAdapter
public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager mgr){
        super(mgr);
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerViewFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "TopStories";
            case 1:
                return "MostPopular";
            case 2:
                return "Business";
            case 3:
                return "Food";
            case 4:
                return "Sport";
            default:
                return null;
        }
    }
}
