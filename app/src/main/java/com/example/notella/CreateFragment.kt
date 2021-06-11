package com.example.notella

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notella.database.NotesDatabase
import com.example.notella.entities.Notes
import com.example.notella.util.NoteBottomFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_create_note.DateTime
import kotlinx.android.synthetic.main.fragment_create_note.NoteText
import kotlinx.android.synthetic.main.fragment_note_bottom.*
import kotlinx.android.synthetic.main.notes_item.*
import kotlinx.android.synthetic.main.notes_item.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class CreateFragment : BaseFragment() {

    var selectedColor = "#606570"
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

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(BroadcastReceiver, IntentFilter("color_action"))
        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        DateTime.text = currentDate

        imgDone.setOnClickListener {
            // Save Note
            saveNote()
        }

        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener {
            var noteBottomFragment = NoteBottomFragment.newInstance()
            noteBottomFragment.show(requireActivity().supportFragmentManager, "Note Bottom Fragment")

        }
    }

    private fun saveNote() {
        if (NoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        else if (NoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        else if (NoteText.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Text is Required", Toast.LENGTH_SHORT).show()
        }
        else {

            launch {
                val notes = Notes()
                notes.title = NoteTitle.text.toString()
                notes.sub_title = NoteSubTitle.text.toString()
                notes.text = NoteText.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insert(notes)
                    NoteText.setText("")
                    NoteTitle.setText("")
                    NoteSubTitle.setText("")
                }
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

    private val BroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            selectedColor = intent!!.getStringExtra("selectedColor")!!
            colorView.setBackgroundColor(Color.parseColor(selectedColor))
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

}