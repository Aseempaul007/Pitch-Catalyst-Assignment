package com.example.pitchcatalystassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pitchcatalystassignment.data.TodoItem
import com.example.pitchcatalystassignment.databinding.ListItemBinding

class ListAdapter(context: Context,private val todoList: List<TodoItem>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.binding.textTitle.text = currentItem.title
        holder.binding.textBody.text = currentItem.body

        holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isChecked = isChecked
            currentItem.isChecked= true
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}