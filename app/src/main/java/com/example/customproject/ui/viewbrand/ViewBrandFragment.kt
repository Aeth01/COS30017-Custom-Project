@file:Suppress("NAME_SHADOWING")
package com.example.customproject.ui.viewbrand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.DatabaseApplication
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentViewBrandBinding
import com.example.customproject.ui.deleteConfirmDialog.DeleteDialogFragment
import kotlinx.coroutines.launch

class ViewBrandFragment : Fragment() {
    private lateinit var binding: FragmentViewBrandBinding
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter : ViewBrandAdapter
    private lateinit var data : MutableList<BrandItem>

    private val viewModel : ViewBrandViewModel by activityViewModels {
        ViewBrandViewModelFactory(
            (activity?.application as DatabaseApplication).database.brandDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewBrandBinding.inflate(inflater, container, false)

        // populate recycler view with brand items
        val itemListView = binding.viewBrandList
        linearLayoutManager = LinearLayoutManager(context)
        itemListView.layoutManager = linearLayoutManager

        data = initData()

        adapter = ViewBrandAdapter(
            brandOpenListener = {
                openBrand(it)
            },
            brandDeleteListener = {
                deleteBrand(it)
            }
        )
        itemListView.adapter = adapter

        viewModel.getRows().asLiveData().observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        // go to add brand on click
        binding.fabAddBrand.setOnClickListener{
            val action =
                ViewBrandFragmentDirections.actionNavSelectBrandToAddBrandFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        return binding.root
    }

    // return data to show
    private fun initData() : MutableList<BrandItem> {
        val ret = mutableListOf<BrandItem>()

        val rows : LiveData<List<BrandItem>> = viewModel.getRows().asLiveData()
        rows.observe(viewLifecycleOwner) { rows ->
            rows?.let {
                for (row in it) {
                    data.add(BrandItem(row.brandName))
                }
            }
        }

        return ret
    }

    // go to view items page for brand
    private fun openBrand(item : BrandItem) {
        val action =
            ViewBrandFragmentDirections.actionNavSelectBrandToNavSelectItem2()
        action.brandItem = item
        NavHostFragment.findNavController(this).navigate(action)
    }

    // open delete confirmation dialog
    private fun deleteBrand(item : BrandItem) {
        val action = ViewBrandFragmentDirections.actionNavSelectBrandToDeleteDialogFragment()
        action.brand = item
        findNavController().navigate(action)
    }
}