package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setting.setOnClickListener {
            val settings= Intent(applicationContext,settings::class.java)
            startActivity(settings)
        }

        back_btn.setOnClickListener{
            finish()
        }

        changeProfile.setOnClickListener {
            val intent=Intent(applicationContext,ProfileChange::class.java)
            startActivity(intent)
        }
    }
}