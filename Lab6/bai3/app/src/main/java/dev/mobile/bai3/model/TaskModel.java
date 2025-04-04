package dev.mobile.bai3.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TaskModel extends SQLiteOpenHelper {
    public TaskModel(Context context) {
        super(context, "task.db", null, 1);
    }
    public TaskModel(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS task(" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "location TEXT," +
                "date TEXT," +
                "state INTEGER)";
        db.execSQL(sql);
//    set auto increament when insert and delete
        sql = "CREATE TRIGGER IF NOT EXISTS task_ai AFTER INSERT ON task BEGIN " +
                "UPDATE task SET id = (SELECT MAX(id) FROM task) WHERE id = 0;" +
                "END";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS task";
        db.execSQL(sql);
        onCreate(db);
    }

    public void add(String title, String location, String date, boolean state) {
        String sql = "INSERT INTO task(title, location, date, state) VALUES(?, ?, ?, ?)";
        int stateInt = state ? 1 : 0;
        getWritableDatabase().execSQL(sql, new String[]{title, location, date, String.valueOf(stateInt)});
    }

    public void update(int id, String title, String location, String date, int state) {
        String sql = "UPDATE task SET title = ?, location = ?, date = ?, state = ? WHERE id = ?";
        getWritableDatabase().execSQL(sql, new String[]{title, location, date, String.valueOf(state), String.valueOf(id)});
    }

    public void delete(int id) {
        String sql = "DELETE FROM task WHERE id = ?";
        getWritableDatabase().execSQL(sql, new String[]{String.valueOf(id)});
    }

    public ArrayList<Task> getAll() {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String location = cursor.getString(2);
            String date = cursor.getString(3);
            boolean state = cursor.getInt(4) > 0;
            tasks.add(new Task(id, title, location, date, state ));
        }
        return tasks;
    }

    public Task get(int id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        Cursor cursor = getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            String title = cursor.getString(1);
            String location = cursor.getString(2);
            String date = cursor.getString(3);
            boolean state = cursor.getInt(4) > 0;
            return new Task(id, title, location, date, state);
        }
        return null;
    }

    public ArrayList<Task> get(boolean state) {
        String sql = "SELECT * FROM task WHERE state = ?";
        int stateInt = state ? 1 : 0;
        Cursor cursor = getWritableDatabase().rawQuery(sql, new String[]{String.valueOf(stateInt)});
        Log.d("CONTENT", "get: " + stateInt);
        ArrayList<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String location = cursor.getString(2);
            String date = cursor.getString(3);
            tasks.add(new Task(id, title, location, date, state));
        }
        return tasks;
    }

    public void clear() {
        String sql = "DELETE FROM task";
        getWritableDatabase().execSQL(sql);
    }

    public void close() {
        getWritableDatabase().close();
    }
}

