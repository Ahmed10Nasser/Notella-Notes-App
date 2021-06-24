package com.example.notella

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notella.adapter.NotesAdapter
import com.example.notella.database.NotesDatabase
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

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
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL )
        launch {
            context?.let{
                var notes = NotesDatabase.getDatabase(it).noteDao().getAll()
                recycler_view.adapter = NotesAdapter(notes)
            }
        }
        BtnCreateNote.setOnClickListener{
            insertFragment(CreateFragment.newInstance(), true)
        }
    }

    private fun insertFragment(fragment: Fragment, is_transition: Boolean){
        val fragmentTransaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        if (is_transition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.activity_fragment_container, fragment).addToBackStack(fragment.javaClass.simpleName).commit()

    }
}