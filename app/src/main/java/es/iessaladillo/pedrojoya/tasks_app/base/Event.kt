package es.iessaladillo.pedrojoya.tasks_app.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

// NO TOCAR

// Clase que representa un evento, cuyo contenido sólo puede ser obtenido
// una sola vez. El resto de las veces que se intenta se retorna null.
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}

// Permite observar directamente el contenido de un Event.
inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit) {
    observe(owner, { it.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}

// FIN DEJAR: NO TOCAR