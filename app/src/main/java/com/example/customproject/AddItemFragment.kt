package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentAddItemBinding
import java.lang.NumberFormatException
import java.text.ParseException

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
    ): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        // attach spinner data adapter to spinner view and populate
        spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.addItemBrand.adapter = spinnerAdapter

        populateSpinnerItems()

        // add item on click
        binding.fabAddConcreteItem.setOnClickListener{
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
            Toast.makeText(context, "Added $nameVal!", Toast.LENGTH_SHORT).show()

            returnToViewItems()
        }
    }

    // populate spinner with brand names from database
    private fun populateSpinnerItems() {
        viewModel.getBrands().asLiveData().observe(viewLifecycleOwner) {
            spinnerAdapter.addAll(BrandItem.brandItemListToStringList(it))
        }
    }

    // return to view items fragment
    private fun returnToViewItems() {
        findNavController().popBackStack()
    }

    // ensure inputs are valid
    private fun checkInputsValid() : Boolean {
//        val nameVal = binding.addItemName.text.toString()
//        val brandVal = binding.addItemBrand.selectedItem.toString()
        val priceVal = binding.addItemPrice.text.toString()
        val dateVal = binding.addItemDate.text.toString()
//        val sellerVal = binding.addItemSeller.text.toString()

        val inputsValid = try {
            ConcreteItem.validDate(dateVal) && ConcreteItem.validPrice(priceVal)
        } catch(parseException : ParseException) {
            Toast.makeText(context, "Please ensure date is valid and in the format: dd/mm/yyyy.",Toast.LENGTH_SHORT).show()
            false
        } catch(numberFormatException : NumberFormatException) {
            Toast.makeText(context, "Please ensure that price is a number above $0.",Toast.LENGTH_SHORT).show()
            false
        }

        return inputsValid
    }
}