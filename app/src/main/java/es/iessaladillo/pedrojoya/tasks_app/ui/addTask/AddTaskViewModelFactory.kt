package es.iessaladillo.pedrojoya.tasks_app.ui.addTask

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.iessaladillo.pedrojoya.tasks_app.data.DataSource
import es.iessaladillo.pedrojoya.tasks_app.ui.tasks.TasksViewModel

class AddTaskViewModelFactory (private val datasource: DataSource,
                               private val application: Application,
                               owner: SavedStateRegistryOwner,
                               defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(key: String,
                                        modelClass: Class<T>,
                                        handle: SavedStateHandle
    ): T {
        return AddTaskViewModel(datasource, application, handle) as T
    }

}