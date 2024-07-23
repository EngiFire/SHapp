package com.example.bthome.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.bthome.R
import com.example.bthome.databinding.FragmentHomeBinding
import com.example.bthome.ui.bluetooth.BluetoothConstans
import com.example.bthome.ui.bluetooth.BluetoothController
import android.graphics.Color
import android.util.Log
import com.example.bthome.R.color
import com.example.bthome.ui.bluetooth.changButtonColor


class HomeFragment : Fragment(), BluetoothController.Listener {

    private var rl: Boolean = false
    private var kl: Boolean = false
    private var bl: Boolean = false
    private var hl: Boolean = false

    private var rb: Boolean = false
    private var kb: Boolean = false
    private var bb: Boolean = false
    private var hb: Boolean = false

    private var _binding: FragmentHomeBinding? = null
    private lateinit var bluetoothController: BluetoothController
    private lateinit var btAdapter: BluetoothAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtAdapter()

        val pref = activity?.getSharedPreferences(
            BluetoothConstans.PREFERENCES, Context.MODE_PRIVATE)
        val mac = pref?.getString(BluetoothConstans.MAC, "")
        bluetoothController = BluetoothController(btAdapter)
        binding.bConnectHome.setOnClickListener {
            bluetoothController.connect(mac ?: "", this)
            showDialog()
        }

        Log.d("MyLog", "111")

        //////КОМНАТЫ

        binding.bRoom.setOnClickListener {
            Log.d("MyLog", "444")

            binding.cvRoom.visibility = View.VISIBLE
            binding.cvKitchen.visibility = View.GONE
            binding.cvBath.visibility = View.GONE
            binding.cvHallway.visibility = View.GONE

            binding.bRoom.setBackgroundColor(color.white_green)
            binding.bKitchen.setBackgroundColor(Color.TRANSPARENT)
            binding.bBath.setBackgroundColor(Color.TRANSPARENT)
            binding.bHallway.setBackgroundColor(Color.TRANSPARENT)

            bluetoothController.sendMessage("rl-?^")
            bluetoothController.sendMessage("rb-?^")
        }
        binding.bKitchen.setOnClickListener {
            binding.cvKitchen.visibility = View.VISIBLE
            binding.cvRoom.visibility = View.GONE
            binding.cvBath.visibility = View.GONE
            binding.cvHallway.visibility = View.GONE

            binding.bKitchen.setBackgroundColor(color.white_green)
            binding.bRoom.setBackgroundColor(Color.TRANSPARENT)
            binding.bBath.setBackgroundColor(Color.TRANSPARENT)
            binding.bHallway.setBackgroundColor(Color.TRANSPARENT)

            bluetoothController.sendMessage("kl-?^")
            bluetoothController.sendMessage("kb-?^")
        }
        binding.bBath.setOnClickListener {
            binding.cvBath.visibility = View.VISIBLE
            binding.cvRoom.visibility = View.GONE
            binding.cvKitchen.visibility = View.GONE
            binding.cvHallway.visibility = View.GONE

            binding.bBath.setBackgroundColor(color.white_green)
            binding.bRoom.setBackgroundColor(Color.TRANSPARENT)
            binding.bKitchen.setBackgroundColor(Color.TRANSPARENT)
            binding.bHallway.setBackgroundColor(Color.TRANSPARENT)

            bluetoothController.sendMessage("bl-?^")
            bluetoothController.sendMessage("bb-?^")
        }
        binding.bHallway.setOnClickListener {
            binding.cvHallway.visibility = View.VISIBLE
            binding.cvRoom.visibility = View.GONE
            binding.cvKitchen.visibility = View.GONE
            binding.cvBath.visibility = View.GONE

            binding.bHallway.setBackgroundColor(color.white_green)
            binding.bRoom.setBackgroundColor(Color.TRANSPARENT)
            binding.bKitchen.setBackgroundColor(Color.TRANSPARENT)
            binding.bBath.setBackgroundColor(Color.TRANSPARENT)

            bluetoothController.sendMessage("hl-?^")
            bluetoothController.sendMessage("hb-?^")
        }

