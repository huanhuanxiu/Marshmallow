package com.greendot.marshmellowhomework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import android.util.Log;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.icu.util.Calendar;
import java.util.concurrent.TimeUnit;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.view.View.OnClickListener;
import android.view.View;
public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        FormatEditText();
    }

    private void FormatEditText() {
        final EditText editStartDate = (EditText) findViewById(R.id.editStartDate);
        final EditText editEndDate = (EditText) findViewById(R.id.editEndDate);
        final EditText editAmount = (EditText) findViewById(R.id.editAmount);
        final EditText editAnualizedYield = (EditText) findViewById(R.id.editAnualizedYield);
        final EditText editExpectedInterest = (EditText) findViewById(R.id.editExpectedInterest);


        editAnualizedYield.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                float amount;
                float anualizedYield;
                float yearLenght;
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
               // ParsePosition pos = new ParsePosition(0);
                try {
                    String startDateString = editStartDate.getText().toString();
                    String endDateString = editEndDate.getText().toString();
                    anualizedYield = Float.parseFloat(editAnualizedYield.getText().toString());
                    amount = Float.parseFloat(editAmount.getText().toString());
                    if(startDateString.length()>0 && endDateString.length()>0) {
                        Date date1 = df.parse(startDateString);
                        Date date2= df.parse(endDateString);
                        long msDiff = date2.getTime() - date1.getTime();
                        long daysDiff =TimeUnit.DAYS.convert(msDiff, TimeUnit.MILLISECONDS);
                        Log.e( "onTextChanged: ", String.valueOf(daysDiff));
                        float interst = amount*(daysDiff/365)*anualizedYield;
                        editExpectedInterest.setText(String.valueOf(interst));
                    }

                } catch(NumberFormatException nfe) {
                    Log.e("Activity1","Could not parse " + nfe);
                }
                catch(ParseException e)
                {
                    e.printStackTrace();
                }


            }


        });

        editStartDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editStartDate.setText(current);
                    editStartDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }
        });

//        editEndDate.addTextChangedListener(new TextWatcher() {
//            private String current = "";
//            private String ddmmyyyy = "DDMMYYYY";
//            private Calendar cal = Calendar.getInstance();
//
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().equals(current)) {
//                    String clean = s.toString().replaceAll("[^\\d.]", "");
//                    String cleanC = current.replaceAll("[^\\d.]", "");
//
//                    int cl = clean.length();
//                    int sel = cl;
//                    for (int i = 2; i <= cl && i < 6; i += 2) {
//                        sel++;
//                    }
//                    //Fix for pressing delete next to a forward slash
//                    if (clean.equals(cleanC)) sel--;
//
//                    if (clean.length() < 8) {
//                        clean = clean + ddmmyyyy.substring(clean.length());
//                    } else {
//                        //This part makes sure that when we finish entering numbers
//                        //the date is correct, fixing it otherwise
//                        int day = Integer.parseInt(clean.substring(0, 2));
//                        int mon = Integer.parseInt(clean.substring(2, 4));
//                        int year = Integer.parseInt(clean.substring(4, 8));
//
//                        if (mon > 12) mon = 12;
//                        cal.set(Calendar.MONTH, mon - 1);
//                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
//                        cal.set(Calendar.YEAR, year);
//                        // ^ first set year for the line below to work correctly
//                        //with leap years - otherwise, date e.g. 29/02/2012
//                        //would be automatically corrected to 28/02/2012
//
//                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
//                        clean = String.format("%02d%02d%02d", day, mon, year);
//                    }
//
//                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current = clean;
//                    editEndDate.setText(current);
//                    editEndDate.setSelection(sel < current.length() ? sel : current.length());
//                }
//            }
//
//
//        });

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel(){

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                editEndDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editEndDate.setOnClickListener (new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Activity1.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }



}
