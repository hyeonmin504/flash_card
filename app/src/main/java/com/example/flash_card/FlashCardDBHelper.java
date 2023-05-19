package com.example.flash_card;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlashCardDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flash_card.db";
    private static final int DATABASE_VERSION = 1;

    public FlashCardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 SQL문 작성
        String createTableQuery = "CREATE TABLE flashcards ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "sub TEXT,"
                + "question TEXT,"
                + "answer TEXT"
                + ");";

        // 데이터베이스에 테이블 생성
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드가 필요한 경우 실행되는 코드
        // 기존 테이블을 삭제하고 새로운 테이블을 생성하거나, 필요에 따라 데이터를 이관하는 등의 작업을 수행할 수 있습니다.
        db.execSQL("DROP TABLE IF EXISTS flashcards");
        onCreate(db);
    }

    public long insertFlashCard(String sub, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sub", sub);
        values.put("question", question);
        values.put("answer", answer);

        long newRowId = db.insert("flashcards", null, values);
        db.close();

        return newRowId;
    }
}