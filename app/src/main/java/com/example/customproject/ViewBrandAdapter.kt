package com.example.customproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.database.BrandItem

class ViewBrandAdapter(
        private val brandOpenListener : (BrandItem) -> Unit,
        private val brandDeleteListener : (BrandItem) -> Unit
) : RecyclerView.Adapter<ViewBrandAdapter.Holder>() {
    val data = mutableListOf<BrandItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // get row template for view holder
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.brand_row, parent, false) as View

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

    // update date
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData : List<BrandItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class Holder(private val v : View) : RecyclerView.ViewHolder(v) {
        private val brandName = v.findViewById<TextView>(R.id.brandRowName)
        private val deleteButton = v.findViewById<TextView>(R.id.brandRowDelete)

        fun bind(item : BrandItem) {
            brandName.text = item.brandName

            brandName.setOnClickListener{
                brandOpenListener(item)
            }

            deleteButton.setOnClickListener {
                brandDeleteListener(item)
            }
        }

        // use alternating row background colours
        fun setBackgroundColour(position : Int) {
            if (position % 2 == 0) {
                v.setBackgroundColor(ResourcesCompat.getColor(v.resources, R.color.primaryRowColor, v.context.theme))
            }
            else {
                v.setBackgroundColor(ResourcesCompat.getColor(v.resources, R.color.secondaryRowColor, v.context.theme))
            }
        }
    }
}