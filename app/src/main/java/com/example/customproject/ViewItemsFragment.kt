package com.example.customproject

import android.content.ClipData
import android.os.Bundle
import android.util.Log
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

        adapter = ViewItemsAdapter() {
            openItem(it)
        }
        itemListView.adapter = adapter

        var brand : String? = null
        ViewItemsFragmentArgs.fromBundle(requireArguments()).brandItem?.let {
            brand = it.brandName
        }

        val databaseData = if (brand.isNullOrEmpty()) {
            viewModel.getRows().asLiveData()
        }
        else {
            viewModel.getByBrand(brand!!).asLiveData()
        }

        databaseData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        binding.fabAddItem.setOnClickListener{
            val action = ViewItemsFragmentDirections.actionNavSelectToAddItemFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ConcreteItem>("changedItem")?.observe(viewLifecycleOwner) {
            viewModel.updateById(it.itemId, it.name, it.brand, it.price, it.date, it.seller)
            Log.e("openItem", "Updated item to $it")
        }

        return binding.root
    }

    private fun openItem(item : ConcreteItem) {
        viewModel.clickedIndex = item.itemId
        Log.e("openItem", "clickedIndex = ${item.itemId}")

        val action = ViewItemsFragmentDirections.actionNavSelectToInfoFragment()
        action.item = item
        NavHostFragment.findNavController(this).navigate(action)
    }
}