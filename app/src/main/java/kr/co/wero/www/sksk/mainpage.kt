package kr.co.wero.www.sksk

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_mainpage.*
import java.io.IOException
import java.io.InputStream
import java.util.*


class mainpage : AppCompatActivity() {

    private var mFlag = false
    private val mHandler: Handler = Handler()
    lateinit var drawerlayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar:androidx.appcompat.widget.Toolbar




    companion object {
        private var mTxtReceive: TextView? = null
        private var mReadThread: ReadInput? = null
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
        lateinit var m_name: String
        lateinit var heartbeatstr : String



    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)


        m_address = intent.getStringExtra(BluetoothSearch2.EXTRA_ADDRESS)



        ConnectToDevice(this).execute()

        mTxtReceive = findViewById<View>(R.id.heartbeat) as TextView
        drawerlayout=findViewById(R.id.drawerlayout)
        navigationView=findViewById(R.id.nav_view)
        toolbar=findViewById(R.id.mytoolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navigationView.bringToFront()
        var toggle=
            ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        message.setOnClickListener {
            val intent=Intent(applicationContext,chating_final::class.java)
            startActivity(intent)
        }
        detail.setOnClickListener {
            detail.visibility=View.GONE
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_profileChange->{
                    //Toast.makeText(applicationContext,"프로필 수정 click",Toast.LENGTH_SHORT).show()
                    drawerlayout.closeDrawer(GravityCompat.START)
                    val intent=Intent(applicationContext,ProfileChange::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_like->{
                    //Toast.makeText(applicationContext,"좋아요 click",Toast.LENGTH_SHORT).show()
                    drawerlayout.closeDrawer(GravityCompat.START)
                    val intent=Intent(applicationContext,like_final::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_heartRacing->{
                    //Toast.makeText(applicationContext,"심쿵 click",Toast.LENGTH_SHORT).show()
                    drawerlayout.closeDrawer(GravityCompat.START)
                    val intent=Intent(applicationContext,heartRacing_final::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_game->{
                    //Toast.makeText(applicationContext,"게임 click",Toast.LENGTH_SHORT).show()
                    drawerlayout.closeDrawer(GravityCompat.START)
                    val intent=Intent(applicationContext,GameList::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_setting->{
                    //Toast.makeText(applicationContext,"설정 click",Toast.LENGTH_SHORT).show()
                    drawerlayout.closeDrawer(GravityCompat.START)
                    val intent=Intent(applicationContext,settings::class.java)
                    startActivity(intent)
                    true
                }
                else->{
                    false
                }
            }
        }

        var is1=true

        var list=ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        //profile.setImageResource(R.layout.profile)
        profile.setImageResource(R.drawable.profile_1)

//        val profile=findViewById<ViewPager>(R.id.profile)
//        var adapter=ProfileAdapter(list)
//        profile.adapter=ProfileAdapter(list)

        profile.setOnClickListener{
//            val intent=Intent(applicationContext,OtherProfile::class.java)
//            startActivity(intent)
            detail.visibility=View.VISIBLE
        }

        back_btn.setOnClickListener {
            detail.visibility=View.GONE
        }

        btn_like.setOnClickListener {
            heart_event.visibility=View.VISIBLE
            Handler().postDelayed({
                heart_event.visibility=View.GONE
                if(is1){
                    profile.setImageResource(R.drawable.profile_2)
                    is1=false
                }else{
                    profile.setImageResource(R.drawable.profile_1)
                    is1=true
                }
            },1000)
        }

        btn_refuse.setOnClickListener {
            if(is1){
                profile.setImageResource(R.drawable.profile_2)
                is1=false
            }else{
                profile.setImageResource(R.drawable.profile_1)
                is1=true
            }
        }




    }
    override fun onBackPressed() {
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
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

    private fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
            try{
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {

                m_isConnected = true
                mReadThread = ReadInput()
            }
            m_progress.dismiss()
        }
    }

    private class ReadInput : Runnable {
        private var bStop = false
        private val t: Thread



        val isRunning: Boolean
            get() = t.isAlive

        override fun run() {
            val inputStream: InputStream

            try {
                inputStream = m_bluetoothSocket!!.inputStream
                while (!bStop) {
                    val buffer = ByteArray(256)
                    if (inputStream.available() > 0) {
                        inputStream.read(buffer)

                        val strInput = String(buffer, 0, 4)


                        /*
                         * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
                         */
                        mTxtReceive?.post(Runnable {
                            mTxtReceive!!.text = strInput
                            heartbeatstr = strInput


                            /*
                           val mSpannableString = SpannableString(mTxtReceive!!.text)
                           val mRed = ForegroundColorSpan(Color.RED)
                           val mGreen = ForegroundColorSpan(Color.GREEN)
                           val mBlack = ForegroundColorSpan(Color.BLACK)

                           if(heartbeat>=80)
                           {
                               mSpannableString.setSpan(mRed,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                           }
                           else if(heartbeat<80&&heartbeat>=60)
                           {
                               mSpannableString.setSpan(mGreen,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                           }
                           else if(heartbeat<60)
                           {
                               mSpannableString.setSpan(mBlack,0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                           }
                           */

                            /*
                                                    int txtLength = mTxtReceive.getEditableText().length();
                                                    if(txtLength > mMaxChars){
                                                        mTxtReceive.getEditableText().delete(0, txtLength - mMaxChars);
                                                    }
                    */
                        })
                    }
                    Thread.sleep(10)
                }
            } catch (e: IOException) {
// TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: InterruptedException) {
// TODO Auto-generated catch block
                e.printStackTrace()
            }
        }

        fun stop() {
            bStop = true
        }

        init {
            t = Thread(this, "Input Thread")
            t.start()
        }
    }


}