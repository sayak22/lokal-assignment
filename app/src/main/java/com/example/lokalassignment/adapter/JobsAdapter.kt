package com.example.lokalassignment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.activity.JobDetailActivity
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.model.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobsAdapter(private var jobs: MutableList<Job>) :
    RecyclerView.Adapter<JobsAdapter.ViewHolder>() {

    private lateinit var db: BookmarkedJobDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobs.size

    /**
     * ViewHolder class for holding the job item views.
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.jobTitleTV)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTV)
        private val salaryTextView: TextView = itemView.findViewById(R.id.salaryTV)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTV)
        private val bookmarkStatusImageView: ImageView = itemView.findViewById(R.id.bookmarkStatus)

        init {
            db = BookmarkedJobDatabase.getDatabase(itemView.context)

            // Set click listener for the entire item view
            itemView.setOnClickListener {
                val job = jobs[bindingAdapterPosition]

                // Creating an Intent to launch JobDetailActivity
                val intent = Intent(itemView.context, JobDetailActivity::class.java)

                // Passing all the data as extras to the intent
                intent.putExtra("jobId", job.id)
                intent.putExtra("jobTitle", job.title)
                intent.putExtra("jobLocation", job.primaryDetails?.destination)
                intent.putExtra("jobSalary", job.primaryDetails?.salary)
                intent.putExtra("jobPhoneNumber", job.phoneNumber)

                itemView.context.startActivity(intent)
            }

            // Set click listener for the bookmark status image view
            bookmarkStatusImageView.setOnClickListener {
                val job = jobs[bindingAdapterPosition]
                job.isBookmarked = if (job.isBookmarked == 0) 1 else 0

                // Update bookmark status in the database
                CoroutineScope(Dispatchers.IO).launch {
                    if (job.isBookmarked == 1) {
                        addJobToBookmarks(job)
                    } else {
                        removeJobFromBookmarks(job)
                    }

                    // Update UI on the main thread
                    withContext(Dispatchers.Main) {
                        updateBookmarkIcon(job)
                    }
                }
            }
        }

        /**
         * Binds job data to the views.
         */
        fun bind(job: Job) {
            titleTextView.text = job.title
            locationTextView.text = job.primaryDetails?.destination ?: "NA"
            salaryTextView.text = job.primaryDetails?.salary ?: "NA"
            phoneTextView.text = job.phoneNumber
            updateBookmarkIcon(job)
        }

        /**
         * Updates the bookmark icon based on the job's bookmark status.
         */
        private fun updateBookmarkIcon(job: Job) {
            val drawableResId =
                if (job.isBookmarked == 1) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark_unadded
            bookmarkStatusImageView.setImageResource(drawableResId)
        }

        /**
         * Adds the job to bookmarks in the database.
         */
        private suspend fun addJobToBookmarks(job: Job) {
            db.bookmarkedJobDAO().insertJob(
                BookmarkedJob(
                    id = job.id,
                    title = job.title,
                    destination = job.primaryDetails?.destination,
                    salary = job.primaryDetails?.salary,
                    phoneNumber = job.phoneNumber,
                    isBookmarked = job.isBookmarked
                )
            )
        }

        /**
         * Removes the job from bookmarks in the database.
         */
        private suspend fun removeJobFromBookmarks(job: Job) {
            db.bookmarkedJobDAO().deleteJob(
                BookmarkedJob(
                    id = job.id,
                    title = job.title,
                    destination = job.primaryDetails?.destination,
                    salary = job.primaryDetails?.salary,
                    phoneNumber = job.phoneNumber,
                    isBookmarked = job.isBookmarked
                )
            )
        }
    }
}
