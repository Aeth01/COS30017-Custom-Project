package com.example.customproject

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewItemsAdapter(private val data : MutableList<ItemData>,
                       private val listener : (ItemData) -> Unit) : RecyclerView.Adapter<ViewItemsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // get row template for view holder
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_row, parent, false) as View

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = data[position]
        holder.setBackgroundColour(position)
        holder.bind(item)
    }

    inner class Holder(private val v : View) : RecyclerView.ViewHolder(v) {
        val nameView = v.findViewById<TextView>(R.id.itemRowName)
        val priceView = v.findViewById<TextView>(R.id.itemRowPrice)
        val dateView = v.findViewById<TextView>(R.id.itemRowDate)

        fun bind(item : ItemData) {
            nameView.text = item.name
            priceView.text = item.price.toString()
            dateView.text = item.date.toString()

            v.setOnClickListener {
                listener(item)
            }
        }

        fun setBackgroundColour(position : Int) {
            if (position % 2 == 0) {
                v.setBackgroundColor(v.resources.getColor(R.color.rowColorDefault))
                v.setBackgroundColor(v.resources.getColor(R.color.rowColorAlternate))
            }
        }
    }
}