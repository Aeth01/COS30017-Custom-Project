package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.customproject.databinding.FragmentViewItemsBinding

class ViewItemsFragment : Fragment() {
    private lateinit var binding : FragmentViewItemsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewItemsBinding.inflate(inflater, container, false)

        return binding.root
    }
}