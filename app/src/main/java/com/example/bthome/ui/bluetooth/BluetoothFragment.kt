package com.example.bthome.ui.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bthome.databinding.FragmentBluetoothBinding
import com.google.android.material.snackbar.Snackbar

class BluetoothFragment : Fragment(), BluetoothItemAdapter.Listener {

    private  var preferences: SharedPreferences? = null
    private lateinit var itemAdapter: BluetoothItemAdapter
    private lateinit var discoveryAdapter: BluetoothItemAdapter
    private var bAdapter: BluetoothAdapter? = null

    private var _binding: FragmentBluetoothBinding? = null

    private lateinit var btLauncher: ActivityResultLauncher<Intent>
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val bluetoothViewModel =
//            ViewModelProvider(this).get(BluetoothViewModel::class.java)
//
//        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
//        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        bluetoothViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = activity?.getSharedPreferences(BluetoothConstans.PREFERENCES, Context.MODE_PRIVATE)
        binding.imBluetoothOn.setOnClickListener {
            btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
        binding.imBluetoothSearch.setOnClickListener {
            try {
                if (bAdapter?.isEnabled == true){
                    bAdapter?.startDiscovery()
                    it.visibility = View.GONE
                    binding.pbSearch.visibility = View.VISIBLE
                }
            } catch (e: SecurityException){

            }
        }
        intentFilters()
        checkPermissions()
        initRcView()
        registerBtLauncher()
        initBtAdapter()
        bluetoothState()
    }

    private fun initRcView() = with(binding){
        rcViewPaired.layoutManager = LinearLayoutManager(requireContext())
        rcViewSearch.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = BluetoothItemAdapter(this@BluetoothFragment, false)
        discoveryAdapter = BluetoothItemAdapter(this@BluetoothFragment, true)
        rcViewPaired.adapter = itemAdapter
        rcViewSearch.adapter = discoveryAdapter
    }

    private fun getPairedDevices(){
        try {
            val list = ArrayList<BluetoothListItem>()
            val deviceList = bAdapter?.bondedDevices as Set<BluetoothDevice>
            deviceList.forEach {
                list.add(
                    BluetoothListItem(
                        it,
                        preferences?.getString(BluetoothConstans.MAC, "") == it.address
                    )
                )
            }
            binding.tvEmptyPaired.visibility = if(list.isEmpty()) View.VISIBLE else View.GONE
            itemAdapter.submitList(list)
        } catch (e: SecurityException){

        }
    }

    private fun initBtAdapter(){
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bAdapter = bManager.adapter
    }

    private fun bluetoothState(){
        if (bAdapter?.isEnabled == true){
            changButtonColor(binding.imBluetoothOn, Color.GREEN)
            getPairedDevices()
        }
    }

    private fun registerBtLauncher(){
        btLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if (it.resultCode == Activity.RESULT_OK){
                changButtonColor(binding.imBluetoothOn, Color.GREEN)
                getPairedDevices()
                Snackbar.make(binding.root, "Блютуз включён!", Snackbar.LENGTH_LONG)
            } else {
                Snackbar.make(binding.root, "Блютуз выключен!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermissions(){
        if (!checkBtPermissions()){
            registerPermissionListener()
            launchBtPermissions()
        }
    }

    private fun launchBtPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            pLauncher.launch(arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        } else {
            pLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        }
    }

    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){

        }
    }

    private fun saveMac(mac: String){
        val editor = preferences?.edit()
        editor?.putString(BluetoothConstans.MAC, mac)
        editor?.apply()
    }

    override fun onClick(item: BluetoothListItem) {
        saveMac(item.device.address)
    }

    private val bReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    val list = mutableSetOf<BluetoothListItem>()
                    list.addAll(discoveryAdapter.currentList)
                    if (device != null) list.add(BluetoothListItem(device, false))
                    discoveryAdapter.submitList(list.toList())
                    binding.tvEmptySearch.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    try {
                        Log.d("MyLog", "Device: ${device?.name}")
                    } catch (e: SecurityException){

                    }
                }
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                    getPairedDevices()
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    binding.imBluetoothSearch.visibility = View.VISIBLE
                    binding.pbSearch.visibility = View.GONE
                }
            }
        }

    }

    private fun intentFilters(){
        val f1 = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val f2 = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        val f3 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        activity?.registerReceiver(bReceiver, f1)
        activity?.registerReceiver(bReceiver, f2)
        activity?.registerReceiver(bReceiver, f3)
    }
}