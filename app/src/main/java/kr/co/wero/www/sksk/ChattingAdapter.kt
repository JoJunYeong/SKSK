package com.example.fragmentuse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.wero.www.sksk.R

class ChattingAdapter(var items:ArrayList<Int>):
    RecyclerView.Adapter<ChattingAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemsView: View):RecyclerView.ViewHolder(itemsView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.chating_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}