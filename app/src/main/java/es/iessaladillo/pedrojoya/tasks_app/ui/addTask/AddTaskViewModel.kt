package es.iessaladillo.pedrojoya.tasks_app.ui.addTask

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.tasks_app.data.DataSource
import es.iessaladillo.pedrojoya.tasks_app.data.Database
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Category
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task

class AddTaskViewModel(
    private val dataSource: DataSource,
    private val application: Application,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var selectedCategory: Category = Category.Default

    fun createTask(concept: String, category: Category) {
        dataSource.addTask(concept, category)
    }


}