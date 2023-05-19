package com.example.flash_card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Attendance extends AppCompatActivity {
    Button btn_attend;
    CalendarView calView;
    TextView tvYear, tvMonth, tvDay;
    TextView tv_ox;
    int selectYear = 0;
    int selectMonth = 0;
    int selectDay = 0;
    Button btn_madecard;
    Button btn_playcard;
    Button btn_review;
    Button btn_statistics;
    Button btn_attendance;

    // 파일 이름과 내용을 정의
    String directoryName = "flash_card";
    String fileName = "attendance.txt";
    String filePath = "C:\\Users\\kim hyeon min\\AppData\\Local\\Google\\AndroidStudio2022.1\\device-explorer\\Pixel_2_API_33 [emulator-5554]\\data\\data\\com.example.flash_card"; // 원하는 파일 경로로 수정

    File file = new File(filePath);

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
        tv_ox = (TextView) findViewById(R.id.tv_ox);

        Calendar calendar = Calendar.getInstance();
        calView.setDate(calendar.getTimeInMillis());

        // 현재 시간을 자동으로 불러와서 텍스트뷰에 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        tvYear.setText(sdf.format(new Date()));
        sdf.applyPattern("MM");
        tvMonth.setText(sdf.format(new Date()));
        sdf.applyPattern("dd");
        tvDay.setText(sdf.format(new Date()));

        // 버튼을 클릭하면 날짜,시간을 가져온다.
        btn_attend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String attendanceStatus = "출석"; // 출석 여부 값, 필요에 따라 동적으로 설정할 수 있음
                tv_ox.setText(attendanceStatus);

                // 날짜 설정
                tvYear.setText(Integer.toString(selectYear));
                tvMonth.setText(Integer.toString(selectMonth));
                tvDay.setText(Integer.toString(selectDay));

                saveAttendanceToFile(attendanceStatus);
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
    }
    private void saveAttendanceToFile(String attendanceStatus) {
        try {
            File directory = new File(getFilesDir(), directoryName);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리가 없으면 생성
            }

            File file = new File(directory, fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(attendanceStatus.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 저장된 출석 여부 파일을 읽어오는 메서드
    private String readAttendanceFromFile() {
        try {
            File directory = new File(getFilesDir(), directoryName);
            File file = new File(directory, fileName);

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}