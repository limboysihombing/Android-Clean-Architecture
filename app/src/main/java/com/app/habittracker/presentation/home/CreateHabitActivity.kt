package com.app.habittracker.presentation.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.app.habittracker.R
import com.app.habittracker.core.domain.model.DailyGoal
import com.app.habittracker.core.domain.model.Habit
import com.app.habittracker.databinding.ActivityCreateHabitBinding
import com.app.habittracker.databinding.LayoutDailyGoalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.*


@AndroidEntryPoint
class CreateHabitActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateHabitBinding
    private val homeViewModel : HomeViewModel by viewModels()

    var habitDays = mutableListOf(1, 1, 1, 1, 1, 1, 1)
    lateinit var dailyGoalTemp: DailyGoalTemp
    val listDailyGoalTemp = mutableListOf<DailyGoal>()

    var dailyGoalRes = "Off"
    var duration = 15
    var repeatCount = 1
    var endOn = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateLayoutDailyTarget(dailyGoalRes)

        dailyGoalTemp = DailyGoalTemp("0", Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString(), false)


        // Toggle Button
        for (i in 0 .. binding.layoutHabitDays.childCount - 1) {
            var toggleButton = binding.layoutHabitDays.getChildAt(i) as ToggleButton
            toggleButton.setOnClickListener {
                if(toggleButton.isChecked)
                    habitDays[i] = 1
                else
                    habitDays[i] = 0

                var str = ""
                for (i in habitDays){
                    str+= i
                }
                showToast(str)
            }
        }

        // Listener
        binding.imgIconNewHabit.setOnClickListener {
            var bottomSheetDialog =  BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext)
                    .inflate(
                            R.layout.layout_daily_goal_bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet_icon_picker_container)
                    )
            bottomSheetView.findViewById<Button>(R.id.button_share).setOnClickListener {
                Toast.makeText(this, "shared ..", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismissWithAnimation = true
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        binding.layoutShowDailyGoalPicker.setOnClickListener {
            val bottomSheetDialog =  BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext)
                    .inflate(
                            R.layout.layout_daily_goal_bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet_daily_goal_container)
                    )
            bottomSheetDialog.setContentView(bottomSheetView)
            val bottomSheetBinding = LayoutDailyGoalBottomSheetBinding.bind(bottomSheetView)

            with(bottomSheetBinding){

                when(dailyGoalRes) {
                    "Off" -> rdoDailyGoalOff.isChecked = true
                    "Durasi" -> rdoDailyGoalDuration.isChecked = true
                    "Pengulangan" -> rdoDailyGoalRepeat.isChecked = true
                }

                buttonSave.setOnClickListener {
                    val selectedRdoId = bottomSheetBinding.rdoGroupDailyGoal.checkedRadioButtonId
                    val selectedRdo = bottomSheetDialog.findViewById<RadioButton>(selectedRdoId)
                    dailyGoalRes = selectedRdo?.text.toString()
                    bottomSheetDialog.dismissWithAnimation = true
                    bottomSheetDialog.dismiss()

                    updateLayoutDailyTarget(dailyGoalRes)
                }
            }
            bottomSheetDialog.show()
        }

        binding.layoutShowDurationOrRepeatPicker.setOnClickListener {
            var bottomSheetDialog =  BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext)
                    .inflate(
                            R.layout.layout_duration_bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet_duration_container)
                    )
