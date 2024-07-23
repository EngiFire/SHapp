package com.example.bthome.ui.bluetooth

import android.bluetooth.BluetoothDevice

data class BluetoothListItem(
    val device: BluetoothDevice,
    val isChecked: Boolean
)