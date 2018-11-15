package com.oc.eliott.mynews.Controller.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.oc.eliott.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicFragment extends Fragment {
    // Bind all the view that is used in ChildFragment
    protected View linearLayoutDate, linearLayoutBorder;
    protected RelativeLayout relativeLayoutEnableNotif;
    protected Button btnSearch;
    protected ImageButton imageButtonBeginDate, imageButtonEndDate;
    protected TextView txtBeginDate, txtEndDate, txtWrongDate, obligationToCheck;
    protected EditText editTextQueryTerm;
    protected CheckBox checkBoxJobs, checkBoxNational, checkBoxBusiness, checkBoxFood, checkBoxSports;
    protected Switch switchEnableNotif;

    public BasicFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    protected void bindView(View view){
        this.linearLayoutDate = view.findViewById(R.id.search_notif_fragment_linear_layout_date);
        this.linearLayoutBorder = view.findViewById(R.id.search_notif_fragment_linear_layout_border);
        this.relativeLayoutEnableNotif = view.findViewById(R.id.search_notif_fragment_relative_layout_enable_notif);
        this.btnSearch = view.findViewById(R.id.search_notif_fragment_btn_search);

        this.imageButtonBeginDate = view.findViewById(R.id.search_notif_fragment_img_btn_begin_date);
        this.imageButtonEndDate = view.findViewById(R.id.search_notif_fragment_img_btn_end_date);
        this.txtBeginDate = view.findViewById(R.id.search_notif_fragment_txt_begin_date);
        this.txtEndDate = view.findViewById(R.id.search_notif_fragment_txt_end_date);
        this.txtWrongDate = view.findViewById(R.id.search_notif_fragment_txt_wrong_date);
        this.obligationToCheck = view.findViewById(R.id.search_notif_fragment_txt_obligation_to_check);

        this.editTextQueryTerm = view.findViewById(R.id.search_notif_fragment_edit_txt_query_term);
        this.checkBoxJobs = view.findViewById(R.id.search_notif_fragment_checkbox_jobs);
        this.checkBoxNational = view.findViewById(R.id.search_notif_fragment_checkbox_national);
        this.checkBoxBusiness = view.findViewById(R.id.search_notif_fragment_checkbox_business);
        this.checkBoxFood = view.findViewById(R.id.search_notif_fragment_checkbox_food);
        this.checkBoxSports = view.findViewById(R.id.search_notif_fragment_checkbox_sports);

        this.switchEnableNotif = view.findViewById(R.id.search_notif_fragment_switch_enable_notif);
    }
}
