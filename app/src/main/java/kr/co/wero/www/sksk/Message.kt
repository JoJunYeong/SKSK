package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.fragmentuse.MyFragmentAdapter
import kotlinx.android.synthetic.main.activity_message.*

class Message : AppCompatActivity() {
    lateinit var mPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        mPager=findViewById(R.id.viewpager)

        val fragmentAdapter= MyFragmentAdapter(supportFragmentManager)
        mPager.adapter=fragmentAdapter

        tab.setupWithViewPager(viewpager)

        message_back.setOnClickListener {
            val intent= Intent(applicationContext,mainpage::class.java)
            startActivity(intent)

        }
    }
}