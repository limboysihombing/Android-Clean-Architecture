package com.app.habittracker.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.NumberPicker
import com.app.habittracker.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

object DialogUtils {
    interface DialogListener {
        fun handleClick()
    }

    interface DialogOptionListener {
        fun handlePositiveButton()
        fun handleNegativeButton()
    }

    interface DialogInputListener {
        fun handleClick(value: String)
    }

    interface DialogListListener {
        fun handleClick(position: Int)
    }

    interface DialogDateListener {
        fun handleClick(value: String)
    }

    interface DailyGoalListener {
        fun handleClick(totalValue: Int)
    }


    fun showDurationPicker(context: Context, currentDuration: Int?, dailyGoalListener: DailyGoalListener) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        val inflater = LayoutInflater.from(context).inflate(R.layout.layout_duration_bottom_sheet,null)
        val npHours = inflater.findViewById<NumberPicker>(R.id.npHours)
        val npMinute = inflater.findViewById<NumberPicker>(R.id.npMinute)

        var currentHour = 0
        var currentMinute = 0
        if(currentDuration != null) {
            currentHour = currentDuration / 60
            currentMinute = currentDuration % 60
        }

        val listHours = mutableListOf<String>()
        val listMinutes = mutableListOf<String>()
        for (i in 0..23)
            listHours.add("${i}jam")

        for (i in 0..59)
            listMinutes.add("${i}menit")

        npHours.minValue = 0
        npHours.maxValue = 23
        npHours.value = currentHour
        npHours.wrapSelectorWheel = false
        npHours.displayedValues = listHours.toTypedArray()

        npMinute.minValue = 0
        npMinute.maxValue = 59
        npMinute.value = currentMinute
        npMinute.wrapSelectorWheel = false
        npMinute.displayedValues = listMinutes.toTypedArray()


        inflater.findViewById<Button>(R.id.btnSaveDuration).setOnClickListener {
            dailyGoalListener.handleClick(npHours.value * 60 + npMinute.value)
            bottomSheetDialog.dismissWithAnimation = true
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(inflater)
        bottomSheetDialog.show()

    }

    fun showRepeatCountPicker(context: Context, currentCount: Int?, dailyGoalListener: DailyGoalListener) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        val inflater = LayoutInflater.from(context).inflate(R.layout.layout_repeats_bottom_sheet,null)
        val npCount = inflater.findViewById<NumberPicker>(R.id.npRepeatCount)


        val listCount = mutableListOf<String>()
        for (i in 2..100)
            listCount.add("${i}x")


        npCount.minValue = 2
        npCount.maxValue = 100
        npCount.value = currentCount ?: 2
        npCount.wrapSelectorWheel = false
        npCount.displayedValues = listCount.toTypedArray()

        inflater.findViewById<Button>(R.id.btnSaveRepeatCount).setOnClickListener {
            dailyGoalListener.handleClick(npCount.value)
            bottomSheetDialog.dismissWithAnimation = true
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(inflater)
        bottomSheetDialog.show()
    }

    fun showRequestErrorDialog(context: Context, title: String?, message: String) {
        AlertDialog.Builder(context).apply {
            setTitle(title ?: context.getString(R.string.request_error))
            setMessage(message)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.close), null)
        }.create().show()
    }

    fun showRequestErrorDialog(context: Context, message: Int) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.request_error))
            setMessage(message)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.close), null)
        }.create().show()
    }

    fun showAlertDialog(context: Context, title: Int, message: Int) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.close), null)
        }.create().show()
    }

    fun showAlertDialog(context: Context, title: Int, message: String) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(true)
            setPositiveButton(context.getString(R.string.close), null)
        }.create().show()
    }

    fun showActionAlertDialog(context: Context, title: Int, message: String, dialogListener: DialogListener) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(title))
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.getString(R.string.close)) { _, _ ->
                dialogListener.handleClick()
            }
        }.create().show()
    }

    fun showActionAlertDialog(context: Context, title: String, message: String, dialogListener: DialogListener) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.getString(R.string.close)) { _, _ ->
                dialogListener.handleClick()
            }
        }.create().show()
    }

    fun showActionAlertDialog(context: Context, title: Int, message: Int, dialogListener: DialogListener) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(title))
            setMessage(context.getString(message))
            setCancelable(false)
            setPositiveButton(context.getString(R.string.close)) { _, _ ->
                dialogListener.handleClick()
            }
        }.create().show()
    }

    fun showOptionAlertDialog(context: Context, title: Int, message: String, dialogOptionListener: DialogOptionListener) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(title))
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                dialogOptionListener.handlePositiveButton()
            }
            setNegativeButton(context.getString(R.string.no)) { _, _ ->
                dialogOptionListener.handleNegativeButton()
            }
        }.create().show()
    }

    fun showOptionAlertDialog(context: Context, title: Int, message: Int, dialogOptionListener: DialogOptionListener) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(title))
            setMessage(context.getString(message))
            setCancelable(false)
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                dialogOptionListener.handlePositiveButton()
            }
            setNegativeButton(context.getString(R.string.no)) { _, _ ->
                dialogOptionListener.handleNegativeButton()
            }
        }.create().show()
    }

    fun showOptionAlertDialog(context: Context, title: String, message: String, dialogOptionListener: DialogOptionListener) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setCancelable(false)
            setPositiveButton(context.getString(R.string.yes)) { _, _ ->
                dialogOptionListener.handlePositiveButton()
            }
            setNegativeButton(context.getString(R.string.no)) { _, _ ->
                dialogOptionListener.handleNegativeButton()
            }
        }.create().show()
    }

//	fun showSingleInputDialog(context: Context, title: String, dialogInputListener: DialogInputListener) {
//		val inflater = LayoutInflater.from(context).inflate(R.layout.dialog_single_input, null)
//		val input = inflater.findViewById<TextInputEditText>(R.id.inputDialog)
//		if (title == context.getString(R.string.reset_password)) {
//			inflater.findViewById<TextView>(R.id.descTv).show()
//		}
//		input.hint = context.getString(R.string.enter) + " " + title
//		AlertDialog.Builder(context).apply {
//			setTitle(title)
//			setCancelable(false)
//			setView(inflater)
//			setPositiveButton(R.string.ok) { _, _ ->
//				dialogInputListener.handleClick(input.trim())
//			}
//			setNegativeButton(R.string.cancel) { _, _ -> }
//		}.create().show()
//	}
//
//	fun showSingleInputDialog(context: Context, mainTitle: String, editTextTitle: String, hint: String, value: String?, inputType: Int, dialogInputListener: DialogInputListener) {
//		val inflater = LayoutInflater.from(context).inflate(R.layout.dialog_single_input, null)
//		inflater.findViewById<TextView>(R.id.editTextTitleTv).text = editTextTitle
//		val input = inflater.findViewById<TextInputEditText>(R.id.inputDialog)
//		input.hint = hint
//		if (!value.isNullOrBlank()) input.setText(value)
//		input.inputType = inputType
//		AlertDialog.Builder(context).apply {
//			setTitle(mainTitle)
//			setCancelable(false)
//			setView(inflater)
//			setPositiveButton(R.string.ok) { _, _ ->
//				dialogInputListener.handleClick(input.trim())
//			}
//			setNegativeButton(R.string.cancel) { _, _ -> }
//		}.create().show()
//	}
//
	fun showDateDialog(context: Context, date: String, dialogDateListener: DialogDateListener) {
		val calendar = Calendar.getInstance()
		val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
			calendar.set(Calendar.YEAR, year)
			calendar.set(Calendar.MONTH, month)
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
			val sdf = SimpleDateFormat(Constant.FORMAT_ISO_DATE, Locale.getDefault())
			dialogDateListener.handleClick(sdf.format(calendar.time))
		}
		val getYear = try { date.substring(Constant.DATE_ISO_YEAR_RANGE).toInt() } catch (e: Exception) { calendar.get(Calendar.YEAR) }
		val getMonth = try { date.substring(Constant.DATE_ISO_MONTH_RANGE).toInt() } catch (e: Exception) { calendar.get(Calendar. MONTH) }
		val getDay = try { date.substring(Constant.DATE_ISO_DATE_RANGE).toInt() } catch (e: Exception) { calendar.get(Calendar.DAY_OF_MONTH) }

		val datePickerDialog = DatePickerDialog(context, android.app.AlertDialog.THEME_HOLO_LIGHT, datePicker,
				getYear, getMonth - 1, getDay)
		datePickerDialog.show()
	}

	fun showTimeDialog(context: Context, time: String, dialogDateListener: DialogDateListener) {
		val calendar = Calendar.getInstance()
		val timePicker = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
			calendar.set(Calendar.MINUTE, minute)
			val sdf = SimpleDateFormat(Constant.FORMAT_TIME_SORT, Locale.getDefault())
			dialogDateListener.handleClick(sdf.format(calendar.time))
		}
		val getHour = try { time.substring(Constant.HOUR_RANGE).toInt() } catch (e: Exception) { calendar.get(Calendar.HOUR_OF_DAY) }
		val getMinute = try { time.substring(Constant.MINUTE_RANGE).toInt() } catch (e: Exception) { calendar.get(Calendar.MINUTE) }

		val timePickerDialog = TimePickerDialog(context, android.app.AlertDialog.THEME_HOLO_LIGHT, timePicker,
				getHour, getMinute, true)
		timePickerDialog.show()
	}


    fun showListDialog(context: Context, title: Int, itemList: List<String>, dialogListListener: DialogListListener) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(title))
            setCancelable(false)
            setItems(itemList.toTypedArray()) { _, which ->
                dialogListListener.handleClick(which)
            }

            setNegativeButton(context.getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }
}
