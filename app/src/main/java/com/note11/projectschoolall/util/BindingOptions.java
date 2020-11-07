package com.note11.projectschoolall.util;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.note11.projectschoolall.model.TodoModel;

public class BindingOptions {

    @BindingAdapter("todo")
    public static void bindTodoItem(RecyclerView recyclerView, ObservableArrayList<TodoModel> items){
        TodoAdapter adapter = (TodoAdapter) recyclerView.getAdapter();
        if(adapter!=null) adapter.setItem(items);

    }
}
