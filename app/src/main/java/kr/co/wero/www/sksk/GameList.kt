package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.activity_mainpage.*
import kotlinx.android.synthetic.main.activity_mainpage.chat_btn
import kotlinx.android.synthetic.main.activity_mainpage.profile_btn

class GameList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        chat_btn.setOnClickListener {
            val intent=Intent(applicationContext,Message::class.java)
            startActivity((intent))
        }

        profile_btn.setOnClickListener {

        }

        back_Btn.setOnClickListener {
            val intent=Intent(applicationContext,mainpage::class.java)
            startActivity(intent)
        }
    }
}