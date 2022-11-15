package com.app.habittracker.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.habittracker.R
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.databinding.ActivityFormHabitBinding
import com.app.habittracker.databinding.LayoutDailyGoalBottomSheetBinding
import com.app.habittracker.presentation.home.viewModels.CreateHabitViewModel
import com.app.habittracker.utils.DialogUtils
import com.app.habittracker.utils.Utility
import com.app.habittracker.utils.extension.hide
import com.app.habittracker.utils.extension.show
import com.app.habittracker.utils.extension.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.*


@AndroidEntryPoint
class FormHabitActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFormHabitBinding
    private val viewModel : CreateHabitViewModel by viewModels()


    val listDailyGoalTemp = mutableListOf<DailyGoal>()

    // DailyGoal
    // id, habitId, type, name, duration, reminder, doAt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Repeating
        for (i in 0 .. binding.layoutHabitDays.childCount - 1) {
            var toggleButton = binding.layoutHabitDays.getChildAt(i) as ToggleButton
            toggleButton.setOnClickListener {
                if(toggleButton.isChecked){
                    viewModel.updateHabitDays(i, 1)
                }
                else
                    viewModel.updateHabitDays(i, 0)

                var str = ""
                for (i in viewModel.habitDays.value!!){
                    str+= i
                }
                toast(str)
            }
        }

        viewModel.duration.observe(this, {
            if(viewModel.dailyGoalRes.value == "Durasi") {
                val h = it / 60
                val m = it % 60
                var result = ""
                if (h != 0) result += "${h}j "
                result += "${m}m"
                binding.tvValueDurationOrRepeatPicker.text = result
            }
        })

        viewModel.repeatCount.observe(this, {
            if(viewModel.dailyGoalRes.value == "Pengulangan") {
                binding.tvValueDurationOrRepeatPicker.text = "${it}x"
            }
        })

        viewModel.dailyGoalRes.observe(this, {
            binding.tvDailyGoalType.text = it
            when (it) {
                "Off" -> {
                    binding.layoutShowDurationOrRepeatPicker.visibility = View.GONE
                    binding.viewSeparatorDailyTarget.visibility = View.GONE
                }
                "Durasi" -> {
                    binding.tvLabelShowDurationOrRepeatPicker.text = "Durasi"
                    binding.layoutShowDurationOrRepeatPicker.show()
                    binding.viewSeparatorDailyTarget.hide()

                    // Set Time
                    binding.layoutParentSetTimePicker.show()
                    binding.swActivateReminder.show()
                    binding.viewSeparatorSetTime.show()

                    // set text
                    binding.tvValueDurationOrRepeatPicker.text = "${viewModel.duration.value}m"

                    binding.tvDoAt.text = "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}"
                }
                "Pengulangan" -> {
                    binding.tvLabelShowDurationOrRepeatPicker.text = "Jumlah Pengulangan"
                    binding.layoutShowDurationOrRepeatPicker.show()
                    binding.viewSeparatorDailyTarget.show()

                    // Set Time
                    binding.layoutParentSetTimePicker.show()
                    binding.swActivateReminder.hide()
                    binding.viewSeparatorSetTime.hide()

                    // set text
                    binding.tvValueDurationOrRepeatPicker.text = "${viewModel.repeatCount.value}x"

                    binding.tvDoAt.text = "belum diatur"
                }
            }
        })

        // Listener
        with(binding) {
            imgIconNewHabit.setOnClickListener {
                val bottomSheetDialog =
                    BottomSheetDialog(this@FormHabitActivity, R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater.from(applicationContext)
                    .inflate(
                        R.layout.layout_daily_goal_bottom_sheet,
                        findViewById<LinearLayout>(R.id.bottom_sheet_icon_picker_container)
                    )
                bottomSheetView.findViewById<Button>(R.id.button_share).setOnClickListener {
                    Toast.makeText(this@FormHabitActivity, "shared ..", Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismissWithAnimation = true
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }


            binding.layoutShowDailyGoalPicker.setOnClickListener {
                val bottomSheetDialog = BottomSheetDialog(this@FormHabitActivity, R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater.from(applicationContext)
                    .inflate(
                        R.layout.layout_daily_goal_bottom_sheet,
                        findViewById<LinearLayout>(R.id.bottom_sheet_daily_goal_container)
                    )
                bottomSheetDialog.setContentView(bottomSheetView)
                val bottomSheetBinding = LayoutDailyGoalBottomSheetBinding.bind(bottomSheetView)

                with(bottomSheetBinding) {

                    when (viewModel.dailyGoalRes.value) {
                        "Off" -> rdoDailyGoalOff.isChecked = true
                        "Durasi" -> rdoDailyGoalDuration.isChecked = true
                        "Pengulangan" -> rdoDailyGoalRepeat.isChecked = true
                    }

                    buttonSave.setOnClickListener {
                        val selectedRdoId =
                            bottomSheetBinding.rdoGroupDailyGoal.checkedRadioButtonId
                        val selectedRdo = bottomSheetDialog.findViewById<RadioButton>(selectedRdoId)
                        viewModel.dailyGoalRes.value = selectedRdo?.text.toString()
                        bottomSheetDialog.dismissWithAnimation = true
                        bottomSheetDialog.dismiss()
                    }
                }
                bottomSheetDialog.show()
            }

            binding.layoutShowDurationOrRepeatPicker.setOnClickListener {
                if(tvDailyGoalType.text == "Durasi") {
                    DialogUtils.showDurationPicker(this@FormHabitActivity, viewModel.duration.value, object : DialogUtils.DailyGoalListener {
                        override fun handleClick(totalValue: Int) {
                            viewModel.duration.value = totalValue
                        }

                    })
                } else if(tvDailyGoalType.text == "Pengulangan") {
                    DialogUtils.showRepeatCountPicker(this@FormHabitActivity, viewModel.repeatCount.value, object : DialogUtils.DailyGoalListener {
                        override fun handleClick(totalValue: Int) {
                            viewModel.repeatCount.value = totalValue
                        }

                    })
                }

            }

            layoutDoAtPicker.setOnClickListener {
                if (viewModel.dailyGoalRes.value == "Pengulangan") {
                    val bottomSheetDialog = BottomSheetDialog(this@FormHabitActivity, R.style.BottomSheetDialogTheme)
                    val bottomSheetView = LayoutInflater.from(applicationContext)
                        .inflate(
                            R.layout.layout_duration_bottom_sheet,
                            findViewById<LinearLayout>(R.id.bottom_sheet_time_periods_container)
                        )
//            bottomSheetView.findViewById<Button>(R.id.button_share).setOnClickListener {
//                Toast.makeText(this, "shared ..", Toast.LENGTH_SHORT).show()
//                bottomSheetDialog.dismissWithAnimation = true
//                bottomSheetDialog.dismiss()
//            }
                    bottomSheetDialog.setContentView(bottomSheetView)
                    bottomSheetDialog.show()
                } else {
                    DialogUtils.showTimeDialog(this@FormHabitActivity, Utility.getCurrentTime(),object : DialogUtils.DialogDateListener {
                        override fun handleClick(value: String) {
                            viewModel.doAt.value = value
                        }

                    })
                }
            }

            layoutShowEndOnPicker.setOnClickListener {
                DialogUtils.showDateDialog(this@FormHabitActivity,Utility.getCurrentDate(),object : DialogUtils.DialogDateListener {
                    override fun handleClick(value: String) {
                        viewModel.endAt.value = value
                    }
                })

            }

            btnSaveNewHabit.setOnClickListener { insertHabit() }
        }
    }


    fun insertHabit(){
        runBlocking {
            val dailyGoals = mutableListOf<DailyGoal>()
            val dailyGoal  = DailyGoal(
                id = (0..100).random(),
                name = "Durasi ${(0..100).random()}",
                type = viewModel.dailyGoalRes.value!!,
                duration = viewModel.duration.value!!,
                reminder = true,
                doAt = viewModel.doAt.toString()
            )

            dailyGoals.add(dailyGoal)

            val habit = Habit(
                id = (0..100).random(),
                name = if(binding.etHabitName.text.toString().isEmpty()) "Habit Baru" else binding.etHabitName.text.toString(),
                icon = "",
                color = "",
                habitDay = viewModel.habitDays.value.toString(),
                dailyGoals = dailyGoals,
                endOn = viewModel.endAt.toString()
            )
            viewModel.insertHabit(habit)
            finish()
        }
    }
}