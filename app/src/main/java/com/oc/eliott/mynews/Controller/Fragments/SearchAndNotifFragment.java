package com.oc.eliott.mynews.Controller.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.eliott.mynews.R;

public class SearchAndNotifFragment extends Fragment {


    public SearchAndNotifFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_and_notif, container, false);
    }

}
