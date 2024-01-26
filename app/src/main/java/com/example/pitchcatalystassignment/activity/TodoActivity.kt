package com.example.pitchcatalystassignment.activity

import android.os.Bundle
import android.widget.Toast
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

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var viewmodel: TodoViewmodel? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private val toDoList = mutableListOf<TodoItem>()
    private var ref: DatabaseReference? = null


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
            val title = binding.editTextTitle.text.toString().trim()
            val body = binding.editTextBody.text.toString().trim()
            val key = ref?.key
            if(title.isNotEmpty() && body.isNotEmpty()){
                viewmodel?.addDataToFirebase(this@TodoActivity,title, body, key!!, ref)
            }else{
                Toast.makeText(this, "Please enter Title and Body", Toast.LENGTH_SHORT).show()
            }
            binding.editTextTitle.text.clear()
            binding.editTextBody.text.clear()
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