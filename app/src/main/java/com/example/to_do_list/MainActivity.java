package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.to_do_list.Adapter.todoAdapter;
import com.example.to_do_list.model.todolist;
import com.example.to_do_list.util.database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView taskrecyclerView;
    private todoAdapter taskAdapter;
    private List<todolist>taskList;
    private FloatingActionButton fab;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new database(this);
        db.openDatabase();
        taskList = new ArrayList<>();
        taskrecyclerView = findViewById(R.id.taskrecyclerview);
        taskrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new todoAdapter(this);
        taskrecyclerView.setAdapter(taskAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskrecyclerView);

        fab = findViewById(R.id.plus);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        taskAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtask.newInstance().show(getSupportFragmentManager(), newtask.TAG);
            }
        });
    }





        @Override
        public void handleDialogClose (DialogInterface dialog){
            taskList = db.getAllTasks();
            Collections.reverse(taskList);
            taskAdapter.setTasks(taskList);
            taskAdapter.notifyDataSetChanged();
        }

}