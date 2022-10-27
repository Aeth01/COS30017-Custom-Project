package com.example.customproject.ui.addbrand

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.customproject.DatabaseApplication
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentAddBrandBinding
import kotlinx.coroutines.*

class AddBrandFragment : Fragment() {
    private lateinit var binding : FragmentAddBrandBinding
    private lateinit var viewModel : AddBrandViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBrandBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AddBrandViewModel::class.java]

        // add brand to database on submit
        binding.fabAddBrandItem.setOnClickListener{
            addBrand()
        }

        return binding.root
    }

    // add brand to database
    private fun addBrand() {
        // get brand name from edit text
        val name = binding.addBrandName.text
                    .trim()
                    .toString()

        Log.e("AddBrand", "name=$name")

        // add brand if it is not empty
        if (name.isNotEmpty()) {
            this.lifecycleScope.launch {
                (activity?.application as DatabaseApplication).database.brandDao()
                    .insertAll(BrandItem(brandName = name))
            }
            Toast.makeText(context, "Added $name to brands.", Toast.LENGTH_SHORT).show()
            returnToViewBrands()
        }
        else {
            Toast.makeText(context, "Please ensure name is not blank.", Toast.LENGTH_SHORT).show()
        }
    }

    // return to view brand fragment
    private fun returnToViewBrands() {
        val action =
            AddBrandFragmentDirections.actionAddBrandFragmentToNavSelectBrand()
        NavHostFragment.findNavController(this).navigate(action)
    }

    override fun onPause() {
        super.onPause()
        // save brand name on pause
        viewModel.brandName = binding.addBrandName.text.toString()
    }

    override fun onResume() {
        super.onResume()
        // repopulate brand name on resume
        binding.addBrandName.setText(viewModel.brandName)
    }
}