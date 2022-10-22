package com.example.customproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.example.customproject.database.ConcreteItem
import com.example.customproject.databinding.FragmentStatisticsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class StatisticsFragment : Fragment() {
    private lateinit var data : List<ConcreteItem>
    private lateinit var binding : FragmentStatisticsBinding
    private val viewModel : StatisticsViewModel by activityViewModels {
        StatisticsViewModelFactory(
            (activity?.application as DatabaseApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        // get data
        viewModel.getData().asLiveData().observe(viewLifecycleOwner) {
            data = it
            binding.statsNoCards.text = getNumItems().toString()
            binding.statsLastAdded.text = getLatestDate()
            binding.statsAveragePrice.text = getAveragePrice().toString()
        }

        return binding.root
    }

    // get number of items
    private fun getNumItems() : Int {
        return data.count()
    }

    // get the latest date from data. Default = 01/01/2000
    private fun getLatestDate() : String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        var latestDateString = "01/01/2000"
        var latestDate = sdf.parse(latestDateString)

        for(row in data) {
            val date = sdf.parse(row.date)
            if (latestDate!!.before(date)) {
                latestDate = date
                latestDateString = row.date
            }
        }

        return latestDateString
    }

    // get average price of items. Return 0 minimum
    private fun getAveragePrice() : Float {
        var total = 0F
        for (row in data) {
            total += row.price
        }

        return total / min(data.count(), 1)
    }
}