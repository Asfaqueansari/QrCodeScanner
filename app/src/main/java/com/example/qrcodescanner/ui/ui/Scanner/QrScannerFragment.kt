package com.example.qrcodescanner.ui.ui.Scanner


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.qrcodescanner.R
import com.example.qrcodescanner.ui.db.DBHelper
import com.example.qrcodescanner.ui.db.DBHelperI
import com.example.qrcodescanner.ui.db.database.QrResultDatabase
import com.example.qrcodescanner.ui.ui.dialogs.QrCodeResultDialog
import kotlinx.android.synthetic.main.fragment_qr_scanner.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView


class QrScannerFragment : Fragment(),ZBarScannerView.ResultHandler {
    companion object{
        fun newInstance():QrScannerFragment{
            return QrScannerFragment()
        }
    }

    private lateinit var mView:View
    private lateinit var  scannerView : ZBarScannerView

    private lateinit var resultDialog : QrCodeResultDialog

    private lateinit var dbHelperI: DBHelperI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_qr_scanner, container, false)
        init()
        initViews()
        onClicks()
        return mView.rootView
    }

    private fun init() {
        dbHelperI = DBHelper(QrResultDatabase.getAppDatabase(context!!)!!)
    }

    private fun initViews() {
        initializeQrScanner()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(context!!)
        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener{
            override fun onDismiss() {
                scannerView.resumeCameraPreview(this@QrScannerFragment)
            }
        })
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener{
            if(it.isSelected){
                offFlashLight()
            }else
                onFlashLight()
        }
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    private fun onFlashLight() {
       mView.flashToggle.isSelected = true
       scannerView.flash = true
    }

    private fun initializeQrScanner() {
        scannerView = ZBarScannerView(context!!)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!,R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(context!!,R.color.design_default_color_primary_dark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!,R.color.design_default_color_primary_dark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setAutoFocus(true)
        scannerView.setSquareViewFinder(true)
        scannerView.setResultHandler(this)
        mView.containerScanner.addView(scannerView)
        startQrCamera()
    }

    private fun startQrCamera(){
        scannerView.startCamera()
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)//Register ourselves as a handler for scan resultes
        scannerView.startCamera()//start camera on resume
    }

    override fun onPause() {
        super.onPause()
        scannerView.startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult!!.contents)

    }

    private fun onQrResult(text: String?) {
        if (text.isNullOrEmpty()){
            Toast.makeText(context!!,"Empty Qr Code.",Toast.LENGTH_SHORT).show()
        }else{
            saveToDatabase(text)
        }

    }

    private fun saveToDatabase(result: String) {
        val insertedRowId = dbHelperI.insertQrResult(result)
        val qrResult = dbHelperI.getQrResult(insertedRowId)
        resultDialog.show(qrResult)

    }
}
