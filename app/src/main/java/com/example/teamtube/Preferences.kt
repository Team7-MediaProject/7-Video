package com.example.teamtube

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.teamtube.Model.ChannelModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object Preferences {
/*    fun saveVideoData(context: Context, item : ChannelModel) {
        val pref = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val edit = pref.edit()
        val gson = GsonBuilder().create()

        edit.putString(item.thumbnails, gson.toJson(item))
        edit.apply()
    }

    fun getSaveDataList(context: Context) : ArrayList<ChannelModel> {
        val prefs = context.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val allEntries: Map<String, *> = prefs.all
        val bookmarkItems = ArrayList<ChannelModel>()
        val gson = GsonBuilder().create()
        for ((key, value) in allEntries) {
            val item = gson.fromJson(value as String, ChannelModel::class.java)
            bookmarkItems.add(item)
        }
        return bookmarkItems
    }*/

    private const val PREF_NAME = "MyVideoPreferences"

    fun addLikedItem(context : Context, item: ChannelModel) {
        val likedVideos = getLikedItems(context).toMutableList()
        likedVideos.add(item)

        val jsonString = Gson().toJson(likedVideos)
        val editor = getPreferences(context).edit()
        editor.putString("likedItems", jsonString)
        editor.apply()
    }

    fun getLikedItems(context:Context): List<ChannelModel> {
        val jsonString = getPreferences(context).getString("likedItems", "[]")
        val type = object : TypeToken<List<ChannelModel>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}