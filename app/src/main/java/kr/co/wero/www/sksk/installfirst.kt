package kr.co.wero.www.sksk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_installfirst.*

class installfirst : AppCompatActivity() {
    private var mFlag = false
    private val mHandler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_installfirst)

        skip_btn.setOnClickListener{
            val login=Intent(applicationContext,login::class.java)
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            login.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK))
            startActivity(login)
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