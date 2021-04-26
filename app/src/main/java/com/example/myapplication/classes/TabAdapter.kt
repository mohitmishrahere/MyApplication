package com.example.myapplication.classes

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.PhotosFragment
import com.example.myapplication.PostsFragment
import com.example.myapplication.TodosFragment

class TabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return totalTabs
    }


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {

            }
            1 -> {
                return PostsFragment()
            }
            2 -> {
                // val movieFragment = MovieFragment()
                return PhotosFragment()
            }
            3 -> {
                // val movieFragment = MovieFragment()
                return TodosFragment()
            }
        }
        return Fragment(position)
    }

}
