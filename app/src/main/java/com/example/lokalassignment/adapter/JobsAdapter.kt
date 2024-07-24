package com.example.lokalassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.model.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.jobTitleTV)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTV)
        private val salaryTextView: TextView = itemView.findViewById(R.id.salaryTV)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTV)
        private val bookmarkStatusImageView: ImageView = itemView.findViewById(R.id.bookmarkStatus)

        init {
            db = BookmarkedJobDatabase.getDatabase(itemView.context)

            bookmarkStatusImageView.setOnClickListener {
                val job = jobs[bindingAdapterPosition]
                job.isBookmarked = !job.isBookmarked

                GlobalScope.launch(Dispatchers.IO) {
                    job.id?.let {
                        if (job.isBookmarked) {
                            db.bookmarkedJobDAO().insertJob(
                                BookmarkedJob(
                                    job.id, job.title,
                                    job.primaryDetails?.destination,
                                    job.primaryDetails?.salary, job.phoneNumber,
                                    job.isBookmarked
                                )
                            )
                        } else {
                            db.bookmarkedJobDAO().deleteJob(BookmarkedJob(
                                job.id, job.title,
                                job.primaryDetails?.destination,
                                job.primaryDetails?.salary, job.phoneNumber,
                                job.isBookmarked
                            ))
                        }
                    }
                }

                val drawableResId =
                    if (job.isBookmarked) R.drawable.ic_bookmark_added else R.drawable.ic_bookmark_unadded
                bookmarkStatusImageView.setImageResource(drawableResId)
            }
        }

        fun bind(job: Job) {
            titleTextView.text = job.title
            locationTextView.text = job.primaryDetails?.destination ?: "NA"
            salaryTextView.text = job.primaryDetails?.salary ?: "NA"
            phoneTextView.text = job.phoneNumber
        }
    }
}
