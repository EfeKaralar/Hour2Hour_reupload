package com.example.hour2hour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.Calendar;

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Time Spinner
        Spinner setHour = findViewById(R.id.selectedHour);
        Spinner setMinute = findViewById(R.id.selectedMinute);
        ArrayAdapter<CharSequence> hourAdapter = ArrayAdapter.createFromResource(this, R.array.hours, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> minuteAdapter = ArrayAdapter.createFromResource(this, R.array.minutes, android.R.layout.simple_spinner_item);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setHour.setAdapter(hourAdapter);
        setMinute.setAdapter(minuteAdapter);
        setHour.setOnItemSelectedListener(this); setMinute.setOnItemSelectedListener(this);
        String firstHour = setHour.getSelectedItem().toString();
        String firstMinute = setMinute.getSelectedItem().toString();

        //TimeZone Spinners
        Spinner TZ_input = findViewById(R.id.inputZone);
        Spinner TZ_output= findViewById(R.id.outputZone);
        ArrayAdapter<CharSequence> timezonesAdapter = ArrayAdapter.createFromResource(this, R.array.time_zones, android.R.layout.simple_spinner_item);
        timezonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TZ_input.setAdapter(timezonesAdapter);
        TZ_output.setAdapter(timezonesAdapter);
        TZ_input.setOnItemSelectedListener(this); TZ_output.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String t = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), t, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void Convert_Button(View v) {
        Spinner Hour1 = findViewById(R.id.selectedHour);
        Spinner Minute1 = findViewById(R.id.selectedMinute);
        Spinner Timezone1 = findViewById(R.id.inputZone);
        Spinner Timezone2 = findViewById(R.id.outputZone);
        int[] hour_min = Convert_Time(Hour1.getSelectedItem().toString(), Minute1.getSelectedItem().toString(), Timezone1.getSelectedItem().toString(),Timezone2.getSelectedItem().toString());
        String Hour2 = String.valueOf(hour_min[0]);
        String Minute2 = String.valueOf(hour_min[1]);
        //am2pm_pm2am(hour_min[2]);
        TextView H2 = findViewById(R.id.FinalHour); TextView M2 = findViewById(R.id.FinalMinute);
        H2.setText(Hour2); M2.setText(Minute2);
    }
    public int[] Convert_Time(String h, String m, String TZ1, String TZ2){
        int h1 = Integer.parseInt(h);
        int minut=Integer.parseInt(m);
        int TZ1_int = convert_tz_to_int(TZ1);
        int TZ2_int = convert_tz_to_int(TZ2);
        int TZ_difference = TZ2_int - TZ1_int;
        int h2_full = (h1 + TZ_difference);
        int rotation = round(h2_full/12);
        int h2 = h2_full%24;
        return new int[]{h2, minut, rotation};
    }

    public int convert_tz_to_int(String TZ){
        if (TZ.length()==3){
            return 0;
        }
        else{
            return Integer.parseInt(TZ.substring(3,TZ.length()));
        }
    }
    public void am2pm_pm2am(int r){
        Button b = findViewById(R.id.am_pm);
        TextView t = findViewById(R.id.final_am_pm);
        for (int i=0; i<r; ++i){
            if (b.getText().equals("AM")){
                t.setText("PM");
            }
            else if (b.getText().equals("PM")){
                t.setText("AM");
            }
        }
    }

    public String am_pm_switcher(View v){
        Button b = (Button) v;
        if (b.getText().equals("AM")){
            b.setText("PM");
            return "PM";
        }
        else if (b.getText().equals("PM")){
            b.setText("AM");
            return "AM";
        }
        else {return null;}
    }
}