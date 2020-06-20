package kr.co.wero.www.sksk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentuse.ChattingAdapter
import com.example.fragmentuse.RankAdapter
import kotlinx.android.synthetic.main.activity_chating_final.*

class chating_final : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chating_final)

        btn_back.setOnClickListener{
            finish()
        }

        val recycler=findViewById<RecyclerView>(R.id.messageCount)
        recycler.layoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
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

        recycler.adapter= ChattingAdapter(list)

        var re=findViewById<RecyclerView>(R.id.rank)
        re.layoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)
        var adapter= RankAdapter(list)
        adapter.itemclick=object :RankAdapter.OnItemClick{
            override fun itemclick(
                viewHolder: RankAdapter.MyViewHolder,
                view: View,
                data: Int,
                position: Int
            ) {
                detail.visibility=View.VISIBLE
            }

        }
        back.setOnClickListener {
            detail.visibility=View.GONE
        }
        re.adapter=adapter
    }
}