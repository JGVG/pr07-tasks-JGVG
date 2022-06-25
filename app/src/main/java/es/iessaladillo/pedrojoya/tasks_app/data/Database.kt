package es.iessaladillo.pedrojoya.tasks_app.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Category
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task
import java.text.SimpleDateFormat
import java.util.*

object Database : DataSource {

    private val tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())

    private lateinit var date : Date

    @SuppressLint("ConstantLocale")
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy, HH:mm:ss", Locale.US)

    //Se usa para dar un id único a cada tarea.
    private var idSequence: Long = 0

    override fun queryAllTasks(): LiveData<List<Task>> {
        return Transformations.map(tasks){
            list -> list.sortedByDescending{it.createdAt}
        }
    }

    override fun queryCompletedTasks(): LiveData<List<Task>> {
        return Transformations.map(tasks){
            list -> list.filter { it.completed }.sortedByDescending { it.createdAt }
        }
    }

    override fun queryPendingTasks(): LiveData<List<Task>> {
        return Transformations.map(tasks){
                list -> list.filter { !it.completed }.sortedByDescending { it.createdAt }
        }
    }

    override fun addTask(concept: String, category: Category) {
        date = Date()
        tasks.value = tasks.value!! + Task(idSequence, concept, simpleDateFormat.format(date.time), false, "no completed", category)
        incrementId()
    }

    override fun insertTask(task: Task) {
        tasks.value = tasks.value!! + Task(task.id, task.concept, task.createdAt, task.completed, task.completedAt, task.category)
    }

    override fun deleteTask(taskId: Long): Boolean {
        val newList = tasks.value!!.filter { it.id != taskId }
        val removed = newList.size < tasks.value!!.size
        tasks.value = newList
        return removed
    }

    override fun deleteTasks(taskIdList: List<Long>) {
        tasks.value = tasks.value!!.filter{it.id !in taskIdList}
    }

    override fun markTaskAsCompleted(taskId: Long) {
        date = Date()
        tasks.value = tasks.value!!.map{
            if(it.id == taskId){
                it.copy(
                    completed = true,
                    completedAt = simpleDateFormat.format(date.time)
                )
            }else{ it }
        }
    }

    override fun markTasksAsCompleted(taskIdList: List<Long>) {
        tasks.value!!.filter{it.id in taskIdList}
        tasks.value!!.map{
            markTaskAsCompleted(it.id)
        }
    }

    override fun markTaskAsPending(taskId: Long) {
        tasks.value = tasks.value!!.map{
            if(it.id == taskId){
                it.copy(
                    completed = false,
                    completedAt = "no completed"
                )
            }else{ it }
        }
    }

    override fun markTasksAsPending(taskIdList: List<Long>) {
        tasks.value!!.filter{it.id in taskIdList}
        tasks.value!!.map{
            markTaskAsPending(it.id)
        }
    }

    private fun incrementId(){
        idSequence++
    }

}
