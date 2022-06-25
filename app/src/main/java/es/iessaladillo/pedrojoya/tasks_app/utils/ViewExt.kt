package es.iessaladillo.pedrojoya.tasks_app.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

// Hace que la vista esté invisible a no ser que se cumpla la condición,
// en cuyo caso estará visible.
fun View.invisibleUnless(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.INVISIBLE
}

// Hace que la vista esté desaparecida (no ocupa espacio en pantalla)
// a no ser que se cumpla la condición, en cuyo caso estará visible.
fun View.goneUnless(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

// Oculta el teclado virtual.
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}
