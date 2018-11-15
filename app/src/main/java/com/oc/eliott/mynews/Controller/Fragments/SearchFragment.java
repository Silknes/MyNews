package com.oc.eliott.mynews.Controller.Fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oc.eliott.mynews.Controller.Activities.ResultSearchActivity;
import com.oc.eliott.mynews.R;

import java.util.Calendar;

public class SearchFragment extends BasicFragment{

    private String queryTerm, newsDesk;
    private DatePickerDialog.OnDateSetListener mDateSetListenerBeginDate, mDateSetListenerEndDate;
    private int year, month, day;
    private long longBeginDate, longEndDate;

    public SearchFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        this.bindView(view);
        this.updateView();

        // The button for search activity is disabled by default
        btnSearch.setEnabled(false);

        queryTerm = editTextQueryTerm.getText().toString();

        /* Set default value for longBeginDate and longEndDate
        so if user don't enter a value for them we can still compare the date */
        longBeginDate = 19820101;
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.DAY_OF_MONTH) < 10)
            longEndDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + "0" + cal.get(Calendar.DAY_OF_MONTH));
        else longEndDate = Long.parseLong("" + cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH) + 1) + cal.get(Calendar.DAY_OF_MONTH));

        // Add a TextWatcher to verify that the user enter something and get it
        this.addTextWatcherOnEditTextQueryTerm();

        // Add a Listener to each Checkbox to know if they are checked or not
        this.addListenerOnCheckBoxNotifActivity(checkBoxJobs);
        this.addListenerOnCheckBoxNotifActivity(checkBoxNational);
        this.addListenerOnCheckBoxNotifActivity(checkBoxBusiness);
        this.addListenerOnCheckBoxNotifActivity(checkBoxFood);
        this.addListenerOnCheckBoxNotifActivity(checkBoxSports);

        // Add a Listener to open a DatePickerDialog to set the date
        this.addListenerToSetEachDate(imageButtonBeginDate);
        this.addListenerToSetEachDate(imageButtonEndDate);

        // Add a listener to the search button to open the ResultSearchActivity
        this.btnSearchOnClick();

        return view;
    }

    // This method bind the view of the BasicFragment
    @Override
    protected void bindView(View view) {
        super.bindView(view);
    }

    // Disable some views of BasicFragment because we are in the SearchFragment
    private void updateView(){
        this.linearLayoutBorder.setVisibility(View.GONE);
        this.relativeLayoutEnableNotif.setVisibility(View.GONE);
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
        return !queryTerm.isEmpty();
    }

    // Add a Listener on each checkBox
    private void addListenerOnCheckBoxNotifActivity(final CheckBox checkBox){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // If all the conditions return true when the checkbox is checked then the button becomes enable
                if(isQueryTermEditTextEmpty() && isCheckBoxChecked() && isDatesCorrect()) {
                    btnSearch.setEnabled(true);
                }
                else btnSearch.setEnabled(false);
            }
        });
    }

    // If at least one checkbox is checked then this method return true
    private boolean isCheckBoxChecked(){
        return checkBoxJobs.isChecked() ||
                checkBoxNational.isChecked() ||
                checkBoxBusiness.isChecked() ||
                checkBoxFood.isChecked() ||
                checkBoxSports.isChecked();
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
        return longEndDate >= longBeginDate;
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
}
