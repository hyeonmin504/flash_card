package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Card extends AppCompatActivity {
    TextView textView;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        setTitle("카드");

        textView = (TextView)findViewById(R.id.textView);
        btn_back = (Button)findViewById(R.id.btn_back);

        Intent intent = getIntent();
        String selectedItem = intent.getStringExtra("selectedItem");
        // 선택된 항목 데이터를 사용하여 필요한 동작 수행
        // 예를 들어, 선택된 항목 데이터를 TextView에 설정하는 경우:
        textView.setText(selectedItem);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}