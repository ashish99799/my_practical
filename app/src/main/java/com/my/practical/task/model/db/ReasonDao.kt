package com.my.practical.task.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.my.practical.task.base.BaseDao

@Dao
abstract class ReasonDao : BaseDao<GymData>() {

    @Query("SELECT * FROM Gym WHERE id=:id")
    abstract fun getGyms(id: Int): LiveData<GymData>

    override fun insert(entity: GymData) {

    }

    override fun update(entity: GymData) {

    }
}