        Log.d("MyLog", "222")

        //////ЛАМПЫ

        binding.ibRoomLamp.setOnClickListener() {
            Log.d("MyLog", "666")
            if (!rl) {
                context?.toast("Включил")
                bluetoothController.sendMessage("rl-on^")
                rl = true
                changButtonColor(binding.ibRoomLamp, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("rl-off^")
                rl = false
                changButtonColor(binding.ibRoomLamp, Color.DKGRAY)
            }
        }
        binding.ibKitchenLamp.setOnClickListener {
            if (!kl) {
                context?.toast("Включил")
                bluetoothController.sendMessage("kl-on^")
                kl = true
                changButtonColor(binding.ibKitchenLamp, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("kl-off^")
                kl = false
                changButtonColor(binding.ibKitchenLamp, Color.DKGRAY)
            }
        }
        binding.ibBathLamp.setOnClickListener {
            if (!bl) {
                context?.toast("Включил")
                bluetoothController.sendMessage("bl-on^")
                bl = true
                changButtonColor(binding.ibBathLamp, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("bl-off^")
                bl = false
                changButtonColor(binding.ibBathLamp, Color.DKGRAY)
            }
        }
        binding.ibHallwayLamp.setOnClickListener {
            if (!hl) {
                context?.toast("Включил")
                bluetoothController.sendMessage("hl-on^")
                hl = true
                changButtonColor(binding.ibHallwayLamp, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("hl-off^")
                hl = false
                changButtonColor(binding.ibHallwayLamp, Color.DKGRAY)
            }
        }

        Log.d("MyLog", "333")

        //////ПЕРЕКЛЮЧАТЕЛИ

        binding.ibRoomDevice.setOnClickListener {
            bluetoothController.sendMessage("rs-?^")
            Log.d("MyLog", "888")
            showDialogSwitchRoom(false, false)
        }
        binding.ibKitchenDevice.setOnClickListener {
            bluetoothController.sendMessage("ks-?^")
        }
        binding.ibBathDevice.setOnClickListener {
            bluetoothController.sendMessage("bs-?^")
        }
        binding.ibHallwayDevice.setOnClickListener {
            bluetoothController.sendMessage("hs-?^")
        }

        //////ВЕНТИЛЯТОРЫ

        binding.ibRoomFun.setOnClickListener {
            bluetoothController.sendMessage("rf-?^")
            Log.d("MyLog", "777")
        }
        binding.ibKitchenFun.setOnClickListener {
            bluetoothController.sendMessage("kf-?^")
        }
        binding.ibBathFun.setOnClickListener {
            bluetoothController.sendMessage("bf-?^")
        }
        binding.ibHallwayFun.setOnClickListener {
            bluetoothController.sendMessage("hf-?^")
        }

        //////ЖАЛЮЗИ

        binding.ibRoomBlind.setOnClickListener {
            if (!rb) {
                context?.toast("Включил")
                bluetoothController.sendMessage("rb-on^")
                rb = true
                changButtonColor(binding.ibRoomBlind, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("rb-off^")
                rb = false
                changButtonColor(binding.ibRoomBlind, Color.DKGRAY)
            }
        }
        binding.ibKitchenBlind.setOnClickListener {
            if (!kb) {
                context?.toast("Включил")
                bluetoothController.sendMessage("kb-on^")
                kb = true
                changButtonColor(binding.ibKitchenBlind, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("kb-off^")
                kb = false
                changButtonColor(binding.ibKitchenBlind, Color.DKGRAY)
            }
        }
        binding.ibBathBlind.setOnClickListener {
            if (!bb) {
                context?.toast("Включил")
                bluetoothController.sendMessage("bb-on^")
                bb = true
                changButtonColor(binding.ibBathBlind, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("bb-off^")
                bb = false
                changButtonColor(binding.ibBathBlind, Color.DKGRAY)
            }
        }
        binding.ibHallwayBlind.setOnClickListener {
            if (!hb) {
                context?.toast("Включил")
                bluetoothController.sendMessage("hb-on^")
                hb = true
                changButtonColor(binding.ibHallwayBlind, Color.GREEN)
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("hb-off^")
                hb = false
                changButtonColor(binding.ibHallwayBlind, Color.DKGRAY)
            }
        }

        //////ТЕМПЕРАТУРА

//        val patternTemp = "([rkbh])t:(\\d+)".toRegex()
//        val mes = "sd rt:43 ff"
//
//        val matchResult = patternTemp.find(mes)
//        if (matchResult != null) {
//                val key = matchResult?.groups?.get(1)?.value
//                Log.d("MyLog", key.toString())
//                val tNumber = matchResult?.groups?.get(2)?.value
//                context?.toast(tNumber.toString())
//
//                when (key) {
//                    "r" -> _binding?.tvRoomTemp?.text = "$tNumber℃"
//                    "k" -> _binding?.tvKitchenTemp?.text = "$tNumber℃"
//                    "b" -> _binding?.tvBathTemp?.text = "$tNumber℃"
//                    "h" -> _binding?.tvHallwayTemp?.text = "$tNumber℃"
//                }
//
//        }
//        else {
//            Log.d("MyLog", "123")
//        }


        var room_temp = binding.tvRoomTemp.text.dropLast(1).toString()
        var kitchen_temp = binding.tvKitchenTemp.text.dropLast(1).toString()
        var bath_temp = binding.tvBathTemp.text.dropLast(1).toString()
        var hallway_temp = binding.tvHallwayTemp.text.dropLast(1).toString()

        if (room_temp.toInt() <= 17) { binding.ivRoomStatusTemp.setImageResource(R.drawable.ic_snowflake)
        } else if (room_temp.toInt() <= 25) { binding.ivRoomStatusTemp.setImageResource(R.drawable.ic_check)
        } else if (room_temp.toInt() > 25) { binding.ivRoomStatusTemp.setImageResource(R.drawable.ic_fire)
        }

        if (kitchen_temp.toInt() <= 17) { binding.ivKitchenStatusTemp.setImageResource(R.drawable.ic_snowflake)
        } else if (kitchen_temp.toInt() <= 25) { binding.ivKitchenStatusTemp.setImageResource(R.drawable.ic_check)
        } else if (kitchen_temp.toInt() > 25) { binding.ivKitchenStatusTemp.setImageResource(R.drawable.ic_fire)
        }

        if (bath_temp.toInt() <= 17) { binding.ivBathStatusTemp.setImageResource(R.drawable.ic_snowflake)
        } else if (bath_temp.toInt() <= 25) { binding.ivBathStatusTemp.setImageResource(R.drawable.ic_check)
        } else if (bath_temp.toInt() > 25) { binding.ivBathStatusTemp.setImageResource(R.drawable.ic_fire)
        }

        if (hallway_temp.toInt() <= 17) { binding.ivHallwayStatusTemp.setImageResource(R.drawable.ic_snowflake)
        } else if (hallway_temp.toInt() <= 25) { binding.ivHallwayStatusTemp.setImageResource(R.drawable.ic_check)
        } else if (hallway_temp.toInt() > 25) { binding.ivHallwayStatusTemp.setImageResource(R.drawable.ic_fire)
        }

        _binding?.tvParamTemp?.text = ((room_temp.toInt() + kitchen_temp.toInt() + bath_temp.toInt() + hallway_temp.toInt()) / 4).toString() + "℃"

        var average_temp = (room_temp.toInt() + kitchen_temp.toInt() + bath_temp.toInt() + hallway_temp.toInt()) / 4

        when {
            average_temp <= 17 -> binding.ivAverageStatusTemp.setImageResource(R.drawable.ic_snowflake)
            average_temp <= 25 -> binding.ivAverageStatusTemp.setImageResource(R.drawable.ic_check)
            average_temp > 25 -> binding.ivAverageStatusTemp.setImageResource(R.drawable.ic_fire)
        }

        //////ВЛАЖНОСТЬ//////

        var room_humid = binding.tvRoomHumidity.text.dropLast(1).toString()
        var kitchen_humid = binding.tvKitchenHumidity.text.dropLast(1).toString()
        var bath_humid = binding.tvBathHumidity.text.dropLast(1).toString()
        var hallway_humid = binding.tvHallwayHumidity.text.dropLast(1).toString()

        if (room_humid.toInt() <= 44) { binding.ivRoomStatusHumidity.setImageResource(R.drawable.ic_fire)
        } else if (room_humid.toInt() <= 65) { binding.ivRoomStatusHumidity.setImageResource(R.drawable.ic_check)
        } else if (room_humid.toInt() > 65) { binding.ivRoomStatusHumidity.setImageResource(R.drawable.ic_water)
        }

        if (kitchen_humid.toInt() <= 44) { binding.ivKitchenStatusHumidity.setImageResource(R.drawable.ic_fire)
        } else if (kitchen_humid.toInt() <= 65) { binding.ivKitchenStatusHumidity.setImageResource(R.drawable.ic_check)
        } else if (kitchen_humid.toInt() > 65) { binding.ivKitchenStatusHumidity.setImageResource(R.drawable.ic_water)
        }

        if (bath_humid.toInt() <= 44) { binding.ivBathStatusHumidity.setImageResource(R.drawable.ic_fire)
        } else if (bath_humid.toInt() <= 65) { binding.ivBathStatusHumidity.setImageResource(R.drawable.ic_check)
        } else if (bath_humid.toInt() > 65) { binding.ivBathStatusHumidity.setImageResource(R.drawable.ic_water)
        }

        if (hallway_humid.toInt() <= 44) { binding.ivHallwayStatusHumidity.setImageResource(R.drawable.ic_fire)
        } else if (hallway_humid.toInt() <= 65) { binding.ivHallwayStatusHumidity.setImageResource(R.drawable.ic_check)
        } else if (hallway_humid.toInt() > 65) { binding.ivHallwayStatusHumidity.setImageResource(R.drawable.ic_water)
        }

        _binding?.tvParamHumidity?.text = ((room_humid.toInt() + kitchen_humid.toInt() + bath_humid.toInt() + hallway_humid.toInt()) / 4).toString() + "%"

        var average_humid = (room_humid.toInt() + kitchen_humid.toInt() + bath_humid.toInt() + hallway_humid.toInt()) / 4

        when {
            average_humid <= 44 -> binding.ivAverageStatusHumidity.setImageResource(R.drawable.ic_fire)
            average_humid <= 65 -> binding.ivAverageStatusHumidity.setImageResource(R.drawable.ic_check)
            average_humid > 65 -> binding.ivAverageStatusHumidity.setImageResource(R.drawable.ic_water)
        }

        binding.lock.setOnClickListener {
            if (binding.lock.isChecked) {
                context?.toast("Включил")
                bluetoothController.sendMessage("close^")
            }
            else {
                (context?.toast("Выключил"))
                bluetoothController.sendMessage("open^")
            }
        }

    }

    //////АВТОРИЗАЦИЯ//////

    private fun showDialog() {
        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.connect_authentication)
//            val tLogin = dialog.findViewById(com.example.bthome.R.id.name_login) as TextView
//            tLogin.text = title
//            val tPass = dialog.findViewById(com.example.bthome.R.id.name_login) as TextView
//            tPass.text = title
            val yesBtn = dialog.findViewById(R.id.btEnter) as Button
            val login = dialog.findViewById(R.id.editLogin) as TextView
            val password = dialog.findViewById(R.id.editPassword) as TextView
            yesBtn.setOnClickListener {
                if (!password.text.toString().isEmpty() && !login.text.toString().isEmpty()) {
                    bluetoothController.sendMessage("l:"+login.text.toString()+"^")
                    context?.toast("l:"+login.text.toString()+" ")
                    bluetoothController.sendMessage("p:"+password.text.toString()+"^")
                    dialog.dismiss()
                } else {
                    context?.toast("Заполните все поля!")
                }
            }

            dialog.show()
        }

    }

    //////ВЕНТИЛЯТОР//////

    private fun showDialogFunRoom(stat : Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_fun)
            val ex = dialog.findViewById(R.id.btFunExit) as Button
            val sw_fun = dialog.findViewById(R.id.swFun) as Switch
            val tv_desired_temp = dialog.findViewById(R.id.tvDesiredTemp) as TextView
            val tv_fun_temp = dialog.findViewById(R.id.tvFunTemp) as TextView
            val sb_fun = dialog.findViewById(R.id.sbFun) as SeekBar

            if (stat) {
                sw_fun.isChecked = true
                tv_desired_temp.visibility = View.VISIBLE
                tv_fun_temp.visibility = View.VISIBLE
                sb_fun.visibility = View.VISIBLE
            }

            sw_fun.setOnClickListener {
                if (sw_fun.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("rf-on^")

                    tv_desired_temp.visibility = View.VISIBLE
                    tv_fun_temp.visibility = View.VISIBLE
                    sb_fun.visibility = View.VISIBLE

                    sb_fun.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        var temp: Int = 0
                        override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                            temp = p1+17
                            tv_desired_temp.text = temp.toString() + "℃"
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                            (context?.toast("t:" + temp.toString()))
                            bluetoothController.sendMessage("rt:" + temp.toString()+"^")
                        }
                    })
                }
                else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("rf-off^")

                    tv_desired_temp.visibility = View.GONE
                    tv_fun_temp.visibility = View.GONE
                    sb_fun.visibility = View.GONE
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogFunKitchen(stat : Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_fun)
            val ex = dialog.findViewById(R.id.btFunExit) as Button
            val sw_fun = dialog.findViewById(R.id.swFun) as Switch
            val tv_desired_temp = dialog.findViewById(R.id.tvDesiredTemp) as TextView
            val tv_fun_temp = dialog.findViewById(R.id.tvFunTemp) as TextView
            val sb_fun = dialog.findViewById(R.id.sbFun) as SeekBar

            if (stat) {
                sw_fun.isChecked = true
                tv_desired_temp.visibility = View.VISIBLE
                tv_fun_temp.visibility = View.VISIBLE
                sb_fun.visibility = View.VISIBLE
            }

            sw_fun.setOnClickListener {
                if (sw_fun.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("kf-on^")

                    tv_desired_temp.visibility = View.VISIBLE
                    tv_fun_temp.visibility = View.VISIBLE
                    sb_fun.visibility = View.VISIBLE

                    sb_fun.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        var temp: Int = 0
                        override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                            temp = p1+17
                            tv_desired_temp.text = temp.toString() + "℃"
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                            (context?.toast("t:" + temp.toString()))
                            bluetoothController.sendMessage("kt:" + temp.toString()+"^")
                        }
                    })
                }
                else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("kf-off^")

