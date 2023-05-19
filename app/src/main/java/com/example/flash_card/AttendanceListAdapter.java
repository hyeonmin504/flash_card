package com.example.flash_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AttendanceListAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> attendanceList;

    public AttendanceListAdapter(Context context, List<String> attendanceList) {
        super(context, 0, attendanceList);
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_attendance, parent, false);
        }

        String attendance = attendanceList.get(position);
        String[] attendanceData = attendance.split(" ");

        TextView tvYear = view.findViewById(R.id.tvYear);
        TextView tvMonth = view.findViewById(R.id.tvMonth);
        TextView tvDay = view.findViewById(R.id.tvDay);

        if (attendanceData.length >= 4) {
            String attendanceDate = attendanceData[0] + "년 " + attendanceData[1] + "월 " + attendanceData[2] + "일";
            String attendanceStatus = attendanceData[3]; // 수정된 출석 데이터 형식에서 출석 상태를 가져옴
            String displayText = attendanceDate + " " + attendanceStatus;

            tvYear.setText(displayText);

            // 출석 내용을 표시
            StringBuilder attendanceContent = new StringBuilder();
            for (int i = 4; i < attendanceData.length; i++) {
                attendanceContent.append(attendanceData[i]).append(" ");
            }
            tvMonth.setText(attendanceContent.toString());
            tvDay.setText("");
        }

        return view;
    }
}