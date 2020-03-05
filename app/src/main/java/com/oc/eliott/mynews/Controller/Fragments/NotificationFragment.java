package com.oc.eliott.mynews.Controller.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.evernote.android.job.JobManager;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.DisplayNotificationJob;
import com.oc.eliott.mynews.Utils.MyJobCreator;

import androidx.annotation.NonNull;

public class NotificationFragment extends BasicFragment{

    private boolean isSwitchCheckedBoolean; // Variable that contain the state of the switch
    private String queryTerm; // Variable that contain the value of the EditText
    // Public key that use to get the state of the switch
    public static final String KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN = "KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN";
    // Public key that use to get the value of queryTerm
    public static final String KEY_PREFERENCES_QUERY_TERM = "KEY_PREFERENCES_QUERY_TERM";
    // Public key that use to get the value of newsDesk
    public static final String KEY_NEWS_DESK = "KEY_NEWS_DESK";
    // Some public keys that use to get the state of the checkboxes
    public static final String KEY_PREFERENCES_CHECKBOX_JOBS = "KEY_PREFERENCES_CHECKBOX_JOBS";
    public static final String KEY_PREFERENCES_CHECKBOX_NATIONAL = "KEY_PREFERENCES_CHECKBOX_NATIONAL";
    public static final String KEY_PREFERENCES_CHECKBOX_BUSINESS = "KEY_PREFERENCES_CHECKBOX_BUSINESS";
    public static final String KEY_PREFERENCES_CHECKBOX_FOOD = "KEY_PREFERENCES_CHECKBOX_FOOD";
    public static final String KEY_PREFERENCES_CHECKBOX_SPORTS = "KEY_PREFERENCES_CHECKBOX_SPORTS";
    private SharedPreferences.Editor editor; // We need an editor to put all variables in the SharedPreferences
    // Public key that define the SharedPreferences
    public static final String KEY_SHARED_PREFERENCES = "KEY_SHARED_PREFERENCES";

    public NotificationFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        bindView(view);
        updateView();

        // Preferences are used to save the state of all checkbox
        SharedPreferences preferences = this.getActivity().getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Get the value of queryTerm and update the EditText with it
        queryTerm = getActivity().getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(KEY_PREFERENCES_QUERY_TERM, "");
        editTextQueryTerm.setText(queryTerm);

        // Add a Listener to each checkbox
        this.addListenerOnCheckBoxNotifActivity(checkBoxJobs,KEY_PREFERENCES_CHECKBOX_JOBS);
        this.addListenerOnCheckBoxNotifActivity(checkBoxNational, KEY_PREFERENCES_CHECKBOX_NATIONAL);
        this.addListenerOnCheckBoxNotifActivity(checkBoxBusiness, KEY_PREFERENCES_CHECKBOX_BUSINESS);
        this.addListenerOnCheckBoxNotifActivity(checkBoxFood, KEY_PREFERENCES_CHECKBOX_FOOD);
        this.addListenerOnCheckBoxNotifActivity(checkBoxSports, KEY_PREFERENCES_CHECKBOX_SPORTS);

        // Create newsDesk with the value return if the checkboxes are checked or not
        this.createNewsDeskString();

        // Add a TextWatcher to the EditText
        this.addTextWatcherOnEditTextQueryTerm();

        //Add a listener to the Switch
        this.onSwitchStateChange();
        // Call the job if the switch is on
        this.isSwitchChecked();

        return view;
    }

    @Override
    protected void bindView(View view) {
        super.bindView(view);
    }

    // Set the visibility of some view because they are only use in the SearchFragment
    private void updateView(){
        this.linearLayoutDate.setVisibility(View.GONE);
        this.btnSearch.setVisibility(View.GONE);
        this.obligationToCheck.setVisibility(View.GONE);
        this.txtWrongDate.setVisibility(View.GONE);
    }

    // Method that add a TextWatcher on the EditText
    private void addTextWatcherOnEditTextQueryTerm(){
        editTextQueryTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                queryTerm = s.toString().trim();
                editor.putString(KEY_PREFERENCES_QUERY_TERM, queryTerm).apply();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // Add a Listener on each checkBox
    private void addListenerOnCheckBoxNotifActivity(final CheckBox checkBox, final String keyCheckBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean(keyCheckBox, checkBox.isChecked()).apply();
            }
        });
        // If we are in the NotificationActivity then we set the good state for each checkbox when the activity is starting
        boolean isCheckBoxChecked =
                getActivity().getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE).getBoolean(keyCheckBox, false);
        checkBox.setChecked(isCheckBoxChecked);
    }

    // Add a listener to the switch
    private void onSwitchStateChange(){
        switchEnableNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // if the switch is checked then return true else return false
                isSwitchCheckedBoolean = switchEnableNotif.isChecked();
                // Save the return in the preferences
                editor.putBoolean(KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN, isSwitchCheckedBoolean);
                editor.apply();
            }
        });
    }

    // Get back the value in the preferences for the switch, if it's true then the switch becomes checked else it's unchecked
    private void isSwitchChecked(){
        isSwitchCheckedBoolean = getActivity()
                .getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN, false);
        if(isSwitchCheckedBoolean) {
            switchEnableNotif.setChecked(true);
            JobManager.create(getActivity()).addJobCreator(new MyJobCreator());
            DisplayNotificationJob.schedulePeriodic();
        }
        else switchEnableNotif.setChecked(false);
    }

    // Use to get all the category that the use want according to the state of each checkbox
    private void createNewsDeskString(){
        String newsDesk = "news_desk:(";
        if(checkBoxJobs.isChecked()) newsDesk = newsDesk + "\"Jobs\"";
        if(checkBoxNational.isChecked()) newsDesk = newsDesk + "\"National\"";
        if(checkBoxBusiness.isChecked()) newsDesk = newsDesk + "\"Business\"";
        if(checkBoxFood.isChecked()) newsDesk = newsDesk + "\"Food\"";
        if(checkBoxSports.isChecked()) newsDesk = newsDesk + "\"Sports\"";
        if(newsDesk.contains("\"\"")) newsDesk = newsDesk.replace("\"\"", "\" \"");
        newsDesk = newsDesk + ")";
        editor.putString(KEY_NEWS_DESK, newsDesk).apply();
    }
}
