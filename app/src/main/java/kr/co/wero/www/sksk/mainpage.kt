package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mainpage.*

class mainpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)



        profile_btn.setOnClickListener {
            val SettingsActivity= Intent(applicationContext,SettingsActivity::class.java)
            startActivity(SettingsActivity)
        }




    }
}