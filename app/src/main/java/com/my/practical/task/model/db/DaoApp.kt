package com.my.practical.task.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface DaoApp {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGym(gymData: GymData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllGym(gymData: List<GymData>)

    @Query(value = "SELECT * FROM Gym WHERE id=:id LIMIT 1")
    fun getGymDetails(id: Int): LiveData<GymData>

    @Query(value = "SELECT * FROM Gym")
    fun getAllGymList(): LiveData<List<GymData>>

    @Update
    fun updateGym(gymData: GymData)

    // =================================================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularGym(popularGymData: PopularGymData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPopularGym(popularGymData: List<PopularGymData>)

    @Query(value = "SELECT * FROM PopularGym WHERE id=:id LIMIT 1")
    fun getPopularGymDetails(id: Int): LiveData<PopularGymData>

    @Query(value = "SELECT * FROM PopularGym")
    fun getAllPopularGyms(): LiveData<List<PopularGymData>>

    @Query(value = "SELECT * FROM PopularGym WHERE gym_id =:gym_id")
    fun getAllPopularGymList(gym_id: Int): LiveData<List<PopularGymData>>

    @Update
    fun updatePopularGym(gymData: PopularGymData)

}