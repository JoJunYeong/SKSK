package com.example.fragmentuse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_layout.*
import kr.co.wero.www.sksk.OtherProfile
import kr.co.wero.www.sksk.R

class chating:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view= inflater.inflate(R.layout.message_layout,container,false)
        val recycler=view.findViewById<RecyclerView>(R.id.messageCount)
        recycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var list=ArrayList<Int>()
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)
        list.add(1)

        recycler.adapter=ChattingAdapter(list)

        var re=view.findViewById<RecyclerView>(R.id.rank)
        re.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        var adapter=RankAdapter(list)
        re.adapter=adapter

        return view
    }
}