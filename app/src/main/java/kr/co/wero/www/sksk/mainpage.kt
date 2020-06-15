package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mainpage.*




class mainpage : AppCompatActivity() {

    private var mFlag = false
    private val mHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)



        profile_btn.setOnClickListener {
            val settings= Intent(applicationContext,settings::class.java)
            startActivity(settings)
        }

        var list=ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)

        val profile=findViewById<ViewPager>(R.id.profile)
        profile.adapter=ProfileAdapter(list)

        profile.setOnClickListener{
            val intent=Intent(applicationContext,OtherProfile::class.java)
            startActivity(intent)
        }


        chat_btn.setOnClickListener{
            val intent=Intent(applicationContext,Message::class.java)
            startActivity(intent)
        }

        btn_game.setOnClickListener{
            val intent=Intent(applicationContext,GameList::class.java)
            startActivity(intent)
        }




    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!mFlag) {
                Toast.makeText(this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()    // 종료안내 toast 를 출력
                mFlag = true
                mHandler.sendEmptyMessageDelayed(0, 2000)    // 2000ms 만큼 딜레이
                Handler().postDelayed({
                    // todo
                    mFlag = false
                },2000)
                return false
            } else {
                // 앱 종료 code
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid())
                return true
            }
        }
        else return super.onKeyDown(keyCode, event)
    }


}