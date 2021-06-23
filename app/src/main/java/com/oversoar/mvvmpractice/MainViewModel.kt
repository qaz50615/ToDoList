package com.oversoar.mvvmpractice

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainViewModel(application: Application): AndroidViewModel(application) {
    val addressSet: MutableLiveData<List<CustomerData>> = MutableLiveData()
    private val address = DataModel(getApplication())
//    val error
//    val success api請求

    fun initData() {
        if (addressSet.value?.isNotEmpty() == true) return

        var jsonData = if (address.getTmpData().isEmpty()) {
            if (address.getDefaultData().isEmpty()) address.initData() else address.getDefaultData()
        } else address.getTmpData()

        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var data = Gson().fromJson<List<CustomerData>>(jsonData, listType)
        addressSet.value = data
        Log.i("data",jsonData)
    }

    fun recoverData() {
        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var data = Gson().fromJson<List<CustomerData>>(address.getDefaultData(), listType)

        addressSet.value = data
    }

    fun loadData(dd:Int) {
        var jsonData = if (address.getTmpData().isEmpty()) {
            address.getDefaultData()
        } else {
            address.getTmpData()
        }
        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var data = Gson().fromJson<List<CustomerData>>(jsonData, listType)

        when(dd) {
            0 -> data = data.filterNot { it.delivery_date == "P" || it.delivery_date == "O" || it.delivery_date == "U" || it.delivery_date == "A"}
            1 -> data = data.filterNot { it.delivery_date == "F" || it.delivery_date == "I" || it.delivery_date == "N" }
            2 -> data = data.filterNot { it.delivery_date == "H" || it.delivery_date == "A" || it.delivery_date == "O" || it.delivery_date == "U" }
            3 -> data = data.filterNot { it.delivery_date == "F" || it.delivery_date == "G" || it.delivery_date == "H" }
            4 -> data = data.filterNot { it.delivery_date == "O" || it.delivery_date == "U" }
            5 ->
            data = data.filterNot {
                it.delivery_date == "A" || it.delivery_date == "J" || it.delivery_date == "D" || it.delivery_date == "F" || it.delivery_date == "L"
                        || it.delivery_date == "G" || it.delivery_date == "I" || it.delivery_date == "K" || it.delivery_date == "X"
            }
            6 -> data = data.filter { it.delivery_date?:"" == "" || it.delivery_date == "E"}
        }

        addressSet.value = data
        Log.i("data", data.toString())
    }

    fun done(pos:Int) {
        val list = addressSet.value!!
        addressSet.value = list - list[pos]
    }

    fun delete(pos:Int) {
        val data = address.getDefaultData()
        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var list = Gson().fromJson<List<CustomerData>>(data, listType)
        val tmpList = addressSet.value!!
        addressSet.value = tmpList-tmpList[pos]
        address.saveDefaultData(Gson().toJson(list-list[pos]))
    }

    fun edit(editData:String,type:String,pos:Int) {
        val data = address.getDefaultData()
        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var list = Gson().fromJson<List<CustomerData>>(data, listType)
        var tmpList = addressSet.value!!.toMutableList()
        when (type) {
            "address" -> {
                list[pos].address = editData
                tmpList[pos].address = editData
            }
            "flavor" -> {
                list[pos].flavor = editData
                tmpList[pos].flavor = editData
            }
            "flavorExtra" -> {
                list[pos].flavorExtra = editData
                tmpList[pos].flavorExtra = editData
            }
            "delivery_date" -> {
                list[pos].delivery_date = editData
                tmpList[pos].delivery_date = editData
            }
            "ice" -> {
                list[pos].ice = editData
                tmpList[pos].ice = editData
            }
        }
        addressSet.value = tmpList
        address.saveDefaultData(Gson().toJson(list))
        Log.i("jsonData",Gson().toJson(list))
    }

    fun add(pos:Int) {
        val data = address.getDefaultData()
        val listType = object : TypeToken<List<CustomerData>>() {}.type
        var list = Gson().fromJson<List<CustomerData>>(data, listType)
        var subList = mutableListOf<CustomerData>()

        if (list.size > pos+1) {
            for ( i in 0 .. pos) {
                subList.add(list[i])
            }
            subList.add(CustomerData("","","",""))
            for (i in pos+1 until list.size) {
                subList.add(list[i])
            }
        } else {
            subList.addAll(list)
            subList.add(CustomerData("","","",""))
        }

        address.saveDefaultData(Gson().toJson(subList))
        Log.i("jsonData",Gson().toJson(subList))

        val tmpList = addressSet.value!!
        subList.clear()
        if (tmpList.size > pos+1) {
            for ( i in 0 .. pos) {
                subList.add(tmpList[i])
            }
            subList.add(CustomerData("","","",""))
            for (i in pos+1 until tmpList.size) {
                subList.add(tmpList[i])
            }
        } else {
            subList.addAll(tmpList)
            subList.add(CustomerData("","","",""))
        }
        addressSet.value = subList
    }

    fun saveTmpData() {
        address.saveTmpData(Gson().toJson(addressSet.value))
        Log.i("jsonData",Gson().toJson(addressSet.value))
    }

    fun filter (dd:Int,query:String) {
        loadData(dd)
        var data = addressSet.value!!

        if (query.isNotEmpty()) {
            data = data.filter {
                it.flavor?.contains(query)!!
            }
        }

        addressSet.value = data
    }

}