package es.iessaladillo.pedrojoya.tasks_app.ui.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import es.iessaladillo.pedrojoya.tasks_app.R

// NO TOCAR
enum class TasksFilter(
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int,
    @StringRes val emptyMessageResId: Int,
) {
    All(
        titleResId = R.string.tasks_filter_all,
        iconResId = R.drawable.ic_filter_task_all,
        emptyMessageResId = R.string.tasks_no_tasks_yet,
    ),
    Pending(
        titleResId = R.string.tasks_filter_pending,
        iconResId = R.drawable.ic_filter_task_pending,
        emptyMessageResId = R.string.tasks_no_pending_tasks_yet,
    ),
    Completed(
        titleResId = R.string.tasks_filter_completed,
        iconResId = R.drawable.ic_filter_task_completed,
        emptyMessageResId = R.string.tasks_no_completed_tasks_yet
    );

    companion object {
        val Default: TasksFilter = All
    }
}