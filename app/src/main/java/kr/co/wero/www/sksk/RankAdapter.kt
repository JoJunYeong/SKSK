package com.example.fragmentuse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.co.wero.www.sksk.R

class RankAdapter(var items:ArrayList<Int>):RecyclerView.Adapter<RankAdapter.MyViewHolder>() {

    lateinit var itemclick:OnItemClick
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var pic=itemView.findViewById<ImageView>(R.id.profile)
        init{
            pic.setOnClickListener {
                itemclick.itemclick(this,it,items[adapterPosition],adapterPosition)

            }
        }
    }

    interface OnItemClick{
        fun itemclick(viewHolder: MyViewHolder,view:View,data:Int, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.rank_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }
}