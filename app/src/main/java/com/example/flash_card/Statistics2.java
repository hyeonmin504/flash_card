package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Statistics2 extends AppCompatActivity {
    TextView tv_sub;
    Button btn_back;
    ListView listView;
    List<String[]> data;
    Intent intent;
    String selectedItem;
    int topicId;
    FlashCardDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics2);

        tv_sub = (TextView) findViewById(R.id.tv_sub);
        btn_back = (Button) findViewById(R.id.btn_back);
        listView = (ListView) findViewById(R.id.wrong_answer);

        intent = getIntent();
        selectedItem = intent.getStringExtra("selectedItem");

        dbHelper = new FlashCardDBHelper(this);

        topicId = dbHelper.getTopicId(selectedItem);
        tv_sub.setText(selectedItem);

        data = new ArrayList<>();
        data = dbHelper.getWrongCardList(topicId);

        ArrayList<String> questionList = new ArrayList<>();
        for (String[] row : data) {
            questionList.add(row[1]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuestion = data.get(position)[0];
                // 선택된 항목에 대한 동작을 수행
                Intent intent = new Intent(Statistics2.this, StatisticsCard.class);
                intent.putExtra("selectedQuestion", selectedQuestion); // 선택된 항목 데이터를 인텐트에 추가
                startActivity(intent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistics2.this, Statistics.class);
                startActivity(intent);
            }
        });
    }
}
