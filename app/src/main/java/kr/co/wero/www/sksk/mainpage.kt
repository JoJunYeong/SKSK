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
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.AsyncTask
import java.util.*
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException



class mainpage : AppCompatActivity() {

    private var mFlag = false
    private val mHandler: Handler = Handler()

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
        lateinit var m_name: String
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)


        m_address = intent.getStringExtra(BluetoothSearch2.EXTRA_ADDRESS)


/*
        profile_btn.setOnClickListener {
            /*val settings= Intent(applicationContext,settings::class.java)
            startActivity(settings)*/
            val intent=Intent(applicationContext,MyProfile::class.java)
            startActivity(intent)
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



*/
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