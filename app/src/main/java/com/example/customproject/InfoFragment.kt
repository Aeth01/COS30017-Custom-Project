package com.example.customproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentInfoBinding
import kotlinx.coroutines.launch

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

        setTextChangedListeners()

        // delete current item
        binding.fabDeleteConcreteItem.setOnClickListener{
            deleteItem()
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

    // set text changed listeners for views
    private fun setTextChangedListeners() {
        val sharedTextWatcher = getTextWatcher()

        binding.infoNameEdit.addTextChangedListener(
            sharedTextWatcher
        )

        binding.infoDateEdit.addTextChangedListener(
            sharedTextWatcher
        )

        binding.infoLowPriceEdit.addTextChangedListener(
            sharedTextWatcher
        )

        binding.infoSellerEdit.addTextChangedListener(
            sharedTextWatcher
        )

        binding.infoBrandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateItem()
            }
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
        // ensure price is sensible
        val price : Float = if (!binding.infoLowPriceEdit.text.isNullOrEmpty()) {
            binding.infoLowPriceEdit.text.toString().toFloat()
        }
        else {
            viewModel.price     // always a sensible value (0, or previous default)
        }

        // get data from edit text views
        val name = binding.infoNameEdit.text.toString()
        val date = binding.infoDateEdit.text.toString()
        val seller = binding.infoSellerEdit.text.toString()

        // get selected brand from spinner
        val brand = binding.infoBrandSpinner.selectedItem?.toString()

        if (brand != null) {
            // add item to back stack for update upon back
            val item = ConcreteItem(viewModel.id, name, brand, price, date, seller)
            findNavController().previousBackStackEntry?.savedStateHandle?.set("changedItem", item)
        }
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

    // get a text watcher that will update the ConcreteItem being edited
    private fun getTextWatcher() : TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateItem()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }
}