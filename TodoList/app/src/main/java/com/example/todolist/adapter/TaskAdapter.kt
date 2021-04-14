package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TaskItemBinding

class TaskAdapter(private var todoList: TodoList) : RecyclerView.Adapter<TaskAdapter.ViewHolder>(){

    class ViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(task: String) {
            binding.textViewTask.text = task
        }
    }

    override fun getItemCount(): Int = todoList.tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = todoList.tasks[position]
        holder.bind(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newTask:String){
        todoList.tasks.add(newTask)
        notifyDataSetChanged()
    }


}