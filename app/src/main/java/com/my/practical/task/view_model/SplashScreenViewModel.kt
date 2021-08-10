package com.my.practical.task.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import com.my.practical.task.base.AppBaseViewModel
import com.my.practical.task.model.db.GymData
import com.my.practical.task.model.db.PopularGymData
import com.my.practical.task.model.repositorys.AppRepository

class SplashScreenViewModel : AppBaseViewModel() {

    companion object {
        lateinit var mContext: Context

        @JvmStatic
        fun newInstance(context: Context) = SplashScreenViewModel().apply {
            mContext = context
        }

        lateinit var mGymDataList: LiveData<List<GymData>>
    }

    fun getAllGymList(): LiveData<List<GymData>> {
        mGymDataList = AppRepository.getAllGymList(mContext)
        return mGymDataList
    }

    fun addAllGym(gymData: List<GymData>) {
        AppRepository.insertAllGymData(mContext, gymData)
    }

    fun addAllPopularGym(popularGymData: List<PopularGymData>) {
        AppRepository.insertAllPopularGym(mContext, popularGymData)
    }

}