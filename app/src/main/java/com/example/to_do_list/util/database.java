package com.example.to_do_list.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_do_list.model.todolist;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String NAME="todolist";
    private static final String Todo_table="todo";
    private static final String ID="id";
    private static final String TASK="task";
    private static final String STATUS="status";
    private static final String CREATE_TODO_TABLE="CREATE TABLE "+ Todo_table + "(" + ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                                            + TASK +"TEXT, "+ STATUS +" INTEGER)";
    private SQLiteDatabase db;
    public database(Context context){
        super(context , NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Todo_table);
        onCreate(db);
    }

    public void openDatabase(){
        db=this.getWritableDatabase();
    }
    public void insertTask(todolist  task ){
        ContentValues cv = new ContentValues();
        cv.put(TASK,task.getTask());
        cv.put(STATUS,0);
        db.insert(Todo_table,null,cv);
    }
    public List<todolist> getAllTasks(){
        List<todolist> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(Todo_table, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        todolist task = new todolist();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }
    public void updatestatus(int id,int status){
        ContentValues cv=new ContentValues();
        cv.put(STATUS,status);
        db.update(Todo_table,cv,ID+ "=?",new String[]{String.valueOf(id)});
    }
    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(Todo_table, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void delete(int id){

        db.delete(Todo_table,ID+ "=?",new String[]{String.valueOf(id)});
    }

}
