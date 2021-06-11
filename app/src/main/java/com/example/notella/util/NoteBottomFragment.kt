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
import com.google.android.material.bottomsheet.BottomSheetBehavior

class NoteBottomFragment : BottomSheetDialogFragment() {

    var selectedColor = "#606570"

    companion object{
        fun newInstance(): NoteBottomFragment{
            var args = Bundle()
            val fragment = NoteBottomFragment()
            fragment.arguments = args
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(selectedColor == "#606570") imgNoteDefault.setImageResource(R.drawable.ic_tick)
        setListener()
    }



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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Blue")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Yellow")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "White")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Purple")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Green")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Orange")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Black")
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
            val intent = Intent("color_action")
            intent.putExtra("actionColor", "Default")
            intent.putExtra("selectedColor", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
    }
}