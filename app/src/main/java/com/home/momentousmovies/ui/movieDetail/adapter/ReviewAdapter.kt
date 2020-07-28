package com.home.momentousmovies.ui.movieDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.databinding.ItemContainerReviewBinding
import com.home.momentousmovies.model.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ShowReviewViewHolder>() {

    private var reviews: List<Review> = arrayListOf()
    private lateinit var binding: ItemContainerReviewBinding

    fun setData(reviewList: List<Review>) {
        this.reviews = reviewList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowReviewViewHolder {
    binding = ItemContainerReviewBinding
    .inflate(LayoutInflater.from(parent.context), parent, false)
    return ShowReviewViewHolder(binding)
}
    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ShowReviewViewHolder, position: Int) =
        holder.bind(reviews[position])

    inner class ShowReviewViewHolder(private val binding: ItemContainerReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) = with(binding) {
            tvAuthorName.text = review.author
            tvAuthorDescription.text = review.value
        }
    }



}