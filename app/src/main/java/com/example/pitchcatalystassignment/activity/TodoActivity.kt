package com.example.pitchcatalystassignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pitchcatalystassignment.data.TodoItem
import com.example.pitchcatalystassignment.databinding.ActivityMainBinding
import com.example.pitchcatalystassignment.viewmodel.TodoViewmodel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var viewmodel: TodoViewmodel? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    val toDoList = mutableListOf<TodoItem>()
    var ref: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this)[TodoViewmodel::class.java]
        FirebaseApp.initializeApp(this)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("EmployeeInfo")

        viewmodel!!.showDataFromFirebase(databaseReference,toDoList,binding,this@TodoActivity)

        binding.addButton.setOnClickListener {
            ref = databaseReference?.push()
            val title = binding.editTextTitle.text.toString()
            val body = binding.editTextBody.text.toString()
            val key = ref?.key
            viewmodel?.addDataToFirebase(this@TodoActivity,title, body, key!!, ref)
        }

        binding.deleteButton.setOnClickListener {
            for(i in 0..toDoList.size-1){
                if (toDoList[i].isChecked){
                    viewmodel?.deleteDataFromFirebase(this@TodoActivity,toDoList[i], databaseReference)
                }
            }
        }
    }






}