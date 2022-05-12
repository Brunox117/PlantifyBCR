package mx.itesm.bcr.plantifybcr

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePIcker(val listener:(String)->Unit):DialogFragment(),TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hora = calendar.get(Calendar.HOUR_OF_DAY)
        val minutos = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(requireContext(),this,hora,minutos,true)
        return dialog
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        listener("$p1:$p2")
    }

}