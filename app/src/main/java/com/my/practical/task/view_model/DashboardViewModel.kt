package com.my.practical.task.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import com.my.practical.task.base.AppBaseViewModel
import com.my.practical.task.model.db.GymData
import com.my.practical.task.model.db.PopularGymData
import com.my.practical.task.model.repositorys.AppRepository

class DashboardViewModel : AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = DashboardViewModel().apply {
            mContext = context
        }

        lateinit var mGymDataList: LiveData<List<GymData>>
    }

    fun getAllGymList(): LiveData<List<GymData>> {
        mGymDataList = AppRepository.getAllGymList(mContext)
        return mGymDataList
    }

    fun updateGym(gymData: GymData) {
        AppRepository.updateGym(mContext, gymData)
    }

    fun getAllPopularGyms(): LiveData<List<PopularGymData>> {
        return AppRepository.getAllPopularGyms(mContext)
    }

    fun getAllPopularGymList(gym_id: Int): LiveData<List<PopularGymData>> {
        return AppRepository.getAllPopularGymList(mContext, gym_id)
    }

    fun updatePopularGym(popularGymData: PopularGymData) {
        AppRepository.updatePopularGym(mContext, popularGymData)
    }

}