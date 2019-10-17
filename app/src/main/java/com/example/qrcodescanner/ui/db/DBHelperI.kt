package com.example.qrcodescanner.ui.db

import com.example.qrcodescanner.ui.db.entites.QrResult

interface DBHelperI {
    fun insertQrResult(result : String): Int

    fun getQrResult(id: Int):QrResult

    fun addToFavourite(id: Int): Int

    fun removeFromFavourite(id: Int): Int

    fun getAllQrScannedResult() : List<QrResult>

    fun getAllFavouriteQrScannedResults(): List<QrResult>

    fun deleteQrResult(id: Int):Int

    fun deleteAllQrScannedResults()

    fun deleteAllFavouriteQrScannedResults()


}