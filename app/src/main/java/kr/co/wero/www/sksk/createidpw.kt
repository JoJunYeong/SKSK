package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_createidpw.*
import kotlinx.android.synthetic.main.activity_login.*

class createidpw : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createidpw)

        back.setOnClickListener {
            finish()
        }

        next.setOnClickListener {

            back.visibility = View.GONE        // 버튼 안보이게 하기
            next.visibility = View.GONE
            previous.visibility = View.VISIBLE      // 버튼 보이게 하기
            done.visibility = View.VISIBLE


            create_first_layout.visibility = View.GONE     // 레이아웃 안보이게 하기
            create_second_layout.visibility = View.VISIBLE      // 레이아웃 보이게 하기



        }

        previous.setOnClickListener {

            back.visibility = View.VISIBLE        // 버튼 보이게 하기
            next.visibility = View.VISIBLE
            previous.visibility = View.GONE      // 버튼 안보이게 하기
            done.visibility = View.GONE


            create_first_layout.visibility = View.VISIBLE        // 레이아웃 보이게 하기
            create_second_layout.visibility = View.GONE            // 레이아웃 안보이게 하기



        }



        done.setOnClickListener {

           finish()



        }






    }
}