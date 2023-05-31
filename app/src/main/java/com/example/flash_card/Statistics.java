package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistics extends AppCompatActivity {
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;
    private ListView listView;
    private List<HashMap<String, String>> data;
    FlashCardDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        setTitle("오답 확인");

        btn_madecard = (Button) findViewById(R.id.btn_madecard);
        btn_playcard = (Button) findViewById(R.id.btn_playcard);
        btn_review = (Button) findViewById(R.id.btn_review);
        btn_statistics = (Button) findViewById(R.id.btn_statistics);
        btn_attendance = (Button) findViewById(R.id.btn_attendance);

        listView = (ListView) findViewById(R.id.list);

        dbHelper = new FlashCardDBHelper(this);

        // 데이터 리스트 초기화
        data = new ArrayList<>();

        // 데이터베이스에서 데이터 가져오기
        data = dbHelper.getTwoDataListByRate();

        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, new String[] {"item1", "item2"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = data.get(position);

                // 선택된 항목에 대한 동작을 수행
                Intent intent = new Intent(Statistics.this, Statistics2.class);
                intent.putExtra("selectedItem", selectedItem.get("item1")); // 선택된 항목 데이터를 인텐트에 추가
                startActivity(intent);
            }
        });

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