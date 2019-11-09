package com.project.sltours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Time_different extends AppCompatActivity {


    TextView currentSL,txtDifferent,selectTime;
    Spinner spinner;


    Calendar calendar;
    long miliSeconds;
    ArrayAdapter<String> idAdapter;
    SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_different);


        currentSL = (TextView)findViewById(R.id.currentSL);
        txtDifferent =(TextView)findViewById(R.id.txtDifferent);
        spinner =(Spinner)findViewById(R.id.countrySpinner);
        selectTime =(TextView)findViewById(R.id.selectTime);

        String[] idArray = TimeZone.getAvailableIDs();

        sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        final TimeZone etTimeZone = TimeZone.getTimeZone("GMT+5:30"); //Target timezone
        final Date currentDate = new Date();  // sl date to br convert
        sdf.setTimeZone(etTimeZone);
        currentSL.setText(sdf.format(currentDate));

        getGMItime();


        idAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idArray);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(idAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGMItime();
                String selectedID = (String)(parent.getItemAtPosition(position));
                TimeZone timeZone = TimeZone.getTimeZone((selectedID));
                String TimeZoneName = timeZone.getDisplayName();

                int TimeZoneOffset = timeZone.getRawOffset() / (60 *1000);
                int hrs = TimeZoneOffset/60;
                int mins = TimeZoneOffset % 60;


                miliSeconds = miliSeconds + timeZone.getRawOffset();
                Date resultDate = new Date(miliSeconds); // date given withour the format



                selectTime.setText(" " + sdf.format(resultDate));




                Date d1 = null;
                Date d2 = null;
                String dateStart = selectTime.getText().toString();
                String dateStop = currentSL.getText().toString();


                try {
                    d1 = sdf.parse(dateStart);
                    d2 = sdf.parse(dateStop);


                    //in milliseconds
                    long diff = d2.getTime() - d1.getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    txtDifferent.setText(diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.");



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getGMItime(){
        calendar = Calendar.getInstance();
        miliSeconds = calendar.getTimeInMillis();

        TimeZone tzCurrent = calendar.getTimeZone();
        int offset = tzCurrent.getRawOffset();
        if(tzCurrent.inDaylightTime(new Date())){
            offset = offset + tzCurrent.getDSTSavings();
        }
        miliSeconds = miliSeconds - offset;
        Date resultDate = new Date(miliSeconds);




    }
}
