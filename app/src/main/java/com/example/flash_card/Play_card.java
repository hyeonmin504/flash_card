package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Play_card extends AppCompatActivity {
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;
    private ListView list;
    List<String> data;
    // DatabaseHelper dbHelper; // 데이터베이스 헬퍼 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_card);

        setTitle("퀴즈 게임");

        btn_madecard = (Button)findViewById(R.id.btn_madecard);
        btn_playcard = (Button)findViewById(R.id.btn_playcard);
        btn_review = (Button)findViewById(R.id.btn_review);
        btn_statistics = (Button)findViewById(R.id.btn_statistics);
        btn_attendance =(Button)findViewById(R.id.btn_attendance);

        list = (ListView)findViewById(R.id.list);

        // dbHelper = new DatabaseHelper(this); // 데이터베이스 헬퍼 객체 생성

        // 데이터 리스트 초기화
        data = new ArrayList<>();

        // 데이터베이스에서 데이터 가져오기
        // dataList = dbHelper.getDataList(); // 데이터베이스 헬퍼 클래스에 구현된 메서드를 통해 데이터 가져오기

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        /*if (data.isEmpty()) {
            // 데이터가 없는 경우 빈 리스트뷰 어댑터 생성
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        } else {
            // 데이터가 있는 경우 데이터 리스트를 어댑터에 설정
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        }*/ // db 추가시 위에 코드와 변경
        list.setAdapter(adapter);

        data.add("주제 예시 1"); // 삭제 예정
        data.add("주제 예시 2"); // 삭제 예정
        data.add("주제 예시 3"); // 삭제 예정
        adapter.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = data.get(position);
                // 선택된 항목에 대한 동작을 수행
                Intent intent = new Intent(Play_card.this, Card.class);
                intent.putExtra("selectedItem", selectedItem); // 선택된 항목 데이터를 인텐트에 추가
                startActivity(intent);
            }
        });

        btn_madecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Play_card.this, Made_card.class);
                startActivity(intent);
            }
        });
        btn_playcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Play_card.this, Play_card.class);
                startActivity(intent);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Play_card.this, Review.class);
                startActivity(intent);
            }
        });
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Play_card.this, Statistics.class);
                startActivity(intent);
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Play_card.this, Attendance.class);
                startActivity(intent);
            }
        });
    }
}