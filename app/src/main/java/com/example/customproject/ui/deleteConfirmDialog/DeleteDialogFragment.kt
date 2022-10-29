package com.example.customproject.ui.deleteConfirmDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.customproject.DatabaseApplication
import com.example.customproject.R
import com.example.customproject.ui.viewbrand.ViewBrandViewModel
import com.example.customproject.ui.viewbrand.ViewBrandViewModelFactory
import kotlinx.coroutines.launch

class DeleteDialogFragment : DialogFragment() {
    private val viewModel : DeleteBrandViewModel by activityViewModels {
        DeleteBrandViewModelFactory(
            (activity?.application as DatabaseApplication).database.brandDao()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // build delete confirmation dialog
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val brand = DeleteDialogFragmentArgs.fromBundle(requireArguments()).brand!!
            builder.setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete ${brand.brandName}?")
                    .setPositiveButton("Confirm") { _, _ ->
                        // delete brand
                        lifecycleScope.launch {
                            viewModel.deleteBrand(brand)
                        }
                    }
                .setNegativeButton("Cancel") { _, _ ->
                        // cancel delete brand
                        dismiss()
                    }
            builder.create()
        } ?: throw IllegalStateException("No activity found")
    }
}