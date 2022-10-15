package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customproject.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {
    private lateinit var binding : FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        InfoFragmentArgs.fromBundle(requireArguments()).item?.let {
            binding.infoBrandEdit.setText(it.brand.toString())
            binding.infoDateEdit.setText(it.date)
            binding.infoNameEdit.setText(it.name)
            binding.infoLowPriceEdit.setText(it.price.toString())
            binding.infoSellerEdit.setText(it.seller)
        }

        return binding.root
    }
}