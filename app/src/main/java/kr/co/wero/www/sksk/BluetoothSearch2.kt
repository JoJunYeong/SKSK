package kr.co.wero.www.sksk

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.AlteredCharSequence.make
import android.text.BoringLayout.make
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar.make
import java.util.*


class BluetoothSearch2 : AppCompatActivity() {
    private var search: Button? = null
    private var connect: Button? = null
    private var listView: ListView? = null
    var mBTAdapter: BluetoothAdapter? = null
    private var mDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var mBufferSize = 50000 //Default




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_search)

        if (savedInstanceState != null) {
            val list = savedInstanceState.getParcelableArrayList<BluetoothDevice>(DEVICE_LIST)
            if (list != null) {
                initList(list)
                val adapter = listView!!.adapter as MyAdapter
                val selectedIndex = savedInstanceState.getInt(DEVICE_LIST_SELECTED)
                if (selectedIndex != -1) {
                    adapter.setSelectedIndex(selectedIndex)
                    connect!!.isEnabled = true
                }
            } else {
                initList(ArrayList())
            }
        } else {
            initList(ArrayList())
        }
        search!!.setOnClickListener {
            mBTAdapter = BluetoothAdapter.getDefaultAdapter()
            if (mBTAdapter == null) {
                Toast.makeText(
                    applicationContext,
                    "Bluetooth not found",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!mBTAdapter!!.isEnabled) {
                val enableBT = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBT, BT_ENABLE_REQUEST)
            } else {
                SearchDevices().execute()
            }
        }
        connect!!.setOnClickListener {
            val device =
                (listView!!.adapter as MyAdapter).selectedItem
            val intent = Intent(applicationContext, login::class.java)
            //        intent.putExtra(DEVICE_EXTRA, device);
            //        intent.putExtra(DEVICE_UUID, mDeviceUUID.toString());
            //         intent.putExtra(BUFFER_SIZE, mBufferSize);
            startActivity(intent)
        }
    }

    override fun onPause() {
// TODO Auto-generated method stub
        super.onPause()
    }

    override fun onStop() {
// TODO Auto-generated method stub
        super.onStop()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        when (requestCode) {
            BT_ENABLE_REQUEST -> if (resultCode == Activity.RESULT_OK) {
                msg("Bluetooth Enabled successfully")
                SearchDevices().execute()
            } else {
                msg("Bluetooth couldn't be enabled")
            }
            SETTINGS -> {
                val prefs =
                    PreferenceManager.getDefaultSharedPreferences(this)
                val uuid = prefs.getString("prefUuid", "Null")
                mDeviceUUID = UUID.fromString(uuid)
                Log.d(TAG, "UUID: $uuid")
                val bufSize = prefs.getString("prefTextBuffer", "Null")
                mBufferSize = bufSize.toInt()
                val orientation = prefs.getString("prefOrientation", "Null")
                Log.d(TAG, "Orientation: $orientation")
                if (orientation == "Landscape") {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else if (orientation == "Portrait") {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                } else if (orientation == "Auto") {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
                }
            }
            else -> {
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Quick way to call the Toast
     * @param str
     */
    private fun msg(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    /**
     * Initialize the List adapter
     * @param objects
     */
    private fun initList(objects: List<BluetoothDevice>) {
        val adapter =
            MyAdapter(applicationContext, R.layout.list_item, R.id.lstContent, objects)
        listView!!.adapter = adapter
        listView!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                adapter.setSelectedIndex(position)
                connect!!.isEnabled = true
            }
    }

    /**
     * Searches for paired devices. Doesn't do a scan! Only devices which are paired through Settings->Bluetooth
     * will show up with this. I didn't see any need to re-build the wheel over here
     * @author ryder
     */
    private class SearchDevices :
        AsyncTask<Void?, Void?, MutableList<BluetoothDevice?>?>() {
        override fun doInBackground(vararg params: Void?): MutableList<BluetoothDevice?>? {
            val pairedDevices: Set<BluetoothDevice> =
                mBTAdapter.getBondedDevices()
            val listDevices: MutableList<BluetoothDevice> =
                ArrayList()
            for (device in pairedDevices) {
                listDevices.add(device)
            }
            return listDevices
        }

        protected override fun onPostExecute(listDevices: List<BluetoothDevice?>) {
            super.onPostExecute(listDevices)
            if (listDevices.size > 0) {
                val adapter = listview.getAdapter() as BluetoothSearch.MyAdapter
                adapter.replaceItems(listDevices)
            } else {
              Toast.makeText(this, "No paired devices found, please pair your serial BT device and try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Custom adapter to show the current devices in the list. This is a bit of an overkill for this
     * project, but I figured it would be good learning
     * Most of the code is lifted from somewhere but I can't find the link anymore
     * @author ryder
     */
    private inner class MyAdapter(
        private val context: Context,
        resource: Int,
        textViewResourceId: Int,
        var entireList: List<BluetoothDevice>
    ) :
        ArrayAdapter<BluetoothDevice>(context, resource, textViewResourceId, entireList) {
        private var selectedIndex: Int
        private val selectedColor = Color.parseColor("#abcdef")

        fun setSelectedIndex(position: Int) {
            selectedIndex = position
            notifyDataSetChanged()
        }

        val selectedItem: BluetoothDevice
            get() = entireList[selectedIndex]

        override fun getCount(): Int {
            return entireList.size
        }

        override fun getItem(position: Int): BluetoothDevice {
            return entireList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        private inner class ViewHolder {
            var tv: TextView? = null
        }

        fun replaceItems(list: List<BluetoothDevice>) {
            entireList = list
            notifyDataSetChanged()
        }

        override fun getView(
            position: Int,
            convertView: View,
            parent: ViewGroup
        ): View {
            var vi = convertView
            val holder: ViewHolder
            if (convertView == null) {
                vi = LayoutInflater.from(context).inflate(R.layout.list_item, null)
                holder = ViewHolder()
                holder.tv = vi.findViewById<View>(R.id.lstContent) as TextView
                vi.tag = holder
            } else {
                holder = vi.tag as ViewHolder
            }
            if (selectedIndex != -1 && position == selectedIndex) {
                holder.tv!!.setBackgroundColor(selectedColor)
            } else {
                holder.tv!!.setBackgroundColor(Color.WHITE)
            }
            val device = entireList[position]
            holder.tv!!.text = """${device.name}
 ${device.address}"""
            return vi
        }

        init {
            selectedIndex = -1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
// Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.homescreen, menu);
        return true
    }

    companion object {
        private const val BT_ENABLE_REQUEST = 10 // This is the code we use for BT Enable
        private const val SETTINGS = 20
        const val DEVICE_EXTRA = "kr.co.wero.www.sksk.SOCKET"
        const val DEVICE_UUID = "kr.co.wero.www.sksk.uuid"
        private const val DEVICE_LIST = "kr.co.wero.www.sksk.devicelist"
        private const val DEVICE_LIST_SELECTED = "kr.co.wero.www.sksk.devicelistselected"
        const val BUFFER_SIZE = "kr.co.wero.www.sksk.buffersize"
        private const val TAG = "BlueTest5-BluetoothSearch"
    }
}