package kr.co.wero.www.sksk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_heart_racing_final.*
import kotlinx.android.synthetic.main.activity_my_profile.view.*

class heartRacing_final : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_racing_final)

        var data=ArrayList<Int>()

        data.add(1)
        data.add(2)
        data.add(3)
        data.add(4)
        data.add(5)

        if(data[0]/2==0){
            profile.setImageResource(R.drawable.boy)
        }else{
            profile.setImageResource(R.drawable.boy1)
        }

        total.setText(data.size.toString())

        profile.setOnClickListener {
            if(detail.visibility==View.VISIBLE){
                detail.visibility=View.GONE

            }else{
                detail.visibility=View.VISIBLE
            }
        }

        like.setOnClickListener {
            if(data.size>1){
                event.visibility=View.VISIBLE
                Handler().postDelayed({
                    event.visibility=View.GONE
                },1000)
                data.removeAt(0)
                if(data[0]/2==0){
                    profile.setImageResource(R.drawable.boy)
                }else{
                    profile.setImageResource(R.drawable.boy1)
                }
                total.setText(data.size.toString())
            }
            else if(data.size==1){
                event.visibility=View.VISIBLE
                Handler().postDelayed({
                    event.visibility=View.GONE
                },1000)
                data.removeAt(0)
                total.setText(data.size.toString())
            }
            else{
                Toast.makeText(applicationContext,"목록이 비워졌습니다.",Toast.LENGTH_SHORT).show()
            }
        }
        refuse.setOnClickListener {
            if(data.size>1){
                data.removeAt(0)
                if(data[0]/2==0){
                    profile.setImageResource(R.drawable.boy)
                }else{
                    profile.setImageResource(R.drawable.boy1)
                }
                total.setText(data.size.toString())
            }
            else if(data.size==1){
                data.removeAt(0)
                total.setText(data.size.toString())
            }
            else{
                Toast.makeText(applicationContext,"목록이 비워졌습니다.",Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(detail.visibility==View.VISIBLE){
            detail.visibility=View.GONE
            return true
        }else{
            return false
        }

    }


}