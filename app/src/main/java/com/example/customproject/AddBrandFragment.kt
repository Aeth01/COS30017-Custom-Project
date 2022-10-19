package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentAddBrandBinding
import com.example.customproject.databinding.FragmentAddItemBinding
import kotlinx.coroutines.*

class AddBrandFragment : Fragment() {
    private lateinit var binding : FragmentAddBrandBinding
    private lateinit var viewModel : AddBrandViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBrandBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AddBrandViewModel::class.java]

        binding.addBrandSubmit.setOnClickListener{
            addBrand()
        }

        return binding.root
    }

    private fun addBrand() {
        var toastMsg = ""
        val name = binding.addBrandName.text
                    .trim()
                    .toString()
        if (name != "") {
            this.lifecycleScope.launch {
                (activity?.application as DatabaseApplication).database.brandDao()
                    .insertAll(BrandItem(brandName = "$name"))
            }
            toastMsg = "Added $name to Brands.";
        }
        else {
            toastMsg = "Please ensure name is not blank."
        }

        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.brandName = binding.addBrandName.text.toString()
    }

    override fun onResume() {
        super.onResume()
        binding.addBrandName.setText(viewModel.brandName)
    }
}