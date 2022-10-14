package com.example.customproject

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.databinding.FragmentViewItemsBinding
import java.util.*

class ViewItemsFragment : Fragment() {
    private lateinit var binding : FragmentViewItemsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter : ViewItemsAdapter
    private lateinit var data : MutableList<ItemData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewItemsBinding.inflate(inflater, container, false)

        val itemListView = binding.viewItemList
        linearLayoutManager = LinearLayoutManager(context)
        itemListView.layoutManager = linearLayoutManager

        data = initData()

        adapter = ViewItemsAdapter(data) {
            openItem(it)
        }
        itemListView.adapter = adapter

        return binding.root
    }

    fun initData() : MutableList<ItemData> {
        val ret = mutableListOf<ItemData>()

        var skippedFirstLine = false
        resources.openRawResource(R.raw.temppricing).bufferedReader().forEachLine {
            if (!skippedFirstLine) {
                skippedFirstLine = true
            }
            else {
                val dataList = it.split(",")
                ret.add(ItemData(
                    dataList[0],
                    dataList[1],
                    dataList[2].toFloat(),
                    dataList[3],
                    dataList[4]
                ))
            }
        }

        return ret
    }

    fun openItem(item : ItemData) {
//        val action = ViewItemsFragmentDirections.actionViewItemsFragmentToInfoFragment()
//        action.item = item
//        NavHostFragment.findNavController(this).navigate(action)
    }
}