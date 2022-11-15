package com.app.habittracker.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.habittracker.core.data.Resource
import com.app.habittracker.databinding.FragmentHomeBinding
import com.app.habittracker.presentation.home.viewModels.HomeViewModel
import com.vivekkaushik.datepicker.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(activity != null){
            val homeAdapter = HomeAdapter()

            homeViewModel.habits.observe(viewLifecycleOwner, { habit ->
                if (habit != null) {
                    when (habit) {
                        is Resource.Loading -> Log.w("->>-->>-->>-->--", "Loading")
                        is Resource.Success -> {
                            homeAdapter.setData(habit.data)
                        }
                    }
                }
            })

            with(binding.rvHome) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = homeAdapter
            }

            binding.fabOpenCreateActivity.setOnClickListener {
                val intent = Intent(activity, FormHabitActivity::class.java)
                startActivity(intent)
            }


            binding.datePickerTimeline.setInitialDate(2019, 3, 21)
            binding.datePickerTimeline.setOnDateSelectedListener(object : OnDateSelectedListener {
                override fun onDateSelected(year: Int, month: Int, day: Int, dayOfWeek: Int) {
                    // Do Something
                }

                override fun onDisabledDateSelected(
                    year: Int,
                    month: Int,
                    day: Int,
                    dayOfWeek: Int,
                    isDisabled: Boolean
                ) {
                    // Do Something
                }
            })

            // Disable date
            val dates: Array<Date> = arrayOf<Date>(Calendar.getInstance().getTime())
            binding.datePickerTimeline.deactivateDates(dates)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}