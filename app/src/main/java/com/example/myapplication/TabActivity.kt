package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.classes.TabAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout

lateinit var imgFront : ImageView
lateinit var imgBack : ImageButton

class TabActivity : AppCompatActivity() {
    lateinit var tabLayout:TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager)
        imgFront = findViewById(R.id.imgFront)
        imgBack = findViewById(R.id.imgBack)

//        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_baseline_camera_alt_24))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("POSTS"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("PHOTOS"))
//        tabLayout!!.addTab(tabLayout!!.newTab().setText("TODOS"))
//        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.currentItem = 1
        tabLayout.setTabTextColors(Color.parseColor("#82afa8"), Color.parseColor("#FFFFFF"))

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position

                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
                tabLayout.setTabTextColors(Color.parseColor("#82afa8"), Color.parseColor("#FFFFFF"))
                tabLayout.setTabIconTintResource(R.color.white)
                if (tab!!.position != 0) {
                    tabLayout.setTabIconTintResource(R.color.tabUn)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        imgBack.setOnClickListener(View.OnClickListener {
            imgBack.visibility = View.GONE
            imgFront.visibility = View.GONE
        })

    }
    override fun onBackPressed() {
        if(imgFront.visibility == View.VISIBLE){
            imgFront.visibility = View.GONE
            imgBack.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }
}