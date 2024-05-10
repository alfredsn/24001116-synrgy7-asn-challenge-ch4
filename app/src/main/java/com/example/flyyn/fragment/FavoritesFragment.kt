package com.example.flyyn.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flyyn.adapter.NotesAdapter
import com.example.flyyn.database.NotesDatabase
import com.example.flyyn.databinding.FragmentFavoritesBinding
import com.example.flyyn.factory.ViewModelFactory
import com.example.flyyn.repository.NotesRepository
import com.example.flyyn.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val dao = NotesDatabase.getDatabase(requireContext()).notesDao()
        val repository = NotesRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        setupRecyclerView()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRecyclerView() {
        adapter = NotesAdapter(
            listOf(),
            onFavoriteClick = { note ->
                lifecycleScope.launch {
                    val updatedNote = note.copy(isFavorite = !note.isFavorite)
                    viewModel.update(updatedNote)
                }
            },
            onDeleteClick = { note ->
                lifecycleScope.launch {
                    viewModel.delete(note)
                }
            }
        )
        binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNote.adapter = adapter

        lifecycleScope.launch {
            viewModel.getFavoriteNotes().collect { notes ->
                adapter.notes = notes
                adapter.notifyDataSetChanged()
            }
        }
    }
}