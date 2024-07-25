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
import kotlinx.coroutines.*

class BookmarksAdapter(private var bookmarkList: MutableList<Job>) :
    RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private lateinit var db: BookmarkedJobDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return BookmarksViewHolder(view)
    }

    override fun getItemCount(): Int = bookmarkList.size

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val job = bookmarkList[position]
        holder.bind(job)
    }

    inner class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // UI elements
        private val titleTextView: TextView = itemView.findViewById(R.id.jobTitleTV)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTV)
        private val salaryTextView: TextView = itemView.findViewById(R.id.salaryTV)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTV)
        private val bookmarkStatusImageView: ImageView = itemView.findViewById(R.id.bookmarkStatus)

        init {
            db = BookmarkedJobDatabase.getDatabase(itemView.context)

            // Handle bookmark status change on click
            bookmarkStatusImageView.setOnClickListener {
                val job = bookmarkList[bindingAdapterPosition]
                toggleBookmarkStatus(job)
            }
        }

        /**
         * Binds data to the UI components for each job item.
         */
        fun bind(job: Job) {
            titleTextView.text = job.title
            locationTextView.text = job.primaryDetails?.destination ?: "NA"
            salaryTextView.text = job.primaryDetails?.salary ?: "NA"
            phoneTextView.text = job.phoneNumber
            bookmarkStatusImageView.setImageResource(R.drawable.ic_bookmark_added)
        }

        /**
         * Toggles the bookmark status of a job and updates the database and UI.
         */
        private fun toggleBookmarkStatus(job: Job) {
            val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
                throwable.printStackTrace()
            }

            GlobalScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                db.bookmarkedJobDAO().deleteJob(
                    BookmarkedJob(
                        job.id,
                        job.title,
                        job.primaryDetails?.destination,
                        job.primaryDetails?.salary,
                        job.phoneNumber,
                        job.isBookmarked
                    )
                )

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    val drawableResId = if (job.isBookmarked == 1) {
                        R.drawable.ic_bookmark_added
                    } else {
                        R.drawable.ic_bookmark_unadded
                    }
                    bookmarkStatusImageView.setImageResource(drawableResId)

                    // Remove the job from the list and notify adapter
                    bookmarkList.removeAt(bindingAdapterPosition)
                    notifyItemRemoved(bindingAdapterPosition)
                }
            }
        }
    }

    /**
     * Updates the adapter's data set and refreshes the RecyclerView.
     */
    fun setData(data: MutableList<Job>) {
        this.bookmarkList = data
        notifyDataSetChanged()
    }
}
