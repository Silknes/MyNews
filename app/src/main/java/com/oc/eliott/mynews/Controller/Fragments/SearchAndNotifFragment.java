package com.oc.eliott.mynews.Controller.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.eliott.mynews.Controller.Activities.ResultSearchActivity;
import com.oc.eliott.mynews.R;
import com.oc.eliott.mynews.Utils.ActivityType;
import com.oc.eliott.mynews.Utils.NYTCalls;

import java.util.Calendar;

public class SearchAndNotifFragment extends Fragment {
    private View linearLayoutDate, linearLayoutBorder;
    private RelativeLayout relativeLayoutEnableNotif;
    private Button btnSearch;
    private ImageButton imageButtonBeginDate, imageButtonEndDate;
    private TextView txtBeginDate, txtEndDate, txtWrongDate, obligationToCheck;

    private EditText editTextQueryTerm;
    private CheckBox checkBoxJobs, checkBoxNational, checkBoxBusiness, checkBoxFood, checkBoxSports;
    private Switch switchEnableNotif;
    private boolean isSwitchCheckedBoolean;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private DatePickerDialog.OnDateSetListener mDateSetListenerBeginDate;
    private DatePickerDialog.OnDateSetListener mDateSetListenerEndDate;
    private int year, month, day;

    private long longBeginDate, longEndDate;
    private String queryTerm, newsDesk;

    public static final String KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN = "KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN";
    public static final String KEY_PREFERENCES_CHECKBOX_JOBS = "KEY_PREFERENCES_CHECKBOX_JOBS";
    public static final String KEY_PREFERENCES_CHECKBOX_NATIONAL = "KEY_PREFERENCES_CHECKBOX_NATIONAL";
    public static final String KEY_PREFERENCES_CHECKBOX_BUSINESS = "KEY_PREFERENCES_CHECKBOX_BUSINESS";
    public static final String KEY_PREFERENCES_CHECKBOX_FOOD = "KEY_PREFERENCES_CHECKBOX_FOOD";
    public static final String KEY_PREFERENCES_CHECKBOX_SPORTS = "KEY_PREFERENCES_CHECKBOX_SPORTS";

