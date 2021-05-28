package com.oversoar.mvvmpractice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel(private val address:DataModel): ViewModel() {
    val addressSet: MutableLiveData<List<DataModel.CustomerData>> = MutableLiveData()
//    val error
//    val success api請求

    fun initData() {
        val listType = object : TypeToken<List<DataModel.CustomerData>>() {}.type
        addressSet.value = Gson().fromJson(address.getCustomerData(),listType)?: listOf()
    }

    fun delete(pos:Int) {
        val list = addressSet.value!!
        addressSet.value = list - list[pos]
        address.saveCustomerData(Gson().toJson(list))
    }

    fun add(data:DataModel.CustomerData) {
        val list = addressSet.value!!
        addressSet.value = list + data
        address.saveCustomerData(Gson().toJson(list))
    }

    fun edit(editData:String,type:String,pos:Int) {
        val list = addressSet.value!!
        var copyData = list[pos]
        when (type) {
            "address" -> list[pos].address = editData
            "nums" -> list[pos].nums = editData.toInt()
            "flavor" -> list[pos].flavor = editData
            "delivery_date" -> list[pos].delivery_date = editData
        }
        addressSet.value = list
        address.saveCustomerData(Gson().toJson(list))
        Log.i("jsonData",Gson().toJson(list))
    }

    fun copy(data:DataModel.CustomerData,pos:Int) {
        val list = addressSet.value!!.toMutableList()
        var subList = mutableListOf<DataModel.CustomerData>()

        if (list.size > pos+1) {
            for ( i in 0 .. pos) {
                subList.add(list[i])
            }
            subList.add(data)
            for (i in pos+1 until list.size) {
                subList.add(list[i])
            }
        } else {
            list.map {
                subList.add(it.)
            }
            subList.add(data)
        }

        addressSet.value = subList
        address.saveCustomerData(Gson().toJson(subList))
        Log.i("jsonData",Gson().toJson(subList))
    }

    fun filter (query:String) {
        var jsonData = address.getCustomerData()
        val listType = object : TypeToken<DataModel.CustomerData>() {}.type
        var data = Gson().fromJson<List<DataModel.CustomerData>>(jsonData,listType)

        if (query.isNotEmpty()) {
            data = data.filter {
                it.address?.contains(query)!!
            }
        }

        addressSet.value = data
    }

}