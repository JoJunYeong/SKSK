package kr.co.wero.www.sksk

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_mainpage.*
import java.io.IOException
import java.io.InputStream
import java.util.*


class mainpage : AppCompatActivity() {

    private var mFlag = false
    private val mHandler: Handler = Handler()




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




    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainpage)


        m_address = intent.getStringExtra(BluetoothSearch2.EXTRA_NAME)

        ConnectToDevice(this).execute()

        mTxtReceive = findViewById<View>(R.id.heartbeat) as TextView


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