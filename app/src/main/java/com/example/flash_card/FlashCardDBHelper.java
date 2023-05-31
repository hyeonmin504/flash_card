package com.example.flash_card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlashCardDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flash_card.db";
    private static final int DATABASE_VERSION = 1;
    public static int topicId;
    public static String topicName;
    public int newRowCard = -1;

    public FlashCardDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 SQL문 작성
        // 데이터베이스에 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS Topic (id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Card (id INTEGER PRIMARY KEY AUTOINCREMENT, tid INTEGER NOT NULL, question STRING NOT NULL, answer STRING NOT NULL, FOREIGN KEY (tid) REFERENCES Topic(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Quiz (id INTEGER PRIMARY KEY AUTOINCREMENT, tid INTEGER NOT NULL, cid INTEGER NOT NULL, userAnswer STRING, isCorrect INTEGER, time INTEGER, FOREIGN KEY (tid) REFERENCES Topic(id), FOREIGN KEY (cid) REFERENCES Card(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드가 필요한 경우 실행되는 코드
        // 기존 테이블을 삭제하고 새로운 테이블을 생성하거나, 필요에 따라 데이터를 이관하는 등의 작업을 수행할 수 있습니다.
        db.execSQL("DROP TABLE IF EXISTS Topic");
        db.execSQL("DROP TABLE IF EXISTS Card");
        db.execSQL("DROP TABLE IF EXISTS Quiz");
        onCreate(db);
    }

    public void insertTopic(String _name) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, name FROM Topic WHERE name = ?", new String[]{_name});
        if (cursor.moveToFirst()) {
            topicId = cursor.getInt(0);
            topicName = cursor.getString(1);
            cursor.close();
            db.close();
        }

        else {
            db.execSQL("INSERT INTO Topic (name) VALUES('" + _name + "');");
            topicId = newRowIdTopic();
            topicName = _name;
            db.close();
        }
    }

    public int newRowIdTopic() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT max(id) FROM Topic", null);
        int newRowTopicId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            newRowTopicId = cursor.getInt(0);
            cursor.close();
        }
        db.close();

        return newRowTopicId;
    }

    public void insertCard(String _question, String _answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT question, answer FROM Card WHERE question = ? AND answer = ?", new String[]{_question, _answer});
        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            newRowCard = 0;
        }

        else {
            db.execSQL("INSERT INTO Card (tid, question, answer) VALUES('" + topicId + "','" + _question + "','" + _answer + "');");
            db.close();
            newRowCard = 1;
        }
    }

    public int newRowCard() {
        if (newRowCard == 0) {
            return 0;
        } else if (newRowCard == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    public void insertQuiz(int _tid, int _cid, String _userAnswer, int _isCorrect) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO Quiz (tid, cid, userAnswer, isCorrect, time) VALUES('" + _tid + "','" + _cid + "','" + _userAnswer + "','" + _isCorrect + "','" + (int) (System.currentTimeMillis() / 1000) + "');");
        db.close();
    }

    public List<String> getDataList() {
        List<String> topicNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Topic", new String[]{"name"}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String topicName = cursor.getString(cursor.getColumnIndex("name"));
                topicNames.add(topicName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return topicNames;
    }

    public List<String> getDataListByRate() {
        List<String> topicNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT T.name " +
                        "FROM Topic AS T " +
                        "JOIN ( " +
                        "    SELECT tid, AVG(isCorrect) AS rate " +
                        "    FROM Quiz AS Q " +
                        "    WHERE Q.time = ( " +
                        "        SELECT MAX(time) " +
                        "        FROM Quiz " +
                        "        WHERE cid = Q.cid " +
                        "    ) " +
                        "    GROUP BY tid " +
                        ") AS M ON T.id = M.tid " +
                        "ORDER BY rate", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String topicName = cursor.getString(cursor.getColumnIndex("name"));
                topicNames.add(topicName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return topicNames;
    }


    public List<String> getDataListByTime() {
        List<String> topicNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT T.name " +
                        "FROM Topic AS T " +
                        "JOIN ( " +
                        "    SELECT Q.tid, AVG(Q.time) AS avgTime " +
                        "    FROM Quiz AS Q " +
                        "    WHERE Q.time = ( " +
                        "        SELECT MAX(time) " +
                        "        FROM Quiz " +
                        "        WHERE cid = Q.cid " +
                        "    ) " +
                        "    GROUP BY tid " +
                        ") AS M ON T.id = M.tid " +
                        "ORDER BY avgTime", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String topicName = cursor.getString(cursor.getColumnIndex("name"));
                topicNames.add(topicName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return topicNames;
    }

    public List<HashMap<String, String>> getTwoDataListByRate() {
        List<HashMap<String, String>> dataList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT T.name, rate*100 AS rate " +
                        "FROM Topic AS T " +
                        "JOIN ( " +
                        "    SELECT tid, AVG(isCorrect) AS rate " +
                        "    FROM Quiz AS Q " +
                        "    WHERE Q.time = ( " +
                        "        SELECT MAX(time) " +
                        "        FROM Quiz " +
                        "        WHERE cid = Q.cid " +
                        "    ) " +
                        "    GROUP BY tid " +
                        ") AS M ON T.id = M.tid " +
                        "ORDER BY rate", null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> data = new HashMap<>();
                @SuppressLint("Range") String topicName = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int rateInt = cursor.getInt(cursor.getColumnIndex("rate"));
                String rate = rateInt + "%";

                data.put("item1", topicName);
                data.put("item2", rate);
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dataList;
    }

    public ArrayList<String[]> getWrongCardList(int topicId) {
        // 데이터베이스에서 틀린 카드의 질문과 퀴즈 아이디를 가져옴
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { String.valueOf(topicId) };

        Cursor cursor = db.rawQuery(
                "SELECT q.id, c.question " +
                        "FROM quiz AS q, card AS c " +
                        "WHERE q.cid = c.id " +
                        "AND q.time = (SELECT MAX(time) FROM quiz WHERE cid = q.cid) " +
                        "AND c.tid = ? " +
                        "AND isCorrect = 0", selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));

                String[] row = { String.valueOf(id), question};
                data.add(row);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return data;
    }

    @SuppressLint("Range")
    public int getTopicId(String topicName) {
        int topicId = -1;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = { "id" };
        String selection = "name = ?";
        String[] selectionArgs = { topicName };

        Cursor cursor = db.query("Topic", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            topicId = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }

        db.close();
        return topicId;
    }


    public ArrayList<String[]> getDataFromDatabase(int topicId) {
        // 데이터베이스에서 일치하는 데이터를 랜덤하게 가져오는 로직 작성
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { String.valueOf(topicId) };

        Cursor cursor = db.rawQuery("SELECT id, question, answer FROM Card WHERE tid = ? ORDER BY RANDOM()", selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                String[] row = { String.valueOf(id), question, answer };
                data.add(row);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return data;
    }

    public ArrayList<String[]> getDataFromDatabaseByRate(int topicId) {
        // 데이터베이스에서 최근에 푼 cid 데이터에 따라, 틀렸던 카드 먼저 가져오는 로직
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { String.valueOf(topicId) };

        Cursor cursor = db.rawQuery("SELECT c.id, question, answer " +
                "FROM Quiz AS q, Card AS c " +
                "WHERE q.cid = c.id " +
                "AND q.time = (SELECT MAX(time) FROM Quiz WHERE cid = q.cid) " +
                "AND c.tid = ? " +
                "ORDER BY isCorrect ASC", selectionArgs);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                String[] row = { String.valueOf(id), question, answer };
                data.add(row);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return data;
    }

    public ArrayList<String[]> getDataFromDatabaseByTime(int topicId) {
        // 데이터베이스에서 최근에 푼 cid 데이터에 따라, 오래된 카드 먼저 가져오는 로직
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { String.valueOf(topicId) };

        Cursor cursor = db.rawQuery("SELECT c.tid, c.id, question, answer " +
                "FROM Quiz AS q, Card AS c " +
                "WHERE q.cid = c.id " +
                "AND q.time = (SELECT MAX(time) FROM Quiz WHERE cid = q.cid) " +
                "AND c.tid = ? " +
                "ORDER BY time", selectionArgs);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                String[] row = { String.valueOf(id), question, answer };
                data.add(row);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return data;
    }

    public ArrayList<String[]> getOneFromDatabase(String quizId) {
        ArrayList<String[]> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = { quizId };

        Cursor cursor = db.rawQuery(
                "SELECT t.name, c.question, q.userAnswer, c.answer " +
                        "FROM quiz AS q, topic AS t, card AS c " +
                        "WHERE q.tid = t.id AND q.cid = c.id " +
                        "AND q.id = ?", selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String topicName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String userAnswer = cursor.getString(cursor.getColumnIndexOrThrow("userAnswer"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                String[] row = { topicName, question, userAnswer, answer };
                data.add(row);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return data;
    }
}
