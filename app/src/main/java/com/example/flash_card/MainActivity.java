package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_madecard;
    ImageButton btn_playcard;
    ImageButton btn_review;
    ImageButton btn_statistics;
    ImageButton btn_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_madecard = findViewById(R.id.btn_madecard);
        btn_playcard = findViewById(R.id.btn_playcard);
        btn_review = findViewById(R.id.btn_review);
        btn_statistics = findViewById(R.id.btn_statistics);
        btn_attendance = findViewById(R.id.btn_attendance);

        btn_madecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Made_card.class);
                startActivity(intent);
            }
        });
        btn_playcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Play_card.class);
                startActivity(intent);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Review.class);
                startActivity(intent);
            }
        });
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Statistics.class);
                startActivity(intent);
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Attendance.class);
                startActivity(intent);
            }
        });
    }
}
