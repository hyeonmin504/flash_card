package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Statistics extends AppCompatActivity {
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        setTitle("오답 확인");
        
        btn_madecard = (Button)findViewById(R.id.btn_madecard);
        btn_playcard = (Button)findViewById(R.id.btn_playcard);
        btn_review = (Button)findViewById(R.id.btn_review);
        btn_statistics = (Button)findViewById(R.id.btn_statistics);
        btn_attendance =(Button)findViewById(R.id.btn_attendance);

        btn_madecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Made_card.class);
                startActivity(intent);
            }
        });
        btn_playcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Play_card.class);
                startActivity(intent);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Review.class);
                startActivity(intent);
            }
        });
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Statistics.class);
                startActivity(intent);
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics.this, Attendance.class);
                startActivity(intent);
            }
        });
    }
}