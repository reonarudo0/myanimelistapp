package com.example.learningleo.repository

import android.content.Context
import android.util.Log
import androidx.room.*


@Entity(tableName = "markers")
data class Marker(
    @PrimaryKey(autoGenerate = true) var uid : Int = 0,
    @ColumnInfo(name = "user") var userName : String = "",
    @ColumnInfo(name = "marker_name") var markerName : String = "",
    @ColumnInfo(name = "latitude") var latitude : String = "",
    @ColumnInfo(name = "longitude") var longitude : String = ""
)

@Dao
interface MarkerDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMarker(marker: Marker)

    @Query("SELECT * FROM markers")
    fun markers(): Array<Marker>

    @Query("SELECT * FROM markers WHERE user LIKE :user_name")
    fun userMarkers(user_name : String): Array<Marker>
}

@Database(entities = [Marker::class], version = 2)
abstract class MarkerDatabase : RoomDatabase(){
    companion object {
        private var instance: MarkerDatabase? = null
        fun getInstance(context: Context): MarkerDatabase {
            if(instance == null) {
                instance = Room
                    .databaseBuilder(context, MarkerDatabase::class.java, "review_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
    abstract fun markerDAO():MarkerDAO
}

class RoomRepository(context: Context)
{
    private val database = MarkerDatabase.getInstance(context)

    fun insert(marker: Marker)
    {
        database.markerDAO().insertMarker(marker)
    }

    fun getByUser(user: String,callback: (markerArray: Array<Marker>?)->Unit)
    {
        val allUserMarkers = database.markerDAO().userMarkers(user)
        callback(allUserMarkers)
    }
}