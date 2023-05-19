package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Attendance extends AppCompatActivity {
    Button btn_attend;
    CalendarView calView;
    TextView tvYear, tvMonth, tvDay;
    int selectYear = 0;
    int selectMonth = 0;
    int selectDay = 0;
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;
    ListView attendanceListView;

    // 파일 이름과 내용을 정의
    String directoryName = "flash_card";
    String fileName = "attendance.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("출석 체크");

        btn_madecard = (Button)findViewById(R.id.btn_madecard);
        btn_playcard = (Button)findViewById(R.id.btn_playcard);
        btn_review = (Button)findViewById(R.id.btn_review);
        btn_statistics = (Button)findViewById(R.id.btn_statistics);
        btn_attendance =(Button)findViewById(R.id.btn_attendance);
        btn_attend = (Button) findViewById(R.id.btn_attend);
        calView = (CalendarView) findViewById(R.id.calendarView1);
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        attendanceListView = (ListView) findViewById(R.id.attendanceListView);

        Calendar calendar = Calendar.getInstance();
        calView.setDate(calendar.getTimeInMillis());

        // 현재 시간을 자동으로 불러와서 텍스트뷰에 설정
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy", Locale.getDefault());
        tvYear.setText(sdfYear.format(calendar.getTime()));
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM", Locale.getDefault());
        tvMonth.setText(sdfMonth.format(calendar.getTime()));
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd", Locale.getDefault());
        tvDay.setText(sdfDay.format(calendar.getTime()));

        // 버튼을 클릭하면 날짜,시간을 가져온다.
        btn_attend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String attendanceStatus = "출석완료"; // 출석 여부 값, 필요에 따라 동적으로 설정할 수 있음

                // 날짜 설정
                tvYear.setText(Integer.toString(selectYear));
                tvMonth.setText(Integer.toString(selectMonth));
                tvDay.setText(Integer.toString(selectDay));

                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectYear, selectMonth, selectDay);
                Log.d("Selected Date", selectedDate);

                saveAttendanceToFile(attendanceStatus);
                Toast.makeText(Attendance.this, "출석완료", Toast.LENGTH_SHORT).show();

            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectYear =  year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;
            }
        });

        btn_madecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendance.this, Made_card.class);
                startActivity(intent);
            }
        });
        btn_playcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendance.this, Play_card.class);
                startActivity(intent);
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendance.this, Review.class);
                startActivity(intent);
            }
        });
        btn_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendance.this, Statistics.class);
                startActivity(intent);
            }
        });
        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Attendance.this, Attendance.class);
                startActivity(intent);
            }
        });

        // 저장된 출석 내용을 ListView에 표시
        List<String> attendanceList = readAttendanceFromFile();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attendanceList);
        attendanceListView.setAdapter(adapter);
    }

    private boolean isAttendanceAlreadyExist(String date) {
        List<String> attendanceList = readAttendanceFromFile();
        for (String attendance : attendanceList) {
            String[] attendanceData = attendance.split(" ");
            if (attendanceData.length >= 2) {
                String attendanceDate = attendanceData[0];
                if (attendanceDate.equals(date)) {
                    return true; // 이미 해당 날짜에 출석한 기록이 있는 경우
                }
            }
        }
        return false; // 해당 날짜에 출석한 기록이 없는 경우
    }

    private void saveAttendanceToFile(String attendanceStatus) {
        try {
            File directory = new File(getFilesDir(), directoryName);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리가 없으면 생성
            }

            if (selectYear == 0 || selectMonth == 0 || selectDay == 0) {
                Toast.makeText(this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            String date = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                    selectYear, selectMonth, selectDay);

            if (isAttendanceAlreadyExist(date)) {
                // 이미 해당 날짜에 출석된 경우, 중복 출석 방지 처리
                String message = date + " 이미 출석 되었습니다.";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                return;
            }

            File file = new File(directory, fileName);

            FileOutputStream fos = new FileOutputStream(file, true); // append 모드로 파일 열기
            String entry = date + " " + attendanceStatus + "\n"; // 날짜와 출석 여부를 결합한 문자열 생성
            fos.write(entry.getBytes());
            fos.close();
            // 로그를 통해 파일 저장 위치 확인
            String filePath = file.getAbsolutePath();
            Log.d("File Location", "Attendance file saved at: " + filePath);

            // 저장된 출석 내용을 ListView에 표시
            List<String> attendanceList = readAttendanceFromFile();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attendanceList);
            attendanceListView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 저장된 출석 여부 파일을 읽어오는 메서드
    private List<String> readAttendanceFromFile() {
        List<String> attendanceList = new ArrayList<>();
        try {
            File directory = new File(getFilesDir(), directoryName);
            File file = new File(directory, fileName);

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                attendanceList.add(line); // 각 줄을 리스트에 추가
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // attendanceList 로그로 출력
        for (String attendance : attendanceList) {
            Log.d("Attendance", attendance);
        }
        return attendanceList;
    }
}