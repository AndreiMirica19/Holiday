package com.example.holiday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;

public class AddTrip extends AppCompatActivity {
    private static final  String TAG="AddTrip";
    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog.OnDateSetListener DataSetListener;
    private DatePickerDialog.OnDateSetListener DataSetListenerEnd;
    private MaterialButton btn;
    private EditText tripName;
    private EditText destination;
    private RadioGroup radioGroup;
    private Slider slider;
    private RatingBar ratingBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip);
        startDate=(TextView) findViewById(R.id.dataStart);
        endDate=(TextView)findViewById(R.id.dataEnd);
        tripName=(EditText) findViewById(R.id.TripNameInput);
        destination=(EditText)findViewById(R.id.DestinationInput);
        radioGroup=(RadioGroup)findViewById(R.id.radio);
        slider=(Slider)findViewById(R.id.slider);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setStepSize(1.0f);

        startDate.setOnClickListener(view->{
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog,DataSetListener,year,month,day);
            dialog.show();
        });
        endDate.setOnClickListener(view->{
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog=new DatePickerDialog(this,
                    android.R.style.Theme_Holo_Light_Dialog,DataSetListenerEnd,year,month,day);
            dialog.show();
        });

        DataSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                  String date=month+"/"+dayOfMonth+"/"+year;
                  startDate.setText(date);
            }
        };
        DataSetListenerEnd=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=month+"/"+dayOfMonth+"/"+year;
                endDate.setText(date);
            }
        };
        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                NumberFormat currecny=NumberFormat.getCurrencyInstance();
                currecny.setCurrency(Currency.getInstance("USD"));
                return  currecny.format(value);
            }
        });
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(view->{
            Trip trip=new Trip();
            trip.setTripName(tripName.getText().toString());
            trip.setDestination(destination.getText().toString());

            trip.setPrice((int)slider.getValue());
            if(radioGroup.getCheckedRadioButtonId()==-1)
            {
                Toast.makeText(getApplicationContext(), "Please select a type", Toast.LENGTH_SHORT).show();
            }
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
           RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);
           trip.setTripType(selectedRadioButton.getText().toString());
           trip.setStartDate(startDate.getText().toString());
           trip.setEndDate(endDate.getText().toString());
            trip.setRatingBar((int)ratingBar.getRating());
            Intent intent=new Intent();
           intent.putExtra("trip",trip);
           setResult(MainActivity.RESULT_OK,intent);
            finish();
        });
    }
}
