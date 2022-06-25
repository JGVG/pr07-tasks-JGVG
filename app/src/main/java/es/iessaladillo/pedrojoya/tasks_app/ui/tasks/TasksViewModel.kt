package es.iessaladillo.pedrojoya.tasks_app.ui.tasks

import android.app.Application
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.tasks_app.R
import es.iessaladillo.pedrojoya.tasks_app.data.DataSource
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task

class TasksViewModel(
    val dataSource: DataSource,
    private val application: Application,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //Hacemos que el filtro sea un MutableLiveData
    var filterOption: MutableLiveData<TasksFilter> = MutableLiveData(TasksFilter.Default)
    var filterIsGone = false

    //Cada vez que el filtro cambie, también lo hará los valores de la lista dinámicamente
    val tasks: LiveData<List<Task>> = filterOption.switchMap { filter ->
            when(filter!!){
                TasksFilter.All -> dataSource.queryAllTasks()
                TasksFilter.Completed -> dataSource.queryCompletedTasks()
                TasksFilter.Pending -> dataSource.queryPendingTasks()
            }
        }

    fun checkFilterOption(tasksFilter: TasksFilter){
        filterOption.value = when(tasksFilter){
            TasksFilter.All -> {
                TasksFilter.All
            }
            TasksFilter.Pending ->{
                TasksFilter.Pending
            }
            TasksFilter.Completed ->{
                TasksFilter.Completed
            }
        }
    }

    fun deleteTask(task: Task) {
       dataSource.deleteTask(task.id)
    }

    fun insertTask(task: Task) {
        dataSource.insertTask(task)
    }

    fun markTasksAsCompleted() {
        var idList : List<Long> = emptyList()

        for(task in tasks.value!!){
            idList = idList + task.id
        }

        dataSource.markTasksAsCompleted(idList)
    }

    fun deleteTasks() {
        var idList : List<Long> = emptyList()

        for(task in tasks.value!!){
            idList = idList + task.id
        }
        dataSource.deleteTasks(idList)
    }

    fun markTasksAsPending() {
        var idList : List<Long> = emptyList()

        for(task in tasks.value!!){
            idList = idList + task.id
        }
        dataSource.markTasksAsPending(idList)
    }

    //Responde si la lista está o no vacía en el momento de la llamada.
    fun isEmptyList(): Boolean {
        return tasks.value!!.isEmpty()
    }

}

