package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.customproject.database.BrandItem
import com.example.customproject.databinding.FragmentAddBrandBinding
import com.example.customproject.databinding.FragmentAddItemBinding
import kotlinx.coroutines.launch

class AddBrandFragment : Fragment() {
    private lateinit var binding : FragmentAddBrandBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBrandBinding.inflate(inflater, container, false)

        binding.addBrandSubmit.setOnClickListener{
            addBrand()
        }

        return binding.root
    }

    private fun addBrand() {
        var toastMsg : String = ""
        val name = binding.addBrandName.text
                    .trim()
                    .toString()
        if (name != "") {
            if (isUniqueBrand(name)) {
                this.lifecycleScope.launch{
                    (activity?.application as DatabaseApplication).database.brandDao().insertAll(BrandItem(name="$name"))
                    Toast.makeText(context, "Added $name to Brands.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                toastMsg = "$name already exists in brands."
            }
        }
        else {
            toastMsg = "Please ensure name is not blank."
        }

        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
    }

    private fun isUniqueBrand(name : String) : Boolean {
        val list = (activity?.application as DatabaseApplication).database.brandDao().getBrandByName(name)

        return !list.isEmpty()
    }
}