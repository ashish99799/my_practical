package com.my.practical.task.base

import androidx.room.*

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: T)
    @Update
    abstract fun update(entity: T)
    @Delete
    abstract fun delete(entity: T)
}