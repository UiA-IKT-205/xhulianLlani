package com.example.todolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.TodoList
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private lateinit var list: TodoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedList = intent.getParcelableExtra<TodoList>(EXTRA_LIST_INFO)

        if(receivedList != null){
            list = receivedList
            Log.i("Details view", receivedList.toString())
        } else{
            finish()
        }

        binding.textViewTaskActivity.text = list.name
        binding.textViewTaskActivity.setOnClickListener{newName()}
        binding.todolistTasks.adapter =TaskAdapter(list)
        binding.addTask.setOnClickListener{newTask()}


    }

    fun newTask(){
        val task = "newTask"
        TaskAdapter(list).updateCollection(task)
    }

    fun newName() {

        val newName = binding.textViewTaskActivity.text.toString()
        if (newName == null) {
            binding.textViewTaskActivity.text = list.name
        } else {
            binding.textViewTaskActivity.text = newName
        }

    }
}