    public SearchAndNotifFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search_and_notif, container, false);

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

        // Preferences are used to save the state of all checkbox
        preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        // The button for search activity is disabled by default
        btnSearch.setEnabled(false);

        /* Set default value for longBeginDate and longEndDate
        so if user don't enter a value for them we can still compare the date */
        longBeginDate = 19820101;
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.DAY_OF_MONTH) < 10)
            longEndDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + "0" + cal.get(Calendar.DAY_OF_MONTH));
        else longEndDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH));

        this.addTextWatcherOnEditTextQueryTerm();
        this.addListenerToSetEachDate(imageButtonBeginDate);
        this.addListenerToSetEachDate(imageButtonEndDate);
        this.onSwitchStateChange();
        this.isSwitchChecked();
        this.btnSearchOnClick();

        return view;
    }

    // Used to update the view according to the parent activity
    public void updateViewConsideringParentActivity(ActivityType activityType){
        if(activityType.toString().equals(ActivityType.SEARCH.toString())){
            this.linearLayoutBorder.setVisibility(View.GONE);
            this.relativeLayoutEnableNotif.setVisibility(View.GONE);
        }
        if(activityType.toString().equals(ActivityType.NOTIFICATION.toString())){
            this.linearLayoutDate.setVisibility(View.GONE);
            this.btnSearch.setVisibility(View.GONE);
            this.obligationToCheck.setVisibility(View.GONE);
        }
        this.txtWrongDate.setVisibility(View.GONE);
    }

    // Add listener for each checkbox and save the state of the checkbox if we are in the NotificationActivity
    public void addListenerOnCheckboxConsideringParentActivity(ActivityType activityType){
        this.addListenerOnCheckBoxNotifActivity(checkBoxJobs,KEY_PREFERENCES_CHECKBOX_JOBS, activityType);
        this.addListenerOnCheckBoxNotifActivity(checkBoxNational, KEY_PREFERENCES_CHECKBOX_NATIONAL, activityType);
        this.addListenerOnCheckBoxNotifActivity(checkBoxBusiness, KEY_PREFERENCES_CHECKBOX_BUSINESS, activityType);
        this.addListenerOnCheckBoxNotifActivity(checkBoxFood, KEY_PREFERENCES_CHECKBOX_FOOD, activityType);
        this.addListenerOnCheckBoxNotifActivity(checkBoxSports, KEY_PREFERENCES_CHECKBOX_SPORTS, activityType);
    }

    // Method that add a TextWatcher on the EditText
    private void addTextWatcherOnEditTextQueryTerm(){
        editTextQueryTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if all the conditions return true then the button becomes enable
                if(isQueryTermEditTextEmpty() && isCheckBoxChecked() && isDatesCorrect()) {
                    btnSearch.setEnabled(true);
                }
                else btnSearch.setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // Return true if the EditText isn't empty and if it only contains spaces
    private boolean isQueryTermEditTextEmpty(){
        if(!editTextQueryTerm.toString().isEmpty()){
            queryTerm = editTextQueryTerm.getText().toString().trim();
            if(queryTerm.isEmpty()) return false;
            else return true;
        }
        else return false;
    }

    // Add a Listener on each checkBox
    private void addListenerOnCheckBoxNotifActivity(final CheckBox checkBox, final String keyCheckBox, final ActivityType activityType){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If all the conditions return true when the checkbox is checked then the button becomes enable
                if(isQueryTermEditTextEmpty() && isCheckBoxChecked() && isDatesCorrect()) {
                    btnSearch.setEnabled(true);
                }
                else btnSearch.setEnabled(false);

                // If we are in the NotificationActivity then we save the state of the checkbox
                if(activityType.toString().equals(ActivityType.NOTIFICATION.toString()))
                    editor.putBoolean(keyCheckBox, checkBox.isChecked()).apply();
            }
        });
        // If we are in the NotificationActivity then we set the good state for each checkbox when the activity is starting
        if(activityType.toString().equals(ActivityType.NOTIFICATION.toString())){
            boolean isCheckBoxChecked = getActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(keyCheckBox, false);
            checkBox.setChecked(isCheckBoxChecked);
        }
    }

    // If at least one checkbox is checked then this method return true
    private boolean isCheckBoxChecked(){
        boolean booleanCheckBoxChecked;

        if(checkBoxJobs.isChecked() ||
                checkBoxNational.isChecked() ||
                checkBoxBusiness.isChecked() ||
                checkBoxFood.isChecked() ||
                checkBoxSports.isChecked()) booleanCheckBoxChecked = true;
        else booleanCheckBoxChecked = false;

        return booleanCheckBoxChecked;
    }

    // Add a listener for each date to open a DatePickerDialog on click
    private void addListenerToSetEachDate(final ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* We call the method that configure the DatePickerDialog
                if the user sets the date for BeginDate then the DatePickerDialog is mDateSetListenerBeginDate
                else its mDateSetListenerEndDate */
                if(imageButton == imageButtonBeginDate) configureDatePickerDialog(mDateSetListenerBeginDate);
                if(imageButton == imageButtonEndDate) configureDatePickerDialog(mDateSetListenerEndDate);
            }
        });

        // Add a listener for the DatePickerDialog
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Create a TextView that takes a different value if we click for begin date or end date
                TextView txtDate;
                if (imageButton == imageButtonBeginDate){
                    txtDate = txtBeginDate;
                }
                else txtDate = txtEndDate;

                month = month + 1;

                // Change the text of the ImageView with year/month/day
                txtDate.setText(year + "/" + month + "/" + dayOfMonth);

                // Change the value of longBeginDate or longEndDate
                if (imageButton == imageButtonBeginDate) longBeginDate = Long.parseLong("" + year + month + dayOfMonth);
                else longEndDate = Long.parseLong("" + year + month + dayOfMonth);

                Calendar cal = Calendar.getInstance();
                long currentDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH));
                if(longBeginDate <= currentDate && longEndDate <= currentDate) {
                    if(isDatesCorrect()) txtWrongDate.setVisibility(View.GONE);
                    else txtWrongDate.setVisibility(View.VISIBLE);
                }
                else txtWrongDate.setVisibility(View.VISIBLE);

                // If all the conditions return true when the date is set then the button becomes enable
                if(isQueryTermEditTextEmpty() && isCheckBoxChecked() && isDatesCorrect()) btnSearch.setEnabled(true);
                else btnSearch.setEnabled(false);
            }
        };

        if(imageButton == imageButtonBeginDate)
            mDateSetListenerBeginDate = onDateSetListener;
        else mDateSetListenerEndDate = onDateSetListener;
    }

    // Configure a DatePickerDialog with Year, Month and Day
    private void configureDatePickerDialog(DatePickerDialog.OnDateSetListener datePickerDialog){
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                datePickerDialog,
                year, month, day);
        dialog.show();
    }

    // Compare the two dates and if EndDate is superior to BeginDate then return true
    private boolean isDatesCorrect(){
        boolean booleanIsDateCorrect;

        if(longEndDate >= longBeginDate) booleanIsDateCorrect = true;
        else booleanIsDateCorrect = false;

        return booleanIsDateCorrect;
    }

    // Use to get all the category that the use want according to the state of each checkbox
    private void createNewsDeskString(){
        newsDesk = "news_desk:(";
        if(checkBoxJobs.isChecked()) newsDesk = newsDesk + "\"Jobs\"";
        if(checkBoxNational.isChecked()) newsDesk = newsDesk + "\"National\"";
        if(checkBoxBusiness.isChecked()) newsDesk = newsDesk + "\"Business\"";
        if(checkBoxFood.isChecked()) newsDesk = newsDesk + "\"Food\"";
        if(checkBoxSports.isChecked()) newsDesk = newsDesk + "\"Sports\"";
        if(newsDesk.contains("\"\"")) newsDesk = newsDesk.replace("\"\"", "\" \"");
        newsDesk = newsDesk + ")";
    }

    // Performed a click on SearchButton to start a new Activity where we pass the value input by the user
    private void btnSearchOnClick(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewsDeskString();
                Intent intent = new Intent(getActivity(), ResultSearchActivity.class);
                intent.putExtra("BEGIN_DATE", longBeginDate);
                intent.putExtra("END_DATE", longEndDate);
                intent.putExtra("NEWS_DESK", newsDesk);
                intent.putExtra("QUERY_TERM", queryTerm);
                startActivity(intent);
            }
        });
    }

    // Add a listener to the switch
    private void onSwitchStateChange(){
        switchEnableNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // if the switch is checked then return true else return false
                if(switchEnableNotif.isChecked()) isSwitchCheckedBoolean = true;
                else isSwitchCheckedBoolean = false;
                // Save the return in the preferences
                editor.putBoolean(KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN, isSwitchCheckedBoolean);
                editor.apply();
            }
        });
    }

    // Get back the value in the preferences for the switch, if it's true then the switch becomes checked else it's unchecked
    private void isSwitchChecked(){
        isSwitchCheckedBoolean = getActivity().getPreferences(Context.MODE_PRIVATE).getBoolean(KEY_PREFERENCES_IS_SWITCH_CHECKED_BOOLEAN, false);
        if(isSwitchCheckedBoolean) switchEnableNotif.setChecked(true);
        else switchEnableNotif.setChecked(false);
    }
}
