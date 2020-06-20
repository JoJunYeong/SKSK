package kr.co.wero.www.sksk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_chating_final.*
import kotlinx.android.synthetic.main.activity_like_final.*
import kotlinx.android.synthetic.main.activity_like_final.btn_back
import kotlinx.android.synthetic.main.activity_like_final.detail

class like_final : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_final)

        btn_back.setOnClickListener {
            finish()
        }

        other1.setOnClickListener {
            detail.visibility= View.VISIBLE
        }
        other2.setOnClickListener {
            detail.visibility= View.VISIBLE
        }
        other3.setOnClickListener {
            detail.visibility= View.VISIBLE
        }
        other4.setOnClickListener {
            detail.visibility= View.VISIBLE
        }

        back.setOnClickListener {
            detail.visibility=View.GONE
        }
    }
}