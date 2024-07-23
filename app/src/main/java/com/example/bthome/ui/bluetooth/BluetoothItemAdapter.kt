package com.example.bthome.ui.bluetooth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bthome.R
import com.example.bthome.databinding.ListBluetoothItemBinding

class BluetoothItemAdapter(private val listener: Listener, val adapterType: Boolean) : ListAdapter<BluetoothListItem, BluetoothItemAdapter.MyHolder>(Comparator()) {
    private var oldCheckBox: CheckBox? = null

    class MyHolder(
        view: View, private val adapter: BluetoothItemAdapter,
        private val listener: Listener,
        val adapterType: Boolean):
        RecyclerView.ViewHolder(view){
        private val b = ListBluetoothItemBinding.bind(view)
        private var item1: BluetoothListItem? = null

        init {
            b.checkBox.setOnClickListener{
                item1?.let { it1 -> listener.onClick(it1) }
                adapter.selectCheckBox(it as CheckBox)
            }
            itemView.setOnClickListener{
                if (adapterType){
                    try {
                        item1?.device?.createBond()
                    } catch (e: SecurityException){

                    }
                } else {
                    item1?.let { it1 -> listener.onClick(it1) }
                    adapter.selectCheckBox(it as CheckBox)
                }
            }
        }

        fun bind(item: BluetoothListItem) = with(b) {
            checkBox.visibility = if (adapterType) View.GONE else View.VISIBLE
            item1 = item
            try {
                name.text = item.device.name
                mac.text = item.device.address
            } catch (e: SecurityException){

            }

            if (item.isChecked) adapter.selectCheckBox(checkBox)
        }
    }

    class Comparator : DiffUtil.ItemCallback<BluetoothListItem>(){
        override fun areItemsTheSame(oldItem: BluetoothListItem, newItem: BluetoothListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BluetoothListItem, newItem: BluetoothListItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_bluetooth_item, parent, false)
        return MyHolder(view, this, listener, adapterType)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun selectCheckBox(checkBox: CheckBox){
        oldCheckBox?.isChecked = false
        oldCheckBox = checkBox
        oldCheckBox?.isChecked = true
    }

    interface Listener{
        fun onClick(device: BluetoothListItem)
    }
}