//            bottomSheetView.findViewById<Button>(R.id.button_share).setOnClickListener {
//                Toast.makeText(this, "shared ..", Toast.LENGTH_SHORT).show()
//                bottomSheetDialog.dismissWithAnimation = true
//                bottomSheetDialog.dismiss()
//            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        binding.layoutDoAtPicker.setOnClickListener {
            if(dailyGoalRes == "Pengulangan") {
                var bottomSheetDialog =  BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater.from(applicationContext)
                        .inflate(
                                R.layout.layout_duration_bottom_sheet, findViewById<LinearLayout>(R.id.bottom_sheet_time_periods_container)
                        )
//            bottomSheetView.findViewById<Button>(R.id.button_share).setOnClickListener {
//                Toast.makeText(this, "shared ..", Toast.LENGTH_SHORT).show()
//                bottomSheetDialog.dismissWithAnimation = true
//                bottomSheetDialog.dismiss()
//            }
                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            } else{
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)
                val timePickerDialog = TimePickerDialog(
                        this,
                        TimePickerDialog.OnTimeSetListener { timePicker, h, m -> dailyGoalTemp.time = h.toString()},
                        hour,
                        minute, true)
                timePickerDialog.show()
            }

        }

        binding.layoutShowEndOnPicker.setOnClickListener {
            val c = Calendar.getInstance()
            var mYear = c[Calendar.YEAR]
            var mMonth = c[Calendar.MONTH]
            var mDay = c[Calendar.DAY_OF_MONTH]


            val datePickerDialog = DatePickerDialog(this,
                    {
                        view, year, monthOfYear, dayOfMonth -> endOn = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    }, mYear, mMonth, mDay)

            datePickerDialog.show()
        }

        binding.btnSaveNewHabit.setOnClickListener { insertHabit() }
    }


    fun updateLayoutDailyTarget(dailyGoalRes: String) {
        when (dailyGoalRes) {
            "Off" -> {
                binding.tvDailyGoalType.text = "Off"
                binding.layoutShowDurationOrRepeatPicker.visibility = View.GONE
                binding.viewSeparatorDailyTarget.visibility = View.GONE
            }
            "Durasi" -> {
                binding.tvLabelShowDurationOrRepeatPicker.text = "Durasi"
                binding.tvDailyGoalType.text = "Durasi"
                binding.layoutShowDurationOrRepeatPicker.visibility = View.VISIBLE
                binding.viewSeparatorDailyTarget.visibility = View.VISIBLE

                // Set Time
                binding.layoutParentSetTimePicker.visibility = View.VISIBLE
                binding.swActivateReminder.visibility = View.VISIBLE
                binding.viewSeparatorSetTime.visibility = View.VISIBLE

                // set text
                binding.tvValueDurationOrRepeatPicker.text = "${duration}m"

                binding.tvDoAt.text = "${dailyGoalTemp.time}"
            }
            "Pengulangan" -> {
                binding.tvLabelShowDurationOrRepeatPicker.text = "Jumlah Pengulangan"
                binding.tvDailyGoalType.text = "Pengulangan"
                binding.layoutShowDurationOrRepeatPicker.visibility = View.VISIBLE
                binding.viewSeparatorDailyTarget.visibility = View.VISIBLE

                // Set Time
                binding.layoutParentSetTimePicker.visibility = View.VISIBLE
                binding.swActivateReminder.visibility = View.GONE
                binding.viewSeparatorSetTime.visibility = View.GONE

                // set text
                binding.tvValueDurationOrRepeatPicker.text = "${repeatCount}x"
                binding.tvDoAt.text = "belum diatur"
            }
        }
    }

    fun addDailyGoalTemp(time: String, useReminder: Boolean) {

    }

    fun insertHabit(){
        val habitId = (0..100).random()
        val dailyGoal  = DailyGoal(
                (0..100).random(),
                habitId,
                "Duration",
                "Durasi ${(0..100).random()}",
                15,
                true,
                Date().toString()
        )
        val list = mutableListOf<DailyGoal>()

        // Extract Habit Day
        var habitDays2 = ""
        habitDays.forEach{
            habitDays2 += it.toString()
        }
        if(dailyGoalRes != "Repeat"){

        }
        val habit = Habit(
                habitId,
                binding.etHabitName.text.toString(),
                "kfsd",
                "sfljk",
                habitDays2,
                list,
                Date().toString()
        );

        runBlocking {
            homeViewModel.insertHabit(habit)
            homeViewModel.insertDailyGoal(dailyGoal)
            finish()
        }
    }

    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(this.requireContext(), this, year, month, day)
        }

        override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
            TODO("Not yet implemented")
        }

    }

    class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener{
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val m = Calendar.getInstance().get(Calendar.MINUTE)
            return TimePickerDialog(this.requireContext(), this, h, m, true)
        }
        override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        }

    }

    data class DailyGoalTemp(var repNumber: String, var time: String, var useReminder: Boolean)

    private fun showToast(msg: String){
        Toast.makeText(this, "${msg}", Toast.LENGTH_SHORT).show()
    }
}