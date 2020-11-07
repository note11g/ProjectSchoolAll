package com.note11.projectschoolall.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.note11.projectschoolall.databinding.RowTodoBinding;
import com.note11.projectschoolall.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private List<TodoModel> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, TodoModel item);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, TodoModel item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setItem(List<TodoModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoHolder(RowTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        TodoModel model = list.get(position);
        holder.bind(model, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TodoHolder extends RecyclerView.ViewHolder {

        private RowTodoBinding binding;

        public TodoHolder(RowTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TodoModel model, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            binding.setTodo(model);
            itemView.setOnClickListener(view -> clickListener.onItemClick(view, model));
            itemView.setOnLongClickListener(view -> longClickListener.onItemLongClick(view, model));
        }

    }
}
