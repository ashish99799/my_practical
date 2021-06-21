package com.my.practical.task.ui.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.practical.task.base.AppBaseAdapter
import com.my.practical.task.databinding.ItemSearchRepoCellBinding
import com.my.practical.task.model.responses.RowData
import com.my.practical.task.util.loadImage

class GithubUserAdapter : AppBaseAdapter<RowData, ItemSearchRepoCellBinding>() {

    override fun getViewBinding(parent: ViewGroup, attachToRoot: Boolean) = ItemSearchRepoCellBinding.inflate(LayoutInflater.from(parent.context), parent, attachToRoot)

    override fun setClickableView(itemView: View): List<View?> = listOf(binding.divCell)

    var selected = 0

    override fun onBind(
        viewType: Int,
        view: ItemSearchRepoCellBinding,
        position: Int,
        item: RowData,
        payloads: MutableList<Any>?
    ) {
        view.run {
            imgAvatar.loadImage((item.avatar_url ?: ""))
            lblUserName.text = (item.login ?: "")
        }
    }
}