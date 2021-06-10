package com.example.notella

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertFragment(HomeFragment.newInstance(), true)
    }

    private fun insertFragment(fragment: Fragment, is_transition: Boolean){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (is_transition) {
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransaction.replace(R.id.activity_fragment_container, fragment).addToBackStack(fragment.javaClass.simpleName).commit()

    }

}

