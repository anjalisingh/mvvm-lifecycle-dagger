package com.solutions.mvvmlifeycledagger.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

/**
 * Created by anjalisingh on 14,January,2019
 */

@Dao
interface CitiesDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    fun insert( w: CityModel )

    @Delete
    fun remove( w: CityModel )

    @Query( "SELECT * FROM cities")
    fun getCities(): LiveData<List<CityModel>>
}