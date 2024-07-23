package com.example.bthome.ui.bluetooth

import android.bluetooth.BluetoothAdapter
import com.example.bthome.ui.home.HomeFragment

class BluetoothController(private val adapter: BluetoothAdapter) {

    private var connectThread: ConnectThread? = null

    fun connect(mac:String, listener: HomeFragment){
        if (adapter.isEnabled && mac.isNotEmpty()){
            val device = adapter.getRemoteDevice(mac)
            connectThread = ConnectThread(device, listener)
            connectThread?.start()
        }
    }
    fun sendMessage(message: String){
        connectThread?.sendMessage(message)
    }
    fun closeConnection(){
        connectThread?.closeConnection()
    }

    companion object{
        const val BLUETOOTH_CONNECTED = "bluetooth_connected"
        const val BLUETOOTH_NO_CONNECTED = "bluetooth_no_connected"
    }
    interface Listener{
        fun onReceive(message: String)
    }
}