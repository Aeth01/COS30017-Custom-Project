package com.example.customproject

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentAddItemBinding
import com.example.customproject.databinding.FragmentViewItemsBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class AddItemFragment : Fragment() {
    private lateinit var binding : FragmentAddItemBinding
    private lateinit var spinnerAdapter : ArrayAdapter<String>

    private val viewModel : AddItemViewModel by activityViewModels {
        val database = (activity?.application as DatabaseApplication).database
        AddItemViewModelFactory(
            database.itemDao(),
            database.brandDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        // attach spinner data adapter to spinner view
        spinnerAdapter = ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addItemBrand.adapter = spinnerAdapter

        populateSpinnerItems()

        binding.addItemSubmit.setOnClickListener{
            addItem()
        }

        return binding.root
    }

    // add item to database and return to database
    private fun addItem() {
        val nameVal = binding.addItemName.text.toString()
        val brandVal = binding.addItemBrand.selectedItem.toString()
        val priceVal = binding.addItemPrice.text.toString().toFloat()
        val dateVal = binding.addItemDate.text.toString()
        val sellerVal = binding.addItemSeller.text.toString()

        if (checkInputsValid()) {
            viewModel.addNewItem(nameVal, brandVal, priceVal, dateVal, sellerVal)
        }

        Toast.makeText(context, "Added $nameVal!", Toast.LENGTH_SHORT).show()

        returnToViewItems()
    }

    // populate spinner with brand names from database
    private fun populateSpinnerItems() {
        viewModel.getBrands().asLiveData().observe(viewLifecycleOwner, Observer {
            spinnerAdapter.addAll(BrandItem.brandItemListToStringList(it))
        })
    }

    private fun returnToViewItems() {
        val action = AddItemFragmentDirections.actionAddItemFragmentToNavSelect()
        action.brandItem = BrandItem(binding.addItemBrand.selectedItem.toString())
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun checkInputsValid() : Boolean {
        val nameVal = binding.addItemName.text.toString()
        val brandVal = binding.addItemBrand.selectedItem.toString()
        val priceVal = binding.addItemPrice.text.toString()
        val dateVal = binding.addItemDate.text.toString()
        val sellerVal = binding.addItemSeller.text.toString()

        return true
    }
}