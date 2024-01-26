package com.example.pitchcatalystassignment.data

data class TodoItem(
    val title: String,
    val body: String,
    val key: String,
    var isChecked: Boolean = false
){
    constructor() : this("", "","")

}