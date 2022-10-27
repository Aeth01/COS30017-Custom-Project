package com.example.customproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentViewItemsBinding
import kotlinx.coroutines.launch

class ViewItemsFragment : Fragment() {
    private lateinit var binding : FragmentViewItemsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter : ViewItemsAdapter

    private val viewModel : ViewItemsViewModel by activityViewModels {
        ViewItemsViewModelFactory(
            (activity?.application as DatabaseApplication).database.itemDao(),
            (activity?.application as DatabaseApplication).database.brandDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewItemsBinding.inflate(inflater, container, false)

        // populate recycler view
        val itemListView = binding.viewItemList
        linearLayoutManager = LinearLayoutManager(context)
        itemListView.layoutManager = linearLayoutManager

        adapter = ViewItemsAdapter {
            openItem(it)
        }
        itemListView.adapter = adapter

        // populate rows
        ViewItemsFragmentArgs.fromBundle(requireArguments()).brandItem?.let { brandItem ->
            val brand = brandItem.brandName
            val databaseData = viewModel.getByBrand(brand!!).asLiveData()
            databaseData.observe(viewLifecycleOwner) {
                adapter.updateData(it)
            }
        }

        // go to add item fragment
        binding.fabAddItem.setOnClickListener{
            val action = ViewItemsFragmentDirections.actionNavSelectToAddItemFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        // update item if changed item exists
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<ConcreteItem>("changedItem")?.observe(viewLifecycleOwner) {
            viewModel.updateById(it.itemId, it.name, it.brand, it.price, it.date, it.seller)
            Log.e("openItem", "Updated item to $it")
        }

        return binding.root
    }

    // open info fragment for corresponding item
    private fun openItem(item : ConcreteItem) {
        viewModel.clickedIndex = item.itemId
        Log.e("openItem", "clickedIndex = ${item.itemId}")

        val action = ViewItemsFragmentDirections.actionNavSelectToInfoFragment()
        action.item = item
        NavHostFragment.findNavController(this).navigate(action)
    }
}