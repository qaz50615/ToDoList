package com.oversoar.mvvmpractice

import android.content.Context
import android.content.SharedPreferences

class DataModel(val context: Context) {

    class CustomerData (
            var address: String? = null,
            var flavor: String? = null,
            var nums: Int? = null,
            var delivery_date: String? = null
    )

    var sp: SharedPreferences = context.getSharedPreferences("Data", Context.MODE_PRIVATE)

    fun getCustomerData() = sp.getString("customer", "")!!
    fun saveCustomerData(data:String) = sp.edit().putString("customer",data)!!

}
