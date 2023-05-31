package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Made_card extends AppCompatActivity {

    EditText et_sub; // 주제
    Button btn_sel;  // 선택 버튼
    EditText et_quest;
    EditText et_answer;
    TextView view1;
    LinearLayout view2;
    LinearLayout view3;
    Button btn_submit;
    Button btn_restart;
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;
    int topicId;
    String topicName;

    FlashCardDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_card);

        setTitle("퀴즈 만들기");

        et_sub = findViewById(R.id.et_sub);
        btn_sel = (Button)findViewById(R.id.btn_sel);
        et_quest = findViewById(R.id.et_quest);
        et_answer = findViewById(R.id.et_answer);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_restart = (Button) findViewById(R.id.btn_restart);
        btn_madecard = (Button)findViewById(R.id.btn_madecard);
        btn_playcard = (Button)findViewById(R.id.btn_playcard);
        btn_review = (Button)findViewById(R.id.btn_review);
        btn_statistics = (Button)findViewById(R.id.btn_statistics);
        btn_attendance =(Button)findViewById(R.id.btn_attendance);

        dbHelper = new FlashCardDBHelper(this);

        btn_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subText = et_sub.getText().toString();
                if (subText.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "주제를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.insertTopic(subText);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_restart.setVisibility(View.VISIBLE);
                    et_sub.setEnabled(false);
                    btn_sel.setEnabled(false);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String question = et_quest.getText().toString();
                String answer = et_answer.getText().toString();

                if (question.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "질문을 입력하세요", Toast.LENGTH_SHORT).show();
                } else if (answer.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "정답을 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    // SQLite 데이터베이스에 데이터 저장
                    FlashCardDBHelper dbHelper = new FlashCardDBHelper(Made_card.this);
                    dbHelper.insertCard(question, answer);
                    int newRowCard = dbHelper.newRowCard();

                    if (newRowCard == -1) {
                        Toast.makeText(getApplicationContext(),
                                "데이터 저장 실패", Toast.LENGTH_SHORT).show();
                    } else if (newRowCard == 0) {
                        Toast.makeText(getApplicationContext(),
                                "이미 있는 값입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "데이터 저장 성공", Toast.LENGTH_SHORT).show();
                        et_quest.setEnabled(false);
                        et_answer.setEnabled(false);
                        btn_submit.setEnabled(false);
                    }
                }
            }
        });

        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Made_card.class);
                startActivity(intent);
            }
        });

        btn_madecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Made_card.class);
                startActivity(intent);
            }
        });
        btn_playcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Play_card.class);
                startActivity(intent);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Review.class);
                startActivity(intent);
            }
        });
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Statistics.class);
                startActivity(intent);
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Made_card.this, Attendance.class);
                startActivity(intent);
            }
        });
    }
}