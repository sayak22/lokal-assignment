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
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarksAdapter(private var bookmarkList: MutableList<Job>) : RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

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

    @OptIn(DelicateCoroutinesApi::class)
    inner class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.jobTitleTV)
        private val locationTextView: TextView = itemView.findViewById(R.id.locationTV)
        private val salaryTextView: TextView = itemView.findViewById(R.id.salaryTV)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phoneTV)
        private val bookmarkStatusImageView: ImageView = itemView.findViewById(R.id.bookmarkStatus)

        init {
            db = BookmarkedJobDatabase.getDatabase(itemView.context)

            bookmarkStatusImageView.setOnClickListener {
                val bookmark = bookmarkList[bindingAdapterPosition]
                bookmark.isBookmarked = !bookmark.isBookmarked

                GlobalScope.launch(Dispatchers.IO) {
                    bookmark.id?.let {
                        if (bookmark.isBookmarked) {
                            db.bookmarkedJobDAO().insertJob(
                                BookmarkedJob(
                                    bookmark.id, bookmark.title,
                                    bookmark.primaryDetails?.destination,
                                    bookmark.primaryDetails?.salary, bookmark.phoneNumber,
                                    bookmark.isBookmarked
                                )
                            )
                        } else {
                            db.bookmarkedJobDAO().deleteJob(
                                BookmarkedJob(
                                    bookmark.id, bookmark.title,
                                    bookmark.primaryDetails?.destination,
                                    bookmark.primaryDetails?.salary, bookmark.phoneNumber,
                                    bookmark.isBookmarked
                                )
                            )
                            bookmarkList.removeAt(bindingAdapterPosition)
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }

        fun bind(job: Job) {
            titleTextView.text = job.title
            locationTextView.text = job.primaryDetails?.destination ?: "NA"
            salaryTextView.text = job.primaryDetails?.salary ?: "NA"
            phoneTextView.text = job.phoneNumber
        }
    }
    fun setData(data: MutableList<Job>){
        this.bookmarkList=data
    }
}