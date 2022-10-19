package com.example.customproject

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.customproject.database.BrandItem
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding : FragmentInfoBinding
    private lateinit var spinnerAdapter : ArrayAdapter<String>
    private var changedVariable = false

    private val viewModel : InfoViewModel by activityViewModels {
        InfoViewModelFactory(
            (activity?.application as DatabaseApplication).database.brandDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater, container, false)

        spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.infoBrandSpinner.adapter = spinnerAdapter

        getSpinnerItems()

        InfoFragmentArgs.fromBundle(requireArguments()).item?.let {
            viewModel.setVariables(it)
        }

        setViewValues()

        binding.infoNameEdit.addTextChangedListener(
            getTextWatcher()
        )

        binding.infoDateEdit.addTextChangedListener(
            getTextWatcher()
        )

        binding.infoLowPriceEdit.addTextChangedListener(
            getTextWatcher()
        )

        binding.infoSellerEdit.addTextChangedListener(
            getTextWatcher()
        )

        binding.infoBrandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateItem()
            }
        }

        return binding.root
    }

    private fun getSpinnerItems() {
        viewModel.getBrands().asLiveData().observe(viewLifecycleOwner, Observer {
            spinnerAdapter.addAll(brandItemListToStringList(it))
            binding.infoBrandSpinner.setSelection(getBrandIndex(viewModel.brand!!))
        })
    }

    private fun brandItemListToStringList(list : List<BrandItem>) : List<String> {
        val ret = mutableListOf<String>()

        for(item in list) {
            ret.add(item.brandName)
        }

        return ret
    }

    private fun getBrandIndex(brand : String) : Int {
        return spinnerAdapter.getPosition(brand)
    }

    private fun setViewValues() {
        binding.infoDateEdit.setText(viewModel.date)
        binding.infoNameEdit.setText(viewModel.name)
        binding.infoLowPriceEdit.setText(viewModel.price.toString())
        binding.infoSellerEdit.setText(viewModel.seller)
    }

    private fun updateItem() {
        val name = binding.infoNameEdit.text.toString()
        val price = binding.infoLowPriceEdit.text.toString().toFloat()
        val date = binding.infoDateEdit.text.toString()
        val seller = binding.infoSellerEdit.text.toString()
        val brand = binding.infoBrandSpinner.selectedItem.toString()
        val item = ConcreteItem(viewModel.id, name, brand, price, date, seller)
        findNavController().previousBackStackEntry?.savedStateHandle?.set("changedItem", item)
    }

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