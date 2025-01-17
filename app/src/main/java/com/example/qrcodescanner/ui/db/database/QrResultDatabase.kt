package com.example.qrcodescanner.ui.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qrcodescanner.ui.db.dao.QrResultDao
import com.example.qrcodescanner.ui.db.entites.QrResult

@Database(entities = [QrResult::class],version = 1,exportSchema = false)
abstract class QrResultDatabase : RoomDatabase() {
    abstract fun getQrDao(): QrResultDao

    companion object{
        private const val DB_NAME = " QrResultDatabase"
        private  var qrResultDatabase : QrResultDatabase? = null

        fun getAppDatabase(context: Context) : QrResultDatabase?{
            if(qrResultDatabase == null){
                qrResultDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    QrResultDatabase::class.java,
                    DB_NAME
                ).allowMainThreadQueries().build()
            }
            return qrResultDatabase
        }
    }
}