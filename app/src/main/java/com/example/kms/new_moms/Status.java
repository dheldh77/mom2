package com.example.kms.new_moms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Status extends AppCompatActivity {
    private Button button;
    private EditText height;
    private EditText weight;
    private EditText month;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        database = FirebaseDatabase.getInstance();
        height = (EditText) findViewById(R.id.inform_h);
        weight = (EditText) findViewById(R.id.inform_w);
        month = (EditText) findViewById(R.id.inform_mon);
        button = (Button) findViewById(R.id.button3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInform();

            }
        });

    }

    void uploadInform(){
        inform inform= new inform();
        inform.height = height.getText().toString();
        inform.weight = weight.getText().toString();
        inform.month = month.getText().toString();

        database.getReference().child("informs").push().setValue(inform);
    }
}
