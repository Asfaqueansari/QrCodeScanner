package com.example.qrcodescanner.ui.db.entites

import androidx.room.*
import com.example.qrcodescanner.ui.db.converters.DateTimeConverters
import java.util.*

@TypeConverters(DateTimeConverters::class)
@Entity
data class QrResult(

    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    @ColumnInfo(name= "result")
    val result : String?,

    @ColumnInfo(name = "result_type")
    val resultType : String?,

    @ColumnInfo(name = "favourite")
    val favourite : Boolean = false,

    @ColumnInfo(name = "time")
    val calendar : Calendar
)
