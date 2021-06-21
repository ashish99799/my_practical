package com.my.practical.task.base

import android.widget.Filter
import android.widget.Filterable
import androidx.viewbinding.ViewBinding

abstract class AppBaseFilterAdapter<T, B : ViewBinding>() : AppBaseAdapter<T, B>(), Filterable {

    val mainList = ArrayList<T>()

    abstract fun includeItem(query: CharSequence?, item: T): Boolean

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.isNullOrEmpty()) {
                    val filterResults = FilterResults()
                    filterResults.values = mainList
                    filterResults.count = mainList.size
                    return filterResults
                }

                val tempList = ArrayList<T>()
                mainList.forEach {
                    val isExist = includeItem(constraint, it)
                    if (isExist) {
                        tempList.add(it)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = tempList
                filterResults.count = tempList.size

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                list.clear()
                list.addAll(results.values as ArrayList<T>)
                notifyDataSetChanged()
            }
        }
    }

    override fun addAll(dataList: Collection<T>) {
        super.addAll(dataList)
        mainList.clear()
        mainList.addAll(dataList)
    }
}