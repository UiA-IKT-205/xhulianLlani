package com.example.todolist.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoList(val name:String):Parcelable{
    var tasks = mutableListOf<String>()
}