package com.example.notella

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class CreateFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    var selectedColor = "#ff363636"
    var currentDate: String? = null
    private var READ_STORAGE_PERMISSION = 123
    private var IMAGE_REQUEST_CODE = 456
    private var selectedImgUri = ""
    private var link = ""
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteId = requireArguments().getInt("noteId",-1)
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


        if (noteId != -1){

            launch {
                context?.let {
                    var notes = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)
                    colorView.setBackgroundColor(Color.parseColor(notes.color))
                    NoteTitle.setText(notes.title)
                    NoteSubTitle.setText(notes.sub_title)
                    NoteText.setText(notes.text)
                    if (notes.imgUri != ""){
                        val selectedImgUri= Uri.parse(notes.imgUri)!!
                        imgNote.setImageURI(selectedImgUri)
                        imgNote.visibility = View.VISIBLE
                        imgDelete.visibility = View.VISIBLE
                        layoutInsertImage.visibility=View.VISIBLE
                    }else{
                        imgNote.visibility = View.GONE
                        imgDelete.visibility = View.GONE
                        layoutInsertImage.visibility=View.GONE
                    }


                    if (notes.link != ""){
                        link=notes.link!!
                        WebLink.text = notes.link
                        layoutInsertWebUrl.visibility=View.VISIBLE
                        imgUrlDelete.visibility=View.VISIBLE
                        etWebLink.setText(notes.link)
                    }else{
                        imgUrlDelete.visibility=View.GONE
                        layoutInsertWebUrl.visibility=View.GONE
                    }
                }
            }
        }

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(BroadcastReceiver, IntentFilter("bottom_action"))
        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        DateTime.text = currentDate

        imgDone.setOnClickListener {
            if (noteId != -1){
                updateNote()
            }else{
                saveNote()
            }
        }

        imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        imgMore.setOnClickListener {
            var noteBottomFragment = NoteBottomFragment.newInstance(noteId)
            noteBottomFragment.show(requireActivity().supportFragmentManager, "Note Bottom Fragment")

        }

        imgDelete.setOnClickListener {
            selectedImgUri = ""
            layoutInsertImage.visibility = View.GONE

        }

        btnOk.setOnClickListener {
            if (etWebLink.text.toString().trim().isNotEmpty()){
                ValidateUrl()
            }else{
                Toast.makeText(requireContext(),"Url is Required",Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            if (noteId != -1){
                WebLink.visibility = View.VISIBLE
                layoutInsertWebUrl.visibility = View.GONE
            }else{
                layoutInsertWebUrl.visibility = View.GONE
            }

        }

        imgUrlDelete.setOnClickListener {
            link = ""
            WebLink.visibility = View.GONE
            imgUrlDelete.visibility = View.GONE
            layoutInsertWebUrl.visibility = View.GONE
        }

        WebLink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW,Uri.parse(etWebLink.text.toString()))
            startActivity(intent)
        }
    }



    private fun updateNote(){
        launch {

            context?.let {
                var note = NotesDatabase.getDatabase(it).noteDao().getNote(noteId)

                note.title = NoteTitle.text.toString()
                note.sub_title = NoteSubTitle.text.toString()
                note.text = NoteText.text.toString()
                note.dateTime = currentDate
                note.color = selectedColor
                note.imgUri = selectedImgUri
                note.link = link

                NotesDatabase.getDatabase(it).noteDao().updateNote(note)
                NoteTitle.setText("")
                NoteSubTitle.setText("")
                NoteText.setText("")
                layoutInsertImage.visibility = View.GONE
                imgNote.visibility = View.GONE
                WebLink.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun saveNote() {
        if (NoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        else {

            launch {
                val note = Notes()
                note.title = NoteTitle.text.toString()
                note.sub_title = NoteSubTitle.text.toString()
                note.text = NoteText.text.toString()
                note.dateTime = currentDate
                note.color = selectedColor
                note.imgUri = selectedImgUri
                note.link=link
                context?.let {
                    NotesDatabase.getDatabase(it).noteDao().insertNote(note)
                    NoteText.setText("")
                    NoteTitle.setText("")
                    NoteSubTitle.setText("")
                    layoutInsertImage.visibility = View.GONE
                    imgNote.visibility = View.GONE
                    WebLink.visibility=View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun deleteNote(){

        launch {
            context?.let {
                NotesDatabase.getDatabase(it).noteDao().deleteNote(noteId)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun ValidateUrl(){
        if (Patterns.WEB_URL.matcher(etWebLink.text.toString()).matches()){
            layoutInsertWebUrl.visibility = View.GONE
            etWebLink.isEnabled = false
            link = etWebLink.text.toString()
            WebLink.visibility = View.VISIBLE
            WebLink.text = etWebLink.text.toString()
        }else{
            Toast.makeText(requireContext(),"Url is not valid",Toast.LENGTH_SHORT).show()
        }
    }

    private val BroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionNote = intent!!.getStringExtra("actionNote")!!

            when(actionNote!!){
                "Color"->{
                    selectedColor = intent!!.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "Image"->{
                    readStorageTask()
                    layoutInsertWebUrl.visibility=View.GONE
                }
                "WebUrl"->{
                    layoutInsertWebUrl.visibility=View.VISIBLE
                }
                "DeleteNote" -> {
                    deleteNote()
                }
                else -> {
                    layoutInsertImage.visibility = View.GONE
                    imgNote.visibility = View.GONE
                    layoutInsertWebUrl.visibility = View.GONE
                    selectedColor = intent!!.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
            }
        }

    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

    private fun hasReadStoragePermission():Boolean{
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask(){
        if (hasReadStoragePermission()){
            getImage()

        }else{
            EasyPermissions.requestPermissions(
                    requireActivity(),
                    getString(R.string.storage_permission),
                    READ_STORAGE_PERMISSION,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun getImage(){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent,IMAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,requireActivity())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data != null){
                var selectedImageUrl = data.data
                if (selectedImageUrl != null){
                    try {
                        imgNote.setImageURI(selectedImageUrl)
                        imgNote.visibility = View.VISIBLE
                        layoutInsertImage.visibility = View.VISIBLE
                        selectedImgUri = selectedImageUrl.toString()

                    }catch (e:Exception){
                        Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(),perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }

    override fun onRationaleDenied(requestCode: Int) {
    }

}