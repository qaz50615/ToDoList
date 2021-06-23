package com.oversoar.mvvmpractice

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class DataModel(private val context: Context) {

    var sp: SharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE)

    fun initData():String {
        val data = context.resources.getString(R.string.default_data)
        saveDefaultData(data)
        return data
    }
    fun getDefaultData() = sp.getString("default", "")!!
    fun saveDefaultData(data:String) = sp.edit().putString("default",data).apply()
    fun getTmpData() = sp.getString("tmp", "")!!
    fun saveTmpData(data:String) = sp.edit().putString("tmp",data).apply()

}
