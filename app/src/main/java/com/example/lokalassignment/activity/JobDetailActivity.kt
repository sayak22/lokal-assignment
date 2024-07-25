package com.example.lokalassignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lokalassignment.R
import com.example.lokalassignment.databinding.ActivityJobDetailBinding

class JobDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Extracting data from the intent and assigning it to the respective views
        binding.jobTitleTV.text = intent.getStringExtra("jobTitle")
        binding.locationTV.text = intent.getStringExtra("jobLocation")
        binding.salaryTV.text = intent.getStringExtra("jobSalary")
        binding.phoneTV.text = intent.getStringExtra("jobPhoneNumber")
    }
}
