package com.example.notella.util

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.fragment_note_bottom.*

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.notella.R
import com.example.notella.database.NotesDatabase
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteBottomFragment : BottomSheetDialogFragment() {

    var selectedColor = "#ff363636"

    companion object{
        var noteId = -1
        fun newInstance(Id: Int): NoteBottomFragment{
            var args = Bundle()
            val fragment = NoteBottomFragment()
            fragment.arguments = args
            noteId = Id
            return fragment
        }
    }

    override  fun setupDialog(dialog: Dialog, style: Int){
        super.setupDialog(dialog, style)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_note_bottom, null)
        dialog.setContentView(view)

        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behaviour = param.behavior

        if (behaviour is BottomSheetBehavior<*>){
            behaviour.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    var state =""
                    when (newState){
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            state = "DRAGGING"
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            state = "SETTLING"
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            state = "EXPANDED"
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            state = "COLLAPSED"
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            state = "HIDDEN"
                            dismiss()
                            behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }
            })


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_bottom, container, false)
    }

    @SuppressLint("ResourceType")
    private fun updateSelectedColor() = runBlocking {  // this: CoroutineScope
        launch {
            context?.let {
                var note = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)
                selectedColor = note.color!!
                if(selectedColor == getResources().getString(R.color.ColorDefaultNote)){imgNoteDefault.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorBlueNote)){imgNote1.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorYellowNote)){imgNote2.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorWhiteNote)){imgNote3.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorPurpleNote)){imgNote4.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorGreenNote)){imgNote5.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorOrangeNote)){imgNote6.setImageResource(R.drawable.ic_tick)}
                else if(selectedColor == getResources().getString(R.color.ColorBlackNote)){imgNote7.setImageResource(R.drawable.ic_tick)}
            }
        }  // implicit this.launch {}
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if note is still being created don't show delete option, if note is being updated shoe delete option
        if (noteId != -1){
            layoutDelete.visibility = View.VISIBLE
            updateSelectedColor();
        }
        else {
            layoutDelete.visibility = View.GONE
        }
        if(selectedColor == getResources().getString(R.color.ColorDefaultNote)){imgNoteDefault.setImageResource(R.drawable.ic_tick)}
        setListener()
    }



    //send Broadcast on events (color selection, Image selection, Web Link addition, Delete note)
    @SuppressLint("ResourceType")
    private fun setListener(){
        Note1.setOnClickListener{
            imgNote1.setImageResource(R.drawable.ic_tick)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorBlueNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note2.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(R.drawable.ic_tick)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorYellowNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note3.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(R.drawable.ic_tick)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorWhiteNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note4.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(R.drawable.ic_tick)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorPurpleNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note5.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(R.drawable.ic_tick)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorGreenNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note6.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(R.drawable.ic_tick)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorOrangeNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        Note7.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(R.drawable.ic_tick)
            imgNoteDefault.setImageResource(0)
            selectedColor = getResources().getString(R.color.ColorBlackNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        NoteDefault.setOnClickListener{
            imgNote1.setImageResource(0)
            imgNote2.setImageResource(0)
            imgNote3.setImageResource(0)
            imgNote4.setImageResource(0)
            imgNote5.setImageResource(0)
            imgNote6.setImageResource(0)
            imgNote7.setImageResource(0)
            imgNoteDefault.setImageResource(R.drawable.ic_tick)
            selectedColor = getResources().getString(R.color.ColorDefaultNote);
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Color")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        layoutImg.setOnClickListener{
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "Image")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        layoutWebLink.setOnClickListener{
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "WebLink")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        layoutDelete.setOnClickListener{
            val intent = Intent("bottom_action")
            intent.putExtra("actionNote", "DeleteNote")
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
    }
}