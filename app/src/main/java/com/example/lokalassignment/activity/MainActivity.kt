package com.example.lokalassignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lokalassignment.fragment.BookmarksFragment
import com.example.lokalassignment.fragment.JobsFragment
import com.example.lokalassignment.R
import com.example.lokalassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(JobsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.jobs -> {
                    replaceFragment(JobsFragment())
                    true
                }

                R.id.bookmarks -> {
                    replaceFragment(BookmarksFragment())
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}