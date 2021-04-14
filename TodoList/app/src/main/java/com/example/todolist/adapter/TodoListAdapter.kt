package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TodoList
import com.example.todolist.databinding.TodolistLayoutBinding

class TodoListAdapter(private var todoList:List<TodoList>,private val onListClicked:(TodoList) -> Unit) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>(){

    class ViewHolder(val binding:TodolistLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: TodoList, onListClicked: (TodoList) -> Unit) {
            binding.itemTitle.text = list.name

            binding.cardView.setOnClickListener{
                onListClicked(list)
            }

        }
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = todoList[position]
        holder.bind(list,onListClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodolistLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newTodoList:List<TodoList>){
        todoList = newTodoList
        notifyDataSetChanged()
    }


}