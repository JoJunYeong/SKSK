package kr.co.wero.www.sksk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_message.*

class Message : FragmentActivity() {

    private lateinit var mPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        //init()

        mPager=findViewById(R.id.viewpager)


    }

    fun init(){
        layout_tab.addTab(layout_tab.newTab().setText("메시지"))
        layout_tab.addTab(layout_tab.newTab().setText("좋아요"))
        layout_tab.addTab(layout_tab.newTab().setText("심쿵"))
    }
}