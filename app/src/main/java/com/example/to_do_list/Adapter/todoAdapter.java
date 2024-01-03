package com.example.to_do_list.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list.MainActivity;
import com.example.to_do_list.R;
import com.example.to_do_list.model.todolist;
import com.example.to_do_list.newtask;
import com.example.to_do_list.util.database;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.ViewHolder> {
    private List<todolist> todolist;
    private MainActivity activity;
    private database db;
    public todoAdapter(MainActivity activity) {
        this.db = db;
        this.activity = activity;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(ItemView) ;
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        todolist item=todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updatestatus(item.getId(), 1);
                } else {
                    db.updatestatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount(){
        return todolist.size();
    }
    private boolean toBoolean(int n){
        return n!=0;
    }

    public Context getContext() {
        return activity;
    }
    public void setTasks(List<todolist>todolist){
        this.todolist=todolist;
        notifyDataSetChanged();
    }


    public void deleteItem(int position) {
        todolist item = todolist.get(position);
        db.delete(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        todolist item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        newtask fragment = new newtask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), newtask.TAG);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.checkbox);
        }
    }

}
