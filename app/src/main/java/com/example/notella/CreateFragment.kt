package com.example.notella

import android.os.Bundle
import androidx.fragment.app.Fragment
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
    var currentDate:String?=null
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
        val sdf=SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate=sdf.format(Date())

        tvDateTime.text=currentDate

        imgDone.setOnClickListener{
            // Save Note
            saveNote()
        }

        imgBack.setOnClickListener{
            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote(){
        if(etNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        if(etNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        if(etNoteDesc.text.isNullOrEmpty()){
            Toast.makeText(context,"Note Description is Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes=Notes()
            notes.title=etNoteTitle.text.toString()
            notes.sub_title=etNoteSubTitle.text.toString()
            notes.text=etNoteDesc.text.toString()
            notes.dateTime=currentDate
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insert(notes)
                etNoteDesc.setText("")
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
            }
        }
    }

    fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()
        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.activity_fragment_container,fragment).addToBackStack(fragment.javaClass.simpleName)
    }
}