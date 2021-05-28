package com.oversoar.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel:MainViewModel
    lateinit var adapter:CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = MainViewModel(DataModel(this))
        adapter = CustomerAdapter(mainViewModel)
        recyclerView.adapter = adapter

        button2.setOnClickListener {
            mainViewModel.add(DataModel.CustomerData(adsEt.text.toString(),flavorEt.text.toString(),numsEt.text.toString().toInt(),deliveryDateEt.text.toString()))
            recyclerView.scrollToPosition(adapter.addressSet.size-1)
        }

        searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.filter(query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.filter(newText?:"")
                return true
            }

        })

        mainViewModel.addressSet.observe(this, Observer<List<DataModel.CustomerData>> {
            adapter.updateData(it)
        })

        mainViewModel.initData()

    }
}