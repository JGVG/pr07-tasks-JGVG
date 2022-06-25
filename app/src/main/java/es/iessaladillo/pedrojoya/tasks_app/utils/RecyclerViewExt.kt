package es.iessaladillo.pedrojoya.tasks_app.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// Recibe el código que queremos que se ejecute cuando se hace el
// gesto de swipe to dismiss sobre un RecyclerView. Por defecto, se
// ejecutará el código indicado tanto se haga swipe sobre la derecha
// como a la izquierda.
fun RecyclerView.setOnSwipeListener(
    swipeDirs: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
    onSwipe: (viewHolder: RecyclerView.ViewHolder, direction: Int) -> Unit =
        { _, _ -> }
) {
    val itemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(0, swipeDirs) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSwipe(viewHolder, direction)
            }
        })
    itemTouchHelper.attachToRecyclerView(this)
}