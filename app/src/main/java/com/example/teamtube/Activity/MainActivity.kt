package com.example.teamtube

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.teamtube.Adapter.ViewPager2Adapter
import com.example.teamtube.Fragment.HomeFragment
import com.example.teamtube.Fragment.MyVideoFragment
import com.example.teamtube.Fragment.SearchFragment
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var likedItems: ArrayList<ChannelModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        initViewPager()
        loadData()
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
                    tab.icon = resources.getDrawable(R.drawable.red_ghost, null)
                }
                1 -> {
                    tab.text = "Search"
                    tab.icon = resources.getDrawable(R.drawable.yellow_ghost, null)
                }
                2 -> {
                    tab.text = "My Video"
                    tab.icon = resources.getDrawable(R.drawable.blue_ghost, null)
                }
            }
        }.attach()
    }

    private fun saveData() {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        val edit = pref.edit()
        val gson = Gson()
        val json = gson.toJson(likedItems)
        edit.putString("List", json)
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        val gson = Gson()
        val json = pref.getString("List", null)
        val type: Type = object : TypeToken<ArrayList<ChannelModel>>(){}.type
        likedItems = gson.fromJson(json, type) ?: ArrayList<ChannelModel>()
    }

    fun addLikedItem(item: ChannelModel) {
        if(!likedItems.contains(item)) {
            likedItems.add(item)
            saveData()
            val titleList = likedItems.map { it.title }
            Log.d("LikedList", "List: $titleList")
        }
    }
    fun removeLikedItem(item: ChannelModel) {
        likedItems.remove(item)
        saveData()
        val titleList = likedItems.map { it.title }
        Log.d("LikedList", "List: $titleList")
    }
}