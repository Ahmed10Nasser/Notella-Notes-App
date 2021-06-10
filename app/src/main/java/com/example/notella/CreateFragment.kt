package com.example.notella

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.notella.database.NotesDatabase
import com.example.notella.entities.Notes
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateFragment : BaseFragment() {
    var currentDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                CreateFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        DateTime.text = currentDate

        imgDone.setOnClickListener {
            // Save Note
            saveNote()
        }

        imgBack.setOnClickListener {
            insertFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote() {
        if (NoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        if (NoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        if (NoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = NoteTitle.text.toString()
            notes.sub_title = NoteSubTitle.text.toString()
            notes.text = NoteDesc.text.toString()
            notes.dateTime = currentDate
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insert(notes)
                NoteDesc.setText("")
                NoteTitle.setText("")
                NoteSubTitle.setText("")
            }
        }
    }

    private fun insertFragment(fragment: Fragment, is_transition: Boolean) {
        val fragmentTransaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        if (is_transition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.activity_fragment_container, fragment).addToBackStack(fragment.javaClass.simpleName).commit()

    }

}