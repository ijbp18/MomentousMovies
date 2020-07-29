package com.home.momentousmovies.ui.movieDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.data.datasource.Endpoints.GET_IMAGE
import com.home.momentousmovies.data.datasource.Endpoints.URL_BASE
import com.home.momentousmovies.databinding.ItemContainerCastingBinding
import com.home.momentousmovies.domain.model.Cast
import com.home.momentousmovies.utils.buildImageUrl
import com.home.momentousmovies.utils.loadImage

class CastingAdapter : RecyclerView.Adapter<CastingAdapter.ShowCastViewHolder>() {

    private var castList: List<Cast> = arrayListOf()
    private lateinit var binding: ItemContainerCastingBinding

    fun setData(castingList: List<Cast>) {
        this.castList = castingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCastViewHolder {
    binding = ItemContainerCastingBinding
    .inflate(LayoutInflater.from(parent.context), parent, false)
    return ShowCastViewHolder(binding)
}
    override fun getItemCount(): Int = castList.size

    override fun onBindViewHolder(holder: ShowCastViewHolder, position: Int) =
        holder.bind(castList[position])

    inner class ShowCastViewHolder(private val binding: ItemContainerCastingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) = with(binding) {
            if(!cast.image.isNullOrEmpty()) ivActorImage.loadImage(cast.image.buildImageUrl(URL_BASE, GET_IMAGE))
            txtCharacter.text = cast.character
            txtActor.text = cast.actor
        }
    }

}