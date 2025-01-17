package com.example.qrcodescanner.ui.db

import com.example.qrcodescanner.ui.db.database.QrResultDatabase
import com.example.qrcodescanner.ui.db.entites.QrResult
import java.util.*

class DBHelper(var qrResultDatabase: QrResultDatabase) :  DBHelperI{


    override fun addToFavourite(id: Int): Int {
        return qrResultDatabase.getQrDao().addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return qrResultDatabase.getQrDao().removeFromFavourite(id)
    }

    override fun insertQrResult(result: String): Int {
        val time = Calendar.getInstance()
        val resultType = "TEXT"
        val qrResult = QrResult(result = result,resultType = resultType,calendar = time,favourite = false)
        return qrResultDatabase.getQrDao().insertQrResult(qrResult).toInt()

    }

    override fun getQrResult(id: Int): QrResult {
        return qrResultDatabase.getQrDao().getQrResults(id)
    }

    override fun getAllQrScannedResult(): List<QrResult> {
        return qrResultDatabase.getQrDao().getAllScannedResults()
    }

    override fun getAllFavouriteQrScannedResults(): List<QrResult> {
        return qrResultDatabase.getQrDao().getAllFavouriteResults()
    }

    override fun deleteQrResult(id: Int): Int {
        return qrResultDatabase.getQrDao().deleteQrResult(id)
    }

    override fun deleteAllQrScannedResults() {
        qrResultDatabase.getQrDao().deleteAllScannedResults()
    }

    override fun deleteAllFavouriteQrScannedResults() {
         qrResultDatabase.getQrDao().deleteAllFavouriteResults()
    }
}