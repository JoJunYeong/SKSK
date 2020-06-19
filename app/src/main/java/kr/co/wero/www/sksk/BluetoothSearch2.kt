package kr.co.wero.www.sksk


import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import kotlinx.android.synthetic.main.activity_bluetooth_search.*
import org.jetbrains.anko.toast


class BluetoothSearch2 : AppCompatActivity() {

    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private lateinit var mNewDeviceArrayAdapter: ArrayAdapter<String>
    private val REQUEST_ENABLE_BLUETOOTH = 1
    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
        val EXTRA_NAME : String = "Device_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_search)

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            toast("this device doesn't support bluetooth")
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        search.setOnClickListener{ pairedDeviceList() }
    }
    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<String> = ArrayList()
        val namelist : ArrayList<String> = ArrayList()
        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device.name+"("+device.address+")")
                namelist.add(device.name+"("+device.address+")")
                Log.i("device", ""+device.name)
            }
        } else {
            toast("no paired bluetooth devices found")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listview.adapter = adapter
        listview.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: String = list[position]
          //  val name : String =  device.name
          //  val address: String = device.name

            val intent = Intent(this, mainpage::class.java)
 //           intent.putExtra(EXTRA_ADDRESS, address)
            intent.putExtra(EXTRA_NAME, device)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth has been enabled")
                } else {
                    toast("Bluetooth has been disabled")
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                toast("Bluetooth enabling has been canceled")
            }
        }
    }
}