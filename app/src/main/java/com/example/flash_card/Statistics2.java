package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    Button bt_back;
    ListView wrongAnswerListView;
    //DBHelper dbHelper;
    List<String> data_wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics2);

        tv_sub = (TextView) findViewById(R.id.tv_sub);
        bt_back = (Button) findViewById(R.id.bt_back);
        wrongAnswerListView = (ListView) findViewById(R.id.wrong_answer);

        //아래 문단 삭제 예정 예시임
        data_wrong = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data_wrong);
        wrongAnswerListView.setAdapter(adapter);
        data_wrong.add("틀린 문제 1"); // 삭제 예정
        data_wrong.add("틀린 문제 2"); // 삭제 예정
        data_wrong.add("틀린 문제 3"); // 삭제 예정
        adapter.notifyDataSetChanged();

        //dbHelper = new DBHelper(this);
        //SQLiteDatabase db = dbHelper.getReadableDatabase();

        wrongAnswerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = data_wrong.get(position);
                // 선택된 항목에 대한 동작을 수행
                Intent intent = new Intent(Statistics2.this, Card.class);
                intent.putExtra("selectedItem", selectedItem); // 선택된 항목 데이터를 인텐트에 추가
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String selectedSub = intent.getStringExtra("selectedSub");
        tv_sub.setText(selectedSub);

        // 데이터베이스에서 선택된 과목에 대한 정보를 가져온 후 ListView에 표시
        /*Cursor cursor = db.rawQuery("SELECT * FROM your_table_name WHERE subject = ?", new String[]{selectedSub});
        if (cursor != null && cursor.moveToFirst()) {
            // 데이터를 담을 리스트 생성
            ArrayList<String> wrongAnswers = new ArrayList<>();
            do {
                // 필요한 컬럼 값 가져와서 리스트에 추가
                String wrongAnswer = cursor.getString(cursor.getColumnIndex("wrong_answer_column_name"));
                wrongAnswers.add(wrongAnswer);
            } while (cursor.moveToNext());

            // ArrayAdapter를 사용하여 ListView에 데이터 설정
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wrongAnswers);
            wrongAnswerListView.setAdapter(adapter);
        }

        cursor.close();
        db.close();*/

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}