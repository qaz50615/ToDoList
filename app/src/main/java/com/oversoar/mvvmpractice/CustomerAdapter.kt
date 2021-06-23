package com.oversoar.mvvmpractice

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter

class CustomerAdapter(private val context:Context,private val viewModel: MainViewModel): RecyclerSwipeAdapter<CustomerAdapter.ViewHolder>() {

    lateinit var data:List<CustomerData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
        mItemManger.bindView(holder.itemView, position)

        holder.noTv.text = "${position + 1}"
        holder.adsEt.setText(data[position].address)
        holder.flavorEt.setText(data[position].flavor)
        holder.ddEt.setText(data[position].delivery_date ?: "")
        holder.iceEt.setText(data[position].ice ?: "")

        holder.add.setOnClickListener {
            viewModel.add(position)
            mItemManger.closeAllItems()
        }
        holder.done.setOnClickListener {
            viewModel.done(position)
            mItemManger.closeAllItems()
        }
        holder.adsEt.setOnClickListener {
            holder.adsEt.isFocusable = true
            holder.adsEt.isFocusableInTouchMode = true
        }
        holder.flavorEt.setOnClickListener {
            holder.flavorEt.isFocusable = true
            holder.flavorEt.isFocusableInTouchMode = true
        }
        holder.ddEt.setOnClickListener {
            holder.ddEt.isFocusable = true
            holder.ddEt.isFocusableInTouchMode = true
        }
        holder.iceEt.setOnClickListener {
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
        holder.iceEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.edit(holder.iceEt.text.toString(), "ice", position)
                holder.ddEt.isFocusable = false
                holder.ddEt.isFocusableInTouchMode = false
            }
            return@setOnEditorActionListener false
        }

        holder.swipeLayout.setOnLongClickListener {
            AlertDialog.Builder(context)
                    .setMessage("是否確定刪除本筆資料？")
                    .setPositiveButton("確定") { d,w ->
                        viewModel.delete(position)
                    }
                    .setNegativeButton("取消") {d,w -> d.cancel()}
                    .show()
            return@setOnLongClickListener true
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val swipeLayout = itemView.findViewById<SwipeLayout>(R.id.swipe)
        val add = itemView.findViewById<TextView>(R.id.add)
        val done = itemView.findViewById<TextView>(R.id.done)
        val noTv = itemView.findViewById<TextView>(R.id.item_no)
        val adsEt = itemView.findViewById<EditText>(R.id.item_adsEt)
        val flavorEt = itemView.findViewById<EditText>(R.id.item_flavorEt)
        val ddEt = itemView.findViewById<EditText>(R.id.item_deliveryDateEt)
        val iceEt = itemView.findViewById<EditText>(R.id.item_ice)
    }

    fun updateData(data:List<CustomerData>) {
        this.data = data
        notifyDataSetChanged()
    }

}
