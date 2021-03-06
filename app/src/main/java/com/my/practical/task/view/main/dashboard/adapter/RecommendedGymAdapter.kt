package com.my.practical.task.view.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseAdapter
import com.my.practical.task.databinding.ItemRecommendedGymCellBinding
import com.my.practical.task.model.db.GymData
import com.my.practical.task.util.loadImage

class RecommendedGymAdapter : AppBaseAdapter<GymData, ItemRecommendedGymCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) = ItemRecommendedGymCellBinding.inflate(LayoutInflater.from(parent.context), parent, attachToRoot)

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.divCell, binding.imgFavorite)

    override fun onBind(
        viewType: Int,
        view: ItemRecommendedGymCellBinding,
        position: Int,
        item: GymData,
        payloads: MutableList<Any>?
    ) {
        view.run {
            imgGym.loadImage(item.image)
            lblTitle.text = (item.title)
            lblDateTime.text = (item.date_time)
            imgFavorite.setImageResource(if (item.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
            lblPrice.text = String.format("$%.2f", item.price).replace(".00", "")
            lblRating.text = String.format("%.1f", item.rating).replace(".0", "")
        }
    }
}