package com.example.pitchcatalystassignment.viewmodel

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pitchcatalystassignment.adapter.ListAdapter
import com.example.pitchcatalystassignment.data.TodoItem
import com.example.pitchcatalystassignment.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewmodel @Inject constructor(): ViewModel() {

    fun showDataFromFirebase(databaseReference: DatabaseReference?, toDoList: MutableList<TodoItem>, binding: ActivityMainBinding, context: Context) {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                toDoList.clear()

                if (snapshot.hasChildren()){
                    binding.textView.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    for (childSnapShot in snapshot.children) {
                        val toDo = childSnapShot.getValue(TodoItem::class.java)
                        toDo?.let {
                            toDoList.add(it)
                        }
                    }
                    binding.recyclerView.adapter = ListAdapter(context, toDoList)
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)

                }else{
                    binding.recyclerView.visibility = View.INVISIBLE
                    binding.textView.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addDataToFirebase(context: Context, title: String, body: String, key: String, ref: DatabaseReference?) {
        val todoItem = TodoItem(title, body, key)
        ref?.setValue(todoItem)
        Toast.makeText(context, "Data added!", Toast.LENGTH_SHORT).show()
    }

    fun deleteDataFromFirebase(context: Context, todoItem: TodoItem, databaseReference: DatabaseReference?) {
        val ref = databaseReference?.child(todoItem.key)
        ref?.removeValue()?.addOnSuccessListener {
            Toast.makeText(context, "Data Deleted!", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            Toast.makeText(context, "Data Not Deleted!", Toast.LENGTH_SHORT).show()

        }
    }
}