package kr.co.wero.www.sksk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Process.myPid
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var mFlag = false
    private val mHandler: Handler = Handler()
    var install_first = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intro()
        Handler().postDelayed({
            // todo
            init()
        },2000)





    }

    fun intro(){
        val intro_intent= Intent(applicationContext,intro::class.java)

        intro_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intro_intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK))
        startActivity(intro_intent)
    }


    fun init(){
        if(install_first){
            //install first
            val first= Intent(applicationContext,installfirst::class.java)
            first.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            first.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK))
            startActivity(first);
        }
        else{
            //already installed
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

    override fun onDestroy() {
        super.onDestroy()
    }
}
