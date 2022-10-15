package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentAddItemBinding
import com.example.customproject.databinding.FragmentViewItemsBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class AddItemFragment : Fragment() {
    private lateinit var binding : FragmentAddItemBinding

    private val viewModel : AddItemViewModel by activityViewModels {
        AddItemViewModelFactory(
            (activity?.application as DatabaseApplication).database
                .itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        binding.addItemSubmit.setOnClickListener{
            addItem()
        }

        binding.addItemBrandSubmit.setOnClickListener{
            this.lifecycleScope.launch{
                (activity?.application as DatabaseApplication).database.brandDao().insertAll(BrandItem(name="Nvidia"))
            }
        }

        return binding.root
    }

    private fun addItem() {
        val nameVal = binding.addItemName.text.toString()
        val brandVal = binding.addItemBrand.text.toString().toInt()
        val priceVal = binding.addItemPrice.text.toString().toFloat()
        val dateVal = binding.addItemDate.text.toString()
        val sellerVal = binding.addItemSeller.text.toString()

        if (checkInputsValid()) {
            viewModel.addNewItem(nameVal, brandVal, priceVal, dateVal, sellerVal)
        }
    }

    private fun checkInputsValid() : Boolean {
        val nameVal = binding.addItemName.text.toString()
        val brandVal = binding.addItemBrand.text.toString()
        val priceVal = binding.addItemPrice.text.toString()
        val dateVal = binding.addItemDate.text.toString()
        val sellerVal = binding.addItemSeller.text.toString()

        return true
    }
}