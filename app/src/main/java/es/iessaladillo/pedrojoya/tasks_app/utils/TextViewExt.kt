package es.iessaladillo.pedrojoya.tasks_app.utils

import android.graphics.Paint
import android.widget.TextView

// Tacha o no el texto de un Textview
fun TextView.strikeThrough(strike: Boolean) {
    paintFlags = if (strike) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
