package com.example.customproject

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.database.ConcreteItem

class ViewItemsAdapter(private val listener : (ConcreteItem) -> Unit) : RecyclerView.Adapter<ViewItemsAdapter.Holder>() {
    val data = mutableListOf<ConcreteItem>()

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

    fun updateData(newData : List<ConcreteItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class Holder(private val v : View) : RecyclerView.ViewHolder(v) {
        private val nameView = v.findViewById<TextView>(R.id.itemRowName)
        private val priceView = v.findViewById<TextView>(R.id.itemRowPrice)
        private val dateView = v.findViewById<TextView>(R.id.itemRowDate)

        fun bind(item : ConcreteItem) {
            nameView.text = item.name
            priceView.text = item.price.toString()
            dateView.text = item.date

            v.setOnClickListener {
                listener(item)
            }
        }

        fun setBackgroundColour(position : Int) {
            if (position % 2 == 0) {
                v.setBackgroundColor(ResourcesCompat.getColor(v.resources, R.color.rowColorDefault, v.context.theme))
            }
            else {
                v.setBackgroundColor(ResourcesCompat.getColor(v.resources, R.color.rowColorAlternate, v.context.theme))
            }
        }
    }
}