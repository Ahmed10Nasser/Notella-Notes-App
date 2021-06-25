package com.example.notella

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notella.adapter.NotesAdapter
import com.example.notella.database.NotesDatabase
import com.example.notella.entities.Notes
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : BaseFragment() {

    //Each individual element in the list is defined by a view holder object.
    // When the view holder is created, it doesn't have any data associated with it.
    // After the view holder is created, the RecyclerView binds it to its data.
    var NotesArray = ArrayList<Notes>()
    //The RecyclerView requests those views, and binds the views to their data, by calling methods in the adapter.
    // The adapter is defined by extending RecyclerView.Adapter.
    var notesAdapter: NotesAdapter = NotesAdapter()

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter changes cannot affect the size of the RecyclerView.
        NotesRecycleView.setHasFixedSize(true)
        // Notes on 2 columns Grid
        NotesRecycleView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL )
        // Get all notes from database
        launch {
            context?.let{
                var notes = NotesDatabase.getDatabase(it).noteDao().getAll()
                notesAdapter!!.setData(notes)
                NotesArray = notes as ArrayList<Notes>
                NotesRecycleView.adapter = notesAdapter
            }
        }

        //listen for click on notes
        notesAdapter!!.setOnClickListener(onClicked)

        //listen for click to replace Home with Notes Creation Fragment
        CreateNoteBtn.setOnClickListener{
            insertFragment(CreateFragment.newInstance(), true)
        }

        // search for notes with text in search bar in the title (letter case irrelevant)
        SearchBar.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                var searchResultNotes = ArrayList<Notes>()
                for (note in NotesArray){
                    if (note.title!!.toLowerCase(Locale.getDefault()).contains(searchText.toString().toLowerCase())){
                        searchResultNotes.add(note)
                    }
                }
                notesAdapter.setData(searchResultNotes)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })
    }

    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {
            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment = CreateFragment.newInstance()
            fragment.arguments = bundle
            insertFragment(fragment,false)
        }

    }

    // Set specific animation resources to run for the fragments that are entering and exiting in this transaction.
    // add transaction to back stack
    private fun insertFragment(fragment: Fragment, is_transition: Boolean){
        val fragmentTransaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        if (is_transition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.activity_fragment_container, fragment).addToBackStack(fragment.javaClass.simpleName).commit()

    }




}