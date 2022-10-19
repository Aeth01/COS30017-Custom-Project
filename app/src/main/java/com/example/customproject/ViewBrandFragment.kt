package com.example.customproject

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentViewBrandBinding

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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBrandBinding.inflate(inflater, container, false)

        val itemListView = binding.viewBrandList
        linearLayoutManager = LinearLayoutManager(context)
        itemListView.layoutManager = linearLayoutManager

        data = initData()

        adapter = ViewBrandAdapter() {
            openBrand(it)
        }
        itemListView.adapter = adapter

        binding.fabAddBrand.setOnClickListener{
            val action = ViewBrandFragmentDirections.actionNavSelectBrandToAddBrandFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        viewModel.getRows().asLiveData().observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        return binding.root
    }

    private fun initData() : MutableList<BrandItem> {
        val ret = mutableListOf<BrandItem>()

        val rows : LiveData<List<BrandItem>> = viewModel.getRows().asLiveData()
        rows.observe(viewLifecycleOwner, Observer{ rows ->
            rows?.let {
                for(row in it) {
                    data.add(BrandItem(row.brandName))
                }
            }
        })

        return ret
    }

    private fun openBrand(item : BrandItem) {
        val action = ViewBrandFragmentDirections.actionNavSelectBrandToNavSelectItem2()
        action.brandItem = item
        NavHostFragment.findNavController(this).navigate(action)
    }
}