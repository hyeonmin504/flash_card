package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewCard extends AppCompatActivity {
    TextView textView;
    TextView tv_quest;
    EditText et_answer;
    TextView tv_answer;
    Button btn_back;
    Button btn_next;
    CardView cardView;
    private LinearLayout frontCardView;
    private LinearLayout backCardView;
    private AnimatorSet flipCardAnimator;
    private boolean isFrontSide = true;
    private FlashCardDBHelper dbHelper;
    private int currentIndex = 0;
    Intent intent;
    String selectedItem;
    int topicId;
    int cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        setTitle("카드");

        cardView = findViewById(R.id.cardView);
        frontCardView = findViewById(R.id.frontCardView);
        backCardView = findViewById(R.id.backCardView);

        // 애니메이션 로드
        flipCardAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip);

        textView = findViewById(R.id.textView);
        tv_quest = findViewById(R.id.tv_quest); // 문제
        et_answer = findViewById(R.id.et_answer); // 내 정답
        tv_answer = findViewById(R.id.tv_answer); // 진짜 정답
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setEnabled(false);

        dbHelper = new FlashCardDBHelper(this);

        intent = getIntent();
        selectedItem = intent.getStringExtra("selectedItem");
        topicId = dbHelper.getTopicId(selectedItem);

        textView.setText(selectedItem);
        // 문제와 정답을 가져와서 해당 뷰에 설정
        ArrayList<String[]> data = dbHelper.getDataFromDatabaseByRate(topicId);// 데이터베이스에서 데이터 가져오기

        if (!data.isEmpty()) {
            // 데이터가 있는 경우
            currentIndex = 0;
            String[] row = data.get(currentIndex); // 선택된 랜덤한 데이터 행

            cardId = Integer.parseInt(row[0]);
            String question = row[1]; // 인덱스 1은 question
            String answer = row[2]; // 인덱스 2는 answer

            tv_quest.setText(question);
            tv_answer.setText(answer);
        } else {
            // 데이터가 없는 경우 처리
            // 예를 들어, 빈 문제와 정답을 보여줄 수 있습니다.
            tv_quest.setText("카드가 없습니다");
            tv_answer.setText("");
            et_answer.setVisibility(View.GONE);

        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_next.setEnabled(true);

                if (tv_answer.getText().toString().isEmpty()) {
                    Toast.makeText(ReviewCard.this, "카드를 생성하세요", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(false);
                    return;
                }
                if (et_answer.getText().toString().isEmpty()) {
                    Toast.makeText(ReviewCard.this, "답을 입력하세요", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(false);
                    return;
                }

                if (isFrontSide) {
                    flipCardAnimator.setTarget(cardView); // 카드뷰에 애니메이션 적용
                    flipCardAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    flipCardAnimator.start();
                    isFrontSide = false;
                    frontCardView.setVisibility(View.GONE); // 앞면 숨김
                    backCardView.setVisibility(View.VISIBLE); // 뒷면 표시

                    // 정답 비교
                    String userAnswer = et_answer.getText().toString();
                    String correctAnswer = tv_answer.getText().toString();

                    if (userAnswer.equals(correctAnswer)) {
                        Toast.makeText(ReviewCard.this, "맞혔습니다!", Toast.LENGTH_SHORT).show();
                        dbHelper.insertQuiz(topicId, cardId, userAnswer, 1);
                    } else {
                        Toast.makeText(ReviewCard.this, "틀렸습니다!", Toast.LENGTH_SHORT).show();
                        dbHelper.insertQuiz(topicId, cardId, userAnswer, 0);
                    }
                }
                if (currentIndex == data.size() - 1) {
                    Toast.makeText(ReviewCard.this, "문제를 다 풀었습니다", Toast.LENGTH_SHORT).show();
                    btn_next.setEnabled(false);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewCard.this, Review.class);
                startActivity(intent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.isEmpty()) {
                    currentIndex++;
                    if (currentIndex >= data.size()) {
                        currentIndex = 0;
                    }

                    String[] row = data.get(currentIndex);
                    cardId = Integer.parseInt(row[0]);
                    String question = row[1];
                    String answer = row[2];

                    et_answer.setText("");
                    tv_quest.setText(question);
                    tv_answer.setText(answer);

                    isFrontSide = true;
                    btn_next.setEnabled(false);
                    frontCardView.setVisibility(View.VISIBLE);
                    backCardView.setVisibility(View.GONE);
                }
            }
        });

    }
}