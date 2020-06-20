package kr.co.wero.www.sksk

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.activity_my_profile.view.*

class GameList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        var data=ArrayList<Int>()

        data.add(1)
        data.add(2)
        data.add(3)
        data.add(4)
        data.add(5)
        data.add(6)

        total.setText(data.size.toString())
        now.setText("1")



        chat_btn.setOnClickListener {
            val intent=Intent(applicationContext,Message::class.java)
            startActivity((intent))
        }

        back_btn.setOnClickListener {
            finish()
        }
    }

    interface ItemTouchHelperListener{
        fun onItemMove(from_position:Int, to_position:Int):Boolean
        fun onItemSwipe(position:Int)
        fun onLeftClick( position:Int, viewHolder:RecyclerView.ViewHolder);
        fun onRightClick( position:Int, viewHolder: RecyclerView.ViewHolder)

    }
}