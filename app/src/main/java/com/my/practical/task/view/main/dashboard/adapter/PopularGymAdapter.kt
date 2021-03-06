package com.my.practical.task.view.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.practical.task.R
import com.my.practical.task.base.AppBaseFilterAdapter
import com.my.practical.task.databinding.ItemPopularGymCellBinding
import com.my.practical.task.model.db.PopularGymData
import com.my.practical.task.util.loadImage

class PopularGymAdapter : AppBaseFilterAdapter<PopularGymData, ItemPopularGymCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) = ItemPopularGymCellBinding.inflate(LayoutInflater.from(parent.context), parent, attachToRoot)

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.imgFavorite)

    override fun includeItem(query: CharSequence?, item: PopularGymData): Boolean {
        return ((item.gym_id).toString().lowercase().contains(query.toString().lowercase()))
    }

    override fun onBind(
        viewType: Int,
        view: ItemPopularGymCellBinding,
        position: Int,
        item: PopularGymData,
        payloads: MutableList<Any>?
    ) {
        view.run {
            imgGym.loadImage(item.image)
            lblTitle.text = (item.title)
            lblDescription.text = (item.description)
            imgFavorite.setImageResource(if (item.favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
            lblPrice.text = String.format("$%.2f", item.price).replace(".00", "")
            lblLocation.text = (item.location)
            lblRating.text = String.format("%.1f", item.rating).replace(".0", "")
        }
    }

}