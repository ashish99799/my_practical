package com.my.practical.task.model.repositorys

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.my.practical.task.base.AppBaseRepository
import com.my.practical.task.model.db.AppDB
import com.my.practical.task.model.db.GymData
import com.my.practical.task.model.db.PopularGymData
import com.my.practical.task.model.responses.DataResponse
import com.my.practical.task.model.responses.UserData
import com.my.practical.task.model.responses.UserRepoData
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// Repository
class AppRepository : AppBaseRepository() {

    companion object {
        var appDB: AppDB? = null

        // Init App DB
        fun initializeDB(context: Context): AppDB {
            return AppDB.getDatabaseClient(context)
        }

        // ================================================================================================
        // Gym Data Start
        fun insertGymData(context: Context, gymData: GymData) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().addGym(gymData)
            }
        }

        fun insertAllGymData(context: Context, gymData: List<GymData>) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().addAllGym(gymData)
            }
        }

        fun getAllGymList(context: Context): LiveData<List<GymData>> {
            appDB = initializeDB(context)
            return appDB!!.daoApp().getAllGymList()
        }

        fun getGymDetails(context: Context, id: Int): LiveData<GymData> {
            appDB = initializeDB(context)
            return appDB!!.daoApp().getGymDetails(id)
        }

        fun updateGym(context: Context, gymData: GymData) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().updateGym(gymData)
            }
        }
        // Gym Data Start
        // ================================================================================================
        // Popular Gym Data Start
        fun insertPopularGym(context: Context, popularGymData: PopularGymData) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().addPopularGym(popularGymData)
            }
        }

        fun insertAllPopularGym(context: Context, popularGymData: List<PopularGymData>) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().addAllPopularGym(popularGymData)
            }
        }

        fun getAllPopularGyms(context: Context): LiveData<List<PopularGymData>> {
            appDB = initializeDB(context)
            return appDB!!.daoApp().getAllPopularGyms()
        }

        fun getAllPopularGymList(context: Context, gym_id: Int): LiveData<List<PopularGymData>> {
            appDB = initializeDB(context)
            return appDB!!.daoApp().getAllPopularGymList(gym_id)
        }

        fun getPopularGymDetails(context: Context, id: Int): LiveData<PopularGymData> {
            appDB = initializeDB(context)
            return appDB!!.daoApp().getPopularGymDetails(id)
        }

        fun updatePopularGym(context: Context, popularGymData: PopularGymData) {
            appDB = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDB!!.daoApp().updatePopularGym(popularGymData)
            }
        }
        // Popular Gym Data Start
    }

    fun getSearchUser(query: String, page: Int): Observable<DataResponse> {
        // Ratrofit API Calling
        return api.getUserSearch(query, page)
    }

    fun onUserInfo(query: String): Observable<UserData> {
        // Ratrofit API Calling
        return api.getUserInfo(query)
    }

    fun onUserRepo(query: String): Observable<List<UserRepoData>> {
        // Ratrofit API Calling
        return api.getUserRipo(query)
    }
}