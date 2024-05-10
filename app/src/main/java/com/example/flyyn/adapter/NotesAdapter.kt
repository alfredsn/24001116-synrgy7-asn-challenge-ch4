package com.example.flyyn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flyyn.databinding.ItemRowBinding
import com.example.flyyn.model.Notes

class NotesAdapter(
    var notes: List<Notes>,
    private val onFavoriteClick: (Notes) -> Unit,
    private val onDeleteClick: (Notes) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            note: Notes,
            onFavoriteClick: (Notes) -> Unit,
            onDeleteClick: (Notes) -> Unit
        ) {
            binding.apply {
                tvTitle.text = note.title
                tvContent.text = note.content

                fabFavorite.setOnClickListener { onFavoriteClick(note) }
                fabDelete.setOnClickListener { onDeleteClick(note) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position], onFavoriteClick, onDeleteClick)
    }
}