package com.example.todolist.data

class TodoListManager {
    private lateinit var todolistCollection: MutableList<TodoList>

    var onLists: ((List<TodoList>) -> Unit)? = null
    var onListUpdate: ((list: TodoList) -> Unit)? = null

    fun load(){
        todolistCollection = mutableListOf(
            TodoList("Liste 1"),
            TodoList("Liste 2"),
            TodoList("Liste 3")
        )
        onLists?.invoke(todolistCollection)
    }

    fun updateList(list: TodoList){
        onListUpdate?.invoke(list)
    }

    fun addList(list: TodoList){
        todolistCollection.add(list)
        onLists?.invoke(todolistCollection)
    }

    companion object{
        val instance = TodoListManager()
    }
}