                    tv_desired_temp.visibility = View.GONE
                    tv_fun_temp.visibility = View.GONE
                    sb_fun.visibility = View.GONE
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogFunBath(stat : Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_fun)
            val ex = dialog.findViewById(R.id.btFunExit) as Button
            val sw_fun = dialog.findViewById(R.id.swFun) as Switch
            val tv_desired_temp = dialog.findViewById(R.id.tvDesiredTemp) as TextView
            val tv_fun_temp = dialog.findViewById(R.id.tvFunTemp) as TextView
            val sb_fun = dialog.findViewById(R.id.sbFun) as SeekBar

            if (stat) {
                sw_fun.isChecked = true
                tv_desired_temp.visibility = View.VISIBLE
                tv_fun_temp.visibility = View.VISIBLE
                sb_fun.visibility = View.VISIBLE
            }

            sw_fun.setOnClickListener {
                if (sw_fun.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("bf-on^")

                    tv_desired_temp.visibility = View.VISIBLE
                    tv_fun_temp.visibility = View.VISIBLE
                    sb_fun.visibility = View.VISIBLE

                    sb_fun.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        var temp: Int = 0
                        override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                            temp = p1+17
                            tv_desired_temp.text = temp.toString() + "℃"
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                            (context?.toast("t:" + temp.toString()))
                            bluetoothController.sendMessage("bt:" + temp.toString()+"^")
                        }
                    })
                }
                else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("bf-off^")

                    tv_desired_temp.visibility = View.GONE
                    tv_fun_temp.visibility = View.GONE
                    sb_fun.visibility = View.GONE
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogFunHallway(stat : Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_fun)
            val ex = dialog.findViewById(R.id.btFunExit) as Button
            val sw_fun = dialog.findViewById(R.id.swFun) as Switch
            val tv_desired_temp = dialog.findViewById(R.id.tvDesiredTemp) as TextView
            val tv_fun_temp = dialog.findViewById(R.id.tvFunTemp) as TextView
            val sb_fun = dialog.findViewById(R.id.sbFun) as SeekBar

            if (stat) {
                sw_fun.isChecked = true
                tv_desired_temp.visibility = View.VISIBLE
                tv_fun_temp.visibility = View.VISIBLE
                sb_fun.visibility = View.VISIBLE
            }

            sw_fun.setOnClickListener {
                if (sw_fun.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("hf-on^")

                    tv_desired_temp.visibility = View.VISIBLE
                    tv_fun_temp.visibility = View.VISIBLE
                    sb_fun.visibility = View.VISIBLE

                    sb_fun.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        var temp: Int = 0
                        override fun onProgressChanged(p0: SeekBar, p1: Int, p2: Boolean) {
                            temp = p1+17
                            tv_desired_temp.text = temp.toString() + "℃"
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                        }

                        override fun onStopTrackingTouch(p0: SeekBar?) {
                            if (p0 != null) {
                                temp = p0!!.progress+17
                                tv_desired_temp.text = temp.toString() + "℃"
                            }
                            (context?.toast("t:" + temp.toString()))
                            bluetoothController.sendMessage("ht:" + temp.toString()+"^")
                        }
                    })
                }
                else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("hf-off^")

                    tv_desired_temp.visibility = View.GONE
                    tv_fun_temp.visibility = View.GONE
                    sb_fun.visibility = View.GONE
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    //////ПЕРЕКЛЮЧАТЕЛИ//////

    private fun showDialogSwitchRoom(stat1: Boolean, stat2: Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_switch)
            val ex = dialog.findViewById(R.id.btSwitchExit) as Button
            val sw_device1 = dialog.findViewById(R.id.swDevice1) as Switch
            val sw_device2 = dialog.findViewById(R.id.swDevice2) as Switch

            if (stat1) {
                sw_device1.isChecked = true
            }
            if (stat2) {
                sw_device2.isChecked = true
            }

            sw_device1.setOnClickListener {
                if (sw_device1.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("rs1-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("rs1-off^")
                }
            }

            sw_device1.setOnClickListener {
                if (sw_device2.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("rs2-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("rs2-off^")
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogSwitchKitchen(stat1: Boolean, stat2: Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_switch)
            val ex = dialog.findViewById(R.id.btSwitchExit) as Button
            val sw_device1 = dialog.findViewById(R.id.swDevice1) as Switch
            val sw_device2 = dialog.findViewById(R.id.swDevice2) as Switch

            if (stat1) {
                sw_device1.isChecked = true
            }
            if (stat2) {
                sw_device2.isChecked = true
            }

            sw_device1.setOnClickListener {
                if (sw_device1.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("ks1-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("ks1-off^")
                }
            }

            sw_device1.setOnClickListener {
                if (sw_device2.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("ks2-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("ks2-off^")
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogSwitchBath(stat1: Boolean, stat2: Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_switch)
            val ex = dialog.findViewById(R.id.btSwitchExit) as Button
            val sw_device1 = dialog.findViewById(R.id.swDevice1) as Switch
            val sw_device2 = dialog.findViewById(R.id.swDevice2) as Switch

            if (stat1) {
                sw_device1.isChecked = true
            }
            if (stat2) {
                sw_device2.isChecked = true
            }

            sw_device1.setOnClickListener {
                if (sw_device1.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("bs1-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("bs1-off^")
                }
            }

            sw_device1.setOnClickListener {
                if (sw_device2.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("bs2-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("bs2-off^")
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    private fun showDialogSwitchHallway(stat1: Boolean, stat2: Boolean) {

        val dialog = activity?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_switch)
            val ex = dialog.findViewById(R.id.btSwitchExit) as Button
            val sw_device1 = dialog.findViewById(R.id.swDevice1) as Switch
            val sw_device2 = dialog.findViewById(R.id.swDevice2) as Switch

            if (stat1) {
                sw_device1.isChecked = true
            }
            if (stat2) {
                sw_device2.isChecked = true
            }

            sw_device1.setOnClickListener {
                if (sw_device1.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("hs1-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("hs1-off^")
                }
            }

            sw_device1.setOnClickListener {
                if (sw_device2.isChecked) {
                    context?.toast("Включил")
                    bluetoothController.sendMessage("hs2-on^")
                } else {
                    (context?.toast("Выключил"))
                    bluetoothController.sendMessage("hs2-off^")
                }
            }

            ex.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }

    }

    //////УВЕДОМЛЕНИЯ//////

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun initBtAdapter(){
        val bManager = activity?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
    }

    override fun onReceive(message: String) {

        val patternTemp = "([rkbh])t:(\\d+)".toRegex()
        val patternHumid = "([rkbh])h:(\\d+)".toRegex()

        activity?.runOnUiThread {
            val matchTempResult = patternTemp.find(message)
            val matchHumidResult = patternHumid.find(message)

            if (matchTempResult != null) {
                val key = matchTempResult?.groups?.get(1)?.value
                Log.d("MyLog", key.toString())
                val tNumber = matchTempResult?.groups?.get(2)?.value
                context?.toast(tNumber.toString())

                when (key.toString()) {
                    "r" -> _binding?.tvRoomTemp?.text = "$tNumber℃"
                    "k" -> _binding?.tvKitchenTemp?.text = "$tNumber℃"
                    "b" -> _binding?.tvBathTemp?.text = "$tNumber℃"
                    "h" -> _binding?.tvHallwayTemp?.text = "$tNumber℃"
                }
            }


            if ("ks1-off/ks2-off" == message) {
                showDialogSwitchKitchen(false, false)
            }


            when{

                BluetoothController.BLUETOOTH_CONNECTED == message -> {
                    binding.bConnectHome.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), color.white_red)
                    binding.bConnectHome.text = "Отключиться"
                }

                BluetoothController.BLUETOOTH_NO_CONNECTED == message -> {
                    binding.bConnectHome.backgroundTintList = AppCompatResources
                        .getColorStateList(requireContext(), color.white_green)
                    binding.bConnectHome.text = "Подключиться"
                }

                "ERROR" == message -> {
                    bluetoothController.closeConnection()
                }

                //////ВЕНТИЛЯТОР//////

                "rf-on" == message -> {
                    showDialogFunRoom(true)
                }
                "rf-off" == message -> {
                    showDialogFunRoom(false)
                }

                "kf-on" == message -> {
                    showDialogFunKitchen(true)
                }
                "kf-off" == message -> {
                    showDialogFunKitchen(false)
                }

                "bf-on" == message -> {
                    showDialogFunBath(true)
                }
                "bf-off" == message -> {
                    showDialogFunBath(false)
                }

                "hf-on" == message -> {
                    showDialogFunHallway(true)
                }
                "hf-off" == message -> {
                    showDialogFunHallway(false)
                }

                //////ЛАМПЫ//////

                ("rl-on" == message) -> {
                    changButtonColor(binding.ibRoomLamp, Color.GREEN)
                    rl = true
                }
                ("rl-off" == message) -> {
                    changButtonColor(binding.ibRoomLamp, Color.DKGRAY)
                    rl = false
                }
                "kl-on" == message -> {
                    changButtonColor(binding.ibKitchenLamp, Color.GREEN)
                    kl = true
                }
                "kl-off" == message -> {
                    changButtonColor(binding.ibKitchenLamp, Color.DKGRAY)
                    kl = false
                }
                "bl-on" == message -> {
                    changButtonColor(binding.ibBathLamp, Color.GREEN)
                    bl = true
                }
                "bl-off" == message -> {
                    changButtonColor(binding.ibBathLamp, Color.DKGRAY)
                    bl = false
                }
                "hl-on" == message -> {
                    changButtonColor(binding.ibHallwayLamp, Color.GREEN)
                    hl = true
                }
                "hl-off" == message -> {
                    changButtonColor(binding.ibHallwayLamp, Color.DKGRAY)
                    hl = false
                }

                //////ПЕРЕКЛЮЧАТЕЛИ//////

                "rs1-off/rs2-off" == message -> {
                    showDialogSwitchRoom(false, false)
                }
                "rs1-on/rs2-off" == message -> {
                    showDialogSwitchRoom(true, false)
                }
                "rs1-off/rs2-on" == message -> {
                    showDialogSwitchRoom(false, true)
                }
                "rs1-on/rs2-on" == message -> {
                    showDialogSwitchRoom(true, true)
                }

                "ks1-off/ks2-off" == message -> {
                    showDialogSwitchKitchen(false, false)
                }
                "ks1-on/ks2-off" == message -> {
                    showDialogSwitchKitchen(true, false)
                }
                "ks1-off/ks2-on" == message -> {
                    showDialogSwitchKitchen(false, true)
                }
                "ks1-on/ks2-on" == message -> {
                    showDialogSwitchKitchen(true, true)
                }

                "bs1-off/bs2-off" == message -> {
                    showDialogSwitchBath(false, false)
                }
                "bs1-on/bs2-off" == message -> {
                    showDialogSwitchBath(true, false)
                }
                "bs1-off/bs2-on" == message -> {
                    showDialogSwitchBath(false, true)
                }
                "bs1-on/bs2-on" == message -> {
                    showDialogSwitchBath(true, true)
                }

                "hs1-off/hs2-off" == message -> {
                    showDialogSwitchHallway(false, false)
                }
                "hs1-on/hs2-off" == message -> {
                    showDialogSwitchHallway(true, false)
                }
                "hs1-off/hs2-on" == message -> {
                    showDialogSwitchHallway(false, true)
                }
                "hs1-on/hs2-on" == message -> {
                    showDialogSwitchHallway(true, true)
                }

                //////ЖАЛЮЗИ//////

                "rb-on" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.GREEN)
                    rb = true
                }
                "rb-off" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.DKGRAY)
                    rb = false
                }
                "kb-on" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.GREEN)
                    kb = true
                }
                "kb-off" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.DKGRAY)
                    kb = false
                }
                "bb-on" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.GREEN)
                    bb = true
                }
                "bb-off" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.DKGRAY)
                    bb = false
                }
                "hb-on" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.GREEN)
                    hb = true
                }
                "hb-off" == message -> {
                    changButtonColor(binding.ibRoomBlind, Color.DKGRAY)
                    hb = false
                }

                //////ТЕМПЕРАТУРА//////




                //////ВЛАЖНОСТЬ//////

                (matchHumidResult != null) -> {
                    val key = matchHumidResult?.groups?.get(1)?.value
                    Log.d("MyLog", key.toString())
                    val hNumber = matchHumidResult?.groups?.get(2)?.value
                    context?.toast(hNumber.toString())

                    when (key.toString()) {
                        "r" -> _binding?.tvRoomHumidity?.text = "$hNumber%"
                        "k" -> _binding?.tvKitchenHumidity?.text = "$hNumber%"
                        "b" -> _binding?.tvBathHumidity?.text = "$hNumber%"
                        "h" -> _binding?.tvHallwayHumidity?.text = "$hNumber%"
                    }

                }

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
