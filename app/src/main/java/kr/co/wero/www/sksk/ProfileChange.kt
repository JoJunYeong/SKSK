package kr.co.wero.www.sksk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_profile_change.*

class ProfileChange : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_change)

        saveBtn.setOnClickListener {
            finish()
        }

        btn_back.setOnClickListener{
            finish()
        }
    }
}