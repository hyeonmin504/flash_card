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

public class Statistics extends AppCompatActivity {
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;
    private ListView list_sta, list_sta2;
    List<String> data_sta,data_sta2;
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
        list_sta = (ListView)findViewById(R.id.list_sta);
        list_sta2 = (ListView)findViewById(R.id.list_sta2);

        // dbHelper = new DatabaseHelper(this); // 데이터베이스 헬퍼 객체 생성

        // 데이터베이스에서 데이터 가져오기
        /*List<String> data_sta = getDataFromDatabase();

        // list_sta에 데이터 설정
        adapter_sta = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data_sta);
        list_sta.setAdapter(adapter_sta);*/
                                            // 데이터 리스트 초기화
        data_sta = new ArrayList<>();
        data_sta2 = new ArrayList<>();

        // 데이터베이스에서 데이터 가져오기
        // dataList = dbHelper.getDataList(); // 데이터베이스 헬퍼 클래스에 구현된 메서드를 통해 데이터 가져오기
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data_sta);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data_sta2);
        /*if (data.isEmpty()) {
            // 데이터가 없는 경우 빈 리스트뷰 어댑터 생성
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        } else {
            // 데이터가 있는 경우 데이터 리스트를 어댑터에 설정
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        }*/ // db 추가시 위에 코드와 변경
        list_sta.setAdapter(adapter);
        data_sta.add("데이터베이스"); // 삭제 예정
        data_sta.add("운영체제"); // 삭제 예정
        data_sta.add("알고리즘"); // 삭제 예정
        adapter.notifyDataSetChanged();

        list_sta2.setAdapter(adapter2);
        data_sta2.add("90%"); // 삭제 예정
        data_sta2.add("90%"); // 삭제 예정
        data_sta2.add("80%"); // 삭제 예정
        adapter.notifyDataSetChanged();

        list_sta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSub = data_sta.get(position);

                // 선택된 항목에 해당하는 데이터 가져오기
                /*String selectedData = getDataFromDatabase2(selectedSub);

                // list_sta2에 데이터 설정
                List<String> data_sta2 = new ArrayList<>();
                data_sta2.add(selectedData);
                adapter_sta2 = new ArrayAdapter<>(Statistics.this, android.R.layout.simple_list_item_1, data_sta2);
                list_sta2.setAdapter(adapter_sta2);*/

                // 선택된 항목에 대한 동작을 수행
                Intent intent = new Intent(Statistics.this, Statistics2.class);
                intent.putExtra("selectedSub", selectedSub); // 선택된 항목 데이터를 인텐트에 추가
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
    // 데이터베이스에서 list_sta에 표시될 데이터 가져오기
    /*private List<String> getDataFromDatabase() {
        // 데이터베이스 쿼리 실행
        // 결과를 List<String> 형태로 변환하여 반환
        // 예: "데이터베이스", "운영체제" 등의 데이터를 가져옴
    }

    // 데이터베이스에서 list_sta2에 표시될 데이터 가져오기
    private String getDataFromDatabase2(String selectedSub) {
        // 선택된 항목에 해당하는 데이터를 가져옴
        // 예: "데이터베이스" 항목에 대한 데이터를 가져옴
    }*/
}