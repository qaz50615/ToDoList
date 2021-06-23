package com.oversoar.mvvmpractice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var adapter:CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        adapter = CustomerAdapter(this,mainViewModel)
        recyclerView.adapter = adapter

        newBtn.setOnClickListener {
            mainViewModel.add(mainViewModel.addressSet.value!!.size-1)
            recyclerView.scrollToPosition(mainViewModel.addressSet.value!!.size-1)
        }

        swipe.setOnRefreshListener {
            mainViewModel.recoverData()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                mainViewModel.loadData(tab?.position?:0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                mainViewModel.loadData(tab?.position?:0)
            }

        })

        searchView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainViewModel.filter(tabLayout?.selectedTabPosition?:0,query?:"")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.filter(tabLayout?.selectedTabPosition?:0,newText?:"")
                return true
            }

        })

        mainViewModel.addressSet.observe(this, Observer<List<CustomerData>> {
            if (swipe.isRefreshing) swipe.isRefreshing = false
            adapter.updateData(it)
        })

        mainViewModel.initData()

    }

    override fun onStop() {
        super.onStop()
        mainViewModel.saveTmpData()
    }
}