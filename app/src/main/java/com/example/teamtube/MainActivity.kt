package com.example.teamtube

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.teamtube.Adapter.ViewPager2Adapter
import com.example.teamtube.ChannelData.Fragment.HomeFragment
import com.example.teamtube.ChannelData.Fragment.MyVideoFragment
import com.example.teamtube.ChannelData.Fragment.SearchFragment
import com.example.teamtube.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        initViewPager()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(HomeFragment())
        viewPager2Adatper.addFragment(SearchFragment())
        viewPager2Adatper.addFragment(MyVideoFragment())

        //Adapter 연결
        binding.vpViewpagerMain.apply {
            adapter = viewPager2Adatper
            isUserInputEnabled = false

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }

        //ViewPager, TabLayout 연결 부분
        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            Log.e("YMC", "ViewPager position: ${position}")
            when (position) {
                0 -> {
                    tab.text = "Home"
                    tab.icon = resources.getDrawable(R.drawable.icon_nav_home, null)
                }
                1 -> {
                    tab.text = "Search"
                    tab.icon = resources.getDrawable(R.drawable.icon_nav_search, null)
                }
                2 -> {
                    tab.text = "My Video"
                    tab.icon = resources.getDrawable(R.drawable.icon_nav_myvideo, null)
                }
            }
        }.attach()
    }
}