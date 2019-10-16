package com.example.qrcodescanner.ui.ui.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.qrcodescanner.R
import com.example.qrcodescanner.ui.db.database.QrResultDatabase
import com.example.qrcodescanner.ui.db.entites.QrResult
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPagerAdapter()
        setBottomNavigation()
        setViewPagerListener()

        val qrResult =
            QrResult(result = "Dummy text",resultType = "TEXT",favourite = false,calendar = Calendar.getInstance())
        QrResultDatabase.getAppDatabase(this)?.getQrDao()?.insertQrResult(qrResult)
    }

    private fun setViewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = when(position){
                   0 ->   R.id.scanMenuId
                   1 ->   R.id.recentScanMenuId
                   2 ->   R.id.favouritesMenuId
                   else -> R.id.scanMenuId
               }
            }

        })
    }

    private fun setBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId){
              R.id.scanMenuId ->  0
              R.id.recentScanMenuId ->  1
              R.id.favouritesMenuId ->  2
              else -> 0
           }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerAdapter() {
        viewPager.adapter = MainPagerAdapter(supportFragmentManager)
    }
}
