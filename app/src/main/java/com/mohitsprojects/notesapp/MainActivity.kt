package com.mohitsprojects.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter(this,this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer{list ->
            list?.let {
                adapter.updateList(it)
            }
        })

    }

    fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.text} Deleted ",Toast.LENGTH_LONG).show()
    }

    fun submitData(view: android.view.View) {
        val noteText = input.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.addNote(Note(noteText))
            Toast.makeText(this,"${noteText} Inserted ",Toast.LENGTH_LONG).show()
        }
    }
}