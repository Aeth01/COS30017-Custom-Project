package com.example.customproject.ui.viewinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.customproject.DatabaseApplication
import com.example.customproject.defines.UpdateFailEnum
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentInfoBinding
import kotlinx.coroutines.launch
import java.lang.Exception

class InfoFragment : Fragment() {
    private lateinit var binding : FragmentInfoBinding
    private lateinit var spinnerAdapter : ArrayAdapter<String>

    private val viewModel : InfoViewModel by activityViewModels {
        InfoViewModelFactory(
            (activity?.application as DatabaseApplication).database.brandDao(),
            (activity?.application as DatabaseApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater, container, false)

        // attach spinner data adapter to spinner view
        spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.infoBrandSpinner.adapter = spinnerAdapter

        populateSpinnerItems()

        // get item data and populate view model and views
        InfoFragmentArgs.fromBundle(requireArguments()).item?.let {
            viewModel.setVariables(it)
            setViewValues()
        }

        // delete current item
        binding.fabDeleteConcreteItem.setOnClickListener{
            deleteItem()
        }

        // save changes to current item
        binding.fabSaveChanges.setOnClickListener {
            updateItem()
        }

        return binding.root
    }

    // populate spinner with brand names from database
    private fun populateSpinnerItems() {
        viewModel.getBrands().asLiveData().observe(viewLifecycleOwner) {
            spinnerAdapter.addAll(BrandItem.brandItemListToStringList(it))
            binding.infoBrandSpinner.setSelection(getBrandIndex(viewModel.brand!!))
        }
    }

    // get index of a brand item in spinner
    private fun getBrandIndex(brand : String) : Int {
        return spinnerAdapter.getPosition(brand)
    }

    // update view text with viewModel values
    private fun setViewValues() {
        binding.infoDateEdit.setText(viewModel.date)
        binding.infoNameEdit.setText(viewModel.name)
        binding.infoLowPriceEdit.setText(viewModel.price.toString())
        binding.infoSellerEdit.setText(viewModel.seller)
    }

    // update item on return to previous fragment
    private fun updateItem() {
        val name = binding.infoNameEdit.text.toString()
        val brand = binding.infoBrandSpinner.selectedItem?.toString()!!
        val price = binding.infoLowPriceEdit.text.toString()
        val date = binding.infoDateEdit.text.toString()
        val seller = binding.infoSellerEdit.text.toString()

        val errors = validateInputs(name, brand, date, price, seller)

        // update if no errors found in validation
        if (errors.isEmpty()) {
            // add item to back stack for update upon back
            val item = ConcreteItem(viewModel.id, name, brand, price.toFloat(), date, seller)
            val navController = findNavController()

            navController.previousBackStackEntry?.savedStateHandle?.set("changedItem", item)
            navController.popBackStack()    // go back to previous fragment

            // show update success toast
            updateSuccessToast()
        }
        else {
            // prioritise EMPTY -> DATE -> PRICE -> GENERAL ERROR
            val error = if (errors.contains(UpdateFailEnum.EMPTY)) {
                UpdateFailEnum.EMPTY
            }
            else if (errors.contains(UpdateFailEnum.DATE)) {
                UpdateFailEnum.DATE
            }
            else if (errors.contains(UpdateFailEnum.PRICE)) {
                UpdateFailEnum.PRICE
            }
            else {
                UpdateFailEnum.ERROR
            }

            // show update fail toast
            updateFailToast(error)
        }
    }

    // validate all inputs
    private fun validateInputs(name : String, brand : String,
                               date : String, price : String,
                               seller : String) : List<UpdateFailEnum> {
        val errors = mutableListOf<UpdateFailEnum>()

        if (!validateNotEmpty(name)) {
            errors.add(UpdateFailEnum.EMPTY)
        }

        if (!validateNotEmpty(brand)) {
            errors.add(UpdateFailEnum.EMPTY)
        }

        if (!validateNotEmpty(date)) {
            errors.add(UpdateFailEnum.EMPTY)
        }
        else if (!validateDate(date)) {
            errors.add(UpdateFailEnum.DATE)
        }

        if (!validateNotEmpty(price)) {
            errors.add(UpdateFailEnum.EMPTY)
        }
        else if (!validatePrice(price.toFloat())) {
            errors.add(UpdateFailEnum.PRICE)
        }

        if (!validateNotEmpty(seller)) {
            errors.add(UpdateFailEnum.EMPTY)
        }

        return errors
    }

    // validate date via exception handling for checking valid date
    private fun validateDate(date : String) : Boolean {
        val ret = try {
            ConcreteItem.validDate(date)
        }
        catch(e : Exception) {
            false
        }

        return ret
    }

    // ensure price is greater than 0
    private fun validatePrice(price : Float) : Boolean {
        return price > 0
    }

    // ensure a field is not empty
    private fun validateNotEmpty(text : String) : Boolean {
        return text.isNotEmpty()
    }

    // delete item
    private fun deleteItem() {
        // remove update mechanism
        findNavController().previousBackStackEntry?.savedStateHandle?.remove<ConcreteItem>("changedItem")

        lifecycleScope.launch{
            viewModel.deleteItem(viewModel.itemRef!!)
        }

        // navigate back to previous
        findNavController().popBackStack()
    }

    private fun updateSuccessToast() {
        showToast("Item updated successfully!")
    }

    private fun updateFailToast(reason : UpdateFailEnum) {
        val toastMessage = when(reason) {
            UpdateFailEnum.DATE ->
                "Please enter a date in the format: DD/MM/YYYY"
            UpdateFailEnum.PRICE ->
                "Please enter a price greater than 0."
            UpdateFailEnum.EMPTY ->
                "Please ensure all inputs are not empty."
            else ->
                "Update failed. Please try again."
        }

        showToast(toastMessage)
    }

    private fun showToast(message : String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}