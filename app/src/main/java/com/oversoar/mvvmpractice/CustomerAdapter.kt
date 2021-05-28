package com.oversoar.mvvmpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter

class CustomerAdapter(private val viewModel: MainViewModel): RecyclerSwipeAdapter<CustomerAdapter.ViewHolder>() {

    lateinit var addressSet:List<DataModel.CustomerData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    override fun getItemCount(): Int {
        return addressSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
        mItemManger.bindView(holder.itemView, position)

        holder.adsEt.text = addressSet[position].address
        holder.numEt.text = addressSet[position].nums.toString()
        holder.flavorEt.text = addressSet[position].flavor
        holder.ddEt.text = addressSet[position].delivery_date

        holder.copy.setOnClickListener {
            viewModel.copy(addressSet[position], position)
        }
        holder.delete.setOnClickListener {
            viewModel.delete(position)
        }
        holder.adsEt.setOnClickListener {
            holder.adsEt.isFocusable = true
            holder.adsEt.isFocusableInTouchMode = true
        }
        holder.numEt.setOnClickListener {
            holder.numEt.isFocusable = true
            holder.numEt.isFocusableInTouchMode = true
        }
        holder.flavorEt.setOnClickListener {
            holder.flavorEt.isFocusable = true
            holder.flavorEt.isFocusableInTouchMode = true
        }
        holder.ddEt.setOnClickListener {
            holder.ddEt.isFocusable = true
            holder.ddEt.isFocusableInTouchMode = true
        }

        holder.adsEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.edit(holder.adsEt.text.toString(), "address", position)
                holder.adsEt.isFocusable = false
                holder.adsEt.isFocusableInTouchMode = false
            }
            return@setOnEditorActionListener false
        }
        holder.numEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.edit(holder.numEt.text.toString(), "nums", position)
                holder.numEt.isFocusable = false
                holder.numEt.isFocusableInTouchMode = false
            }
            return@setOnEditorActionListener false
        }
        holder.flavorEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.edit(holder.flavorEt.text.toString(), "flavor", position)
                holder.flavorEt.isFocusable = false
                holder.flavorEt.isFocusableInTouchMode = false
            }
            return@setOnEditorActionListener false
        }
        holder.ddEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.edit(holder.ddEt.text.toString(), "delivery_date", position)
                holder.ddEt.isFocusable = false
                holder.ddEt.isFocusableInTouchMode = false
            }
            return@setOnEditorActionListener false
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout = itemView.findViewById<SwipeLayout>(R.id.swipe)
        val copy = itemView.findViewById<TextView>(R.id.copyNext)
        val delete = itemView.findViewById<TextView>(R.id.delete)
        val adsEt = itemView.findViewById<TextView>(R.id.item_adsEt)
        val numEt = itemView.findViewById<TextView>(R.id.item_numsEt)
        val flavorEt = itemView.findViewById<TextView>(R.id.item_flavorEt)
        val ddEt = itemView.findViewById<TextView>(R.id.item_deliveryDateEt)
    }

    fun updateData(data:List<DataModel.CustomerData>) {
        this.addressSet = data
        notifyDataSetChanged()
    }

}
