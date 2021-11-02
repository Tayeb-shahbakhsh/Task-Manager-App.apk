package com.example.taskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskmanager.database.Notes
import com.example.taskmanager.databinding.RecyclerViewNoteBinding
class Adapter(private val list: MutableList<Notes>, private var listener: OnItemClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    //onCreateViewHolder needs a parent and viewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RecyclerViewNoteBinding.inflate(LayoutInflater.from(parent.context), parent , false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notes = list[position]

       holder.binding.titleNoteTextView.text = list[position].title
       holder.binding.descNoteTextView.text = list[position].description

        Glide.with(holder.itemView)
            .load(notes.imagePath)
            .into(holder.binding.noteImage)

        holder.binding.checkbox.setOnCheckedChangeListener{checkBoxView, isChecked ->
             notes.checked = isChecked
            listener.onUpdate(notes)
        }

        holder.binding.checkbox.isChecked = notes.checked
    }
    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: RecyclerViewNoteBinding) : RecyclerView.ViewHolder(binding.root)
        , View.OnClickListener{

        init{
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) listener.onItemClick(position)
        }
    }

    //make an interface to share items click listener with main activity
    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onUpdate(notes : Notes)
    }

}






