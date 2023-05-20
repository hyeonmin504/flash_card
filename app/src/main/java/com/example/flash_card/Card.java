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

public class Card extends AppCompatActivity {
    TextView textView;
    TextView tv_quest;
    EditText et_answer;
    TextView tv_answer;
    Button btn_back;
    CardView cardView;
    private LinearLayout frontCardView;
    private LinearLayout backCardView;
    private AnimatorSet flipCardAnimator;
    private boolean isFrontSide = true;

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

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFrontSide) {
                    flipCardAnimator.setTarget(cardView); // 카드뷰에 애니메이션 적용
                    flipCardAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    flipCardAnimator.start();
                    isFrontSide = false;
                    frontCardView.setVisibility(View.GONE); // 앞면 숨김
                    backCardView.setVisibility(View.VISIBLE); // 뒷면 표시

                    // 정답 비교ㅇ
                    String userAnswer = et_answer.getText().toString();
                    String correctAnswer = tv_answer.getText().toString();

                    if (userAnswer.equals(correctAnswer)) {
                        Toast.makeText(Card.this, "맞았습니다!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Card.this, "틀렸습니다!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    flipCardAnimator.setTarget(cardView); // 카드뷰에 애니메이션 적용
                    flipCardAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    flipCardAnimator.start();
                    isFrontSide = true;
                    frontCardView.setVisibility(View.VISIBLE); // 앞면 표시
                    backCardView.setVisibility(View.GONE); // 뒷면 숨김
                }
            }
        });

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        // 선택된 항목 데이터를 사용하여 필요한 동작 수행
        // 예를 들어, 선택된 항목 데이터를 TextView에 설정하는 경우:
        textView.setText(selectedItem);

        // 문제와 정답을 가져와서 해당 뷰에 설정
        String question = getQuestionFromDatabase(); // 데이터베이스에서 문제 가져오기
        String answer = getAnswerFromDatabase(); // 데이터베이스에서 정답 가져오기

        tv_quest.setText(question);
        tv_answer.setText(answer);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getQuestionFromDatabase() {
        // 데이터베이스에서 문제를 가져오는 로직 작성
        // 가져온 문제를 문자열로 반환
        return "문제를 가져온 내용";
    }

    private String getAnswerFromDatabase() {
        // 데이터베이스에서 정답을 가져오는 로직 작성
        // 가져온 정답을 문자열로 반환
        return "answer";
    }
}