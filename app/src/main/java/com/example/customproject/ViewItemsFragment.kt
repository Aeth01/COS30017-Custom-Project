package com.example.customproject

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentViewItemsBinding
import kotlinx.coroutines.flow.count
import java.util.*

class ViewItemsFragment : Fragment() {
    private lateinit var binding : FragmentViewItemsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter : ViewItemsAdapter
    private lateinit var data : MutableList<ItemData>

    private val viewModel : ViewItemsViewModel by activityViewModels {
        ViewItemsViewModelFactory(
            (activity?.application as DatabaseApplication).database.itemDao()
        )
    }

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

        binding.fabAddItem.setOnClickListener{
            val action = ViewItemsFragmentDirections.actionNavSelectToAddItemFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    private fun initData() : MutableList<ItemData> {
        val ret = mutableListOf<ItemData>()

        val rows : LiveData<List<ConcreteItem>> = viewModel.getRows().asLiveData()
        rows.observe(viewLifecycleOwner, Observer{ rows ->
            rows?.let {
                for(row in it) {
                    data.add(
                        ItemData(
                        row.name,
                        row.brand.toString(),
                        row.price,
                        row.date,
                        row.seller
                    )
                    )
                }
            }
        })

        return ret
    }

    private fun openItem(item : ItemData) {
        val action = ViewItemsFragmentDirections.actionNavSelectToInfoFragment()
        action.item = item
        NavHostFragment.findNavController(this).navigate(action)
    }
}