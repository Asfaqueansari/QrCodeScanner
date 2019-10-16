package com.example.qrcodescanner.ui.ui.MainActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcodescanner.ui.ui.Scanner.QrScannerFragment
import com.example.qrcodescanner.ui.ui.scanned_history.ScannedHistoryFragment

class MainPagerAdapter(var fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> QrScannerFragment.newInstance()
            1 -> ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.ALL_RESULT)
            2 -> ScannedHistoryFragment.newInstance(ScannedHistoryFragment.ResultListType.FAVOURITE_RESULT)
            else -> QrScannerFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}