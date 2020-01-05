package com.rental_apps.android.rental_apps.helper

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.Gravity
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Muhajir on 20/03/2017.
 */
/**
 * Date picker widget.
 *
 * @author bgamard
 */
class DatePickerView : EditText, OnDateSetListener {
    private var date: Date? = null

    //  private Date previousSelectedDate;
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setAttributes()
    }

    constructor(context: Context?) : super(context) {
        setAttributes()
    }

    private fun setAttributes() {
        hint = "Select Date"
        gravity = Gravity.LEFT or Gravity.CENTER
        isFocusable = false
        // setTextSize(18);
//  setPadding(10, 10, 10, 10);
        setOnClickListener {
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            val datePicker = DatePickerDialog(
                    this@DatePickerView.context, this@DatePickerView,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH])
            datePicker.setCancelable(false)
            // datePicker.setCanceledOnTouchOutside(true);
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL") { dialog, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss()
                }
            }
            datePicker.show()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = GregorianCalendar(year, monthOfYear, dayOfMonth).time
        setDate(date)
    }

    fun setDate(date: Date?) {
        if (date != null) {
            this.date = date
            val newformat = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate = newformat.format(date)
            setText(formattedDate)
        } else {
            setText("")
        }
    }

    fun getDate(): Date? {
        return date
    }

    val modifiedDate: Calendar
        get() {
            val calendar = Calendar.getInstance()
            if (date != null) {
                calendar.time = date
            }
            return calendar
        }
}