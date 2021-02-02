package com.example.holiday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddUser  extends AppCompatActivity {
    EditText UserName;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
       UserName = (EditText) findViewById(R.id.NameInput);
        EditText Email = (EditText) findViewById(R.id.emailInput);
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(view->{
            Intent intent=new Intent();
            intent.putExtra("name",UserName.getText().toString());
            intent.putExtra("email",Email.getText().toString());
            setResult(MainActivity.RESULT_OK,intent);
            finish();
        });

    }
}
