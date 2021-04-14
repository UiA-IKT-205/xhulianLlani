package com.example.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TodoListAdapter
import com.example.todolist.data.TodoList
import com.example.todolist.data.TodoListManager
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_LIST_INFO: String = "com.example.todolist.info"
const val REQUEST_TASK_DETAILS:Int = 564567

// May be Task
class ListHolder{

    companion object{
        var PickedList:TodoList? = null
    }


}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todolistListing.layoutManager = LinearLayoutManager(this)
        binding.todolistListing.adapter = TodoListAdapter(emptyList<TodoList>(), this::onListClicked)


        TodoListManager.instance.onLists = {
            (binding.todolistListing.adapter as TodoListAdapter).updateCollection(it)
        }

        TodoListManager.instance.load()

        binding.addList.setOnClickListener{
            addList("newTask")
/*
            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
 */
        }
    }

    private fun addList(name: String) {
        val default = "untitled"
        val list = TodoList(name)
        TodoListManager.instance.addList(list)

    }

    private fun onListClicked(list: TodoList): Unit {


        val intent = Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_LIST_INFO, list)
        }

        startActivity(intent)
        //startActivityForResult(intent, REQUEST_TASK_DETAILS)
    }

}