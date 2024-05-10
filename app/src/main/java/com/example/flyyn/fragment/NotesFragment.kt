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
import com.example.flyyn.databinding.FragmentNotesBinding
import com.example.flyyn.factory.ViewModelFactory
import com.example.flyyn.model.Notes
import com.example.flyyn.repository.NotesRepository
import com.example.flyyn.viewmodel.NotesViewModel
import kotlinx.coroutines.launch

class NotesFragment : Fragment() {
    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModel: NotesViewModel
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        val dao = NotesDatabase.getDatabase(requireContext()).notesDao()
        val repository = NotesRepository(dao)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        setupRecyclerView()
        setupFAB()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val notesCount = viewModel.getNotesCount()
            if (notesCount == 0) {
                val note1 = Notes(0, "Judul Catatan 1", "Isi Catatan 1", false)
                val note2 = Notes(0, "Judul Catatan 2", "Isi Catatan 2", false)
                viewModel.insert(note1)
                viewModel.insert(note2)
            }
        }
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

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.notes = notes
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupFAB() {
        binding.fabAddNote.setOnClickListener {
            // TODO: Navigate to a new fragment to create a new note
        }
    }
}