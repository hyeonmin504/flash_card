package com.example.flash_card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class StatisticsCard extends AppCompatActivity {
    TextView textView;
    TextView tv_quest;
    TextView tv_user_answer;
    TextView tv_answer;
    Button btn_back;
    CardView cardView;

    private FlashCardDBHelper dbHelper;
    Intent intent;
    String selectedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_card);

        setTitle("카드");

        cardView = findViewById(R.id.cardView);

        textView = findViewById(R.id.textView);
        tv_quest = findViewById(R.id.tv_quest); // 문제
        tv_user_answer = findViewById(R.id.tv_user_answer); // 내 정답
        tv_answer = findViewById(R.id.tv_answer); // 진짜 정답
        btn_back = findViewById(R.id.btn_back);

        dbHelper = new FlashCardDBHelper(this);

        intent = getIntent();
        selectedCard = intent.getStringExtra("selectedQuestion");

        ArrayList<String[]> data = dbHelper.getOneFromDatabase(selectedCard); // 데이터베이스에서 데이터 가져오기

        if (!data.isEmpty()) {
            // 데이터가 있는 경우
            String[] row = data.get(0);

            String topic = row[0];
            String question = row[1];
            String userAnswer = row[2];
            String answer = row[3];

            textView.setText(topic);
            tv_quest.setText(question);
            tv_user_answer.setText(userAnswer);
            tv_answer.setText(answer);

        } else {
            // 데이터가 없는 경우 처리
            // 예를 들어, 빈 문제와 정답을 보여줄 수 있습니다.
            textView.setText("");
            tv_quest.setText("카드가 없습니다");
            tv_user_answer.setText("");
            tv_answer.setText("");
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
