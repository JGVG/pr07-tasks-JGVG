package es.iessaladillo.pedrojoya.tasks_app.ui.tasks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.tasks_app.R
import es.iessaladillo.pedrojoya.tasks_app.data.Database
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task
import es.iessaladillo.pedrojoya.tasks_app.databinding.TasksActivityBinding
import es.iessaladillo.pedrojoya.tasks_app.ui.addTask.AddTaskActivity
import es.iessaladillo.pedrojoya.tasks_app.utils.addMenuProvider
import es.iessaladillo.pedrojoya.tasks_app.utils.goneUnless
import es.iessaladillo.pedrojoya.tasks_app.utils.setOnSwipeListener

class TasksActivity : AppCompatActivity() {

    //Implementación de la listAdapter con su acción correspondiente.
    private val listAdapter: TasksActivityAdapter = TasksActivityAdapter().apply {
        setOnItemClickListener {
            changeStateTask(getItem(it))
        }
    }
    private val tasksViewModel: TasksViewModel by viewModels {
        TasksViewModelFactory(Database, application, this )
    }
    private val b: TasksActivityBinding by lazy { TasksActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        setupMenu()
        setupViews()
        observeTasks()
    }

    private fun setupViews(){
        //Setup
        title = String.format((getText(R.string.tasks_title) as String), getText(tasksViewModel.filterOption.value!!.titleResId))
        b.chipGroup.goneUnless(tasksViewModel.filterIsGone)
        b.txtFilter.goneUnless(tasksViewModel.filterIsGone)
        b.imgFilter.goneUnless(tasksViewModel.filterIsGone)
        b.txtNoTasks.text = getText(tasksViewModel.filterOption.value!!.emptyMessageResId)
        b.imgFilter.setImageResource(tasksViewModel.filterOption.value!!.iconResId)
        b.chipAll.text = getText(TasksFilter.All.titleResId)
        b.chipPending.text = getText(TasksFilter.Pending.titleResId)
        b.chipCompleted.text = getText(TasksFilter.Completed.titleResId)

        //Functionality
        b.chipAll.setOnClickListener { filterControls(TasksFilter.All) }
        b.chipPending.setOnClickListener{ filterControls(TasksFilter.Pending) }
        b.chipCompleted.setOnClickListener{ filterControls(TasksFilter.Completed) }
        b.iconAdd.setOnClickListener{ navigateToAddTaskActivity() }

        //Methods
        setUpRecycledView()

    }

    private fun setupMenu() {
        addMenuProvider(R.menu.tasks_activity) { menuItem ->
            when (menuItem.itemId) {
                R.id.mnuFilter -> {
                    tasksViewModel.filterIsGone = !tasksViewModel.filterIsGone

                    b.chipGroup.goneUnless(tasksViewModel.filterIsGone)
                    b.txtFilter.goneUnless(tasksViewModel.filterIsGone)
                    b.imgFilter.goneUnless(tasksViewModel.filterIsGone)
                    b.chipAll.isChecked = true
                    true
                }
                R.id.mnuAdd -> {
                    navigateToAddTaskActivity()
                    true
                }
                R.id.mnuDelete -> {
                    if(tasksViewModel.isEmptyList()){
                        Snackbar.make(b.root, getString(R.string.tasks_no_tasks_to_delete),
                            Snackbar.LENGTH_LONG).show()
                    }else{
                        tasksViewModel.deleteTasks()
                    }
                    true
                }
                R.id.mnuComplete -> {
                    tasksViewModel.markTasksAsCompleted()
                    true
                }
                R.id.mnuPending -> {
                    tasksViewModel.markTasksAsPending()
                    true
                }
                else -> false
            }
        }
    }

    //Observa el liveData de la lista, si está la lista vacía muestra un icono y un texto.
    private fun observeTasks() {
        tasksViewModel.tasks.observe(this){
            listAdapter.submitList( it )
            b.iconAdd.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
            b.txtNoTasks.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }

    //Configuración del recycledView con la acción de borrar al deslizar un elemento.
    private fun setUpRecycledView() {
        b.lstTasks.run{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter

            setOnSwipeListener { viewHolder, _ -> deleteTask(listAdapter.getItem(viewHolder.bindingAdapterPosition)) }
        }
    }

    //Controla el comportamiento de los chips al ser seleccionados
    private fun filterControls(tasksFilter: TasksFilter) {
        when(tasksFilter){
            TasksFilter.All -> {
                tasksViewModel.checkFilterOption(TasksFilter.All)
                b.chipAll.isChecked = true
                b.chipAll.isCheckable = false
                b.chipPending.isCheckable = true
                b.chipCompleted.isCheckable = true
                b.chipPending.isChecked = false
                b.chipCompleted.isChecked = false
            }
            TasksFilter.Pending ->{
                tasksViewModel.checkFilterOption(TasksFilter.Pending)
                b.chipAll.isCheckable = true
                b.chipPending.isCheckable = false
                b.chipCompleted.isCheckable = true
                b.chipAll.isChecked = false
                b.chipCompleted.isChecked = false
            }
            TasksFilter.Completed ->{
                tasksViewModel.checkFilterOption(TasksFilter.Completed)
                b.chipAll.isCheckable = true
                b.chipPending.isCheckable = true
                b.chipCompleted.isCheckable = false
                b.chipAll.isChecked = false
                b.chipPending.isChecked = false
            }
        }
        title = String.format((getText(R.string.tasks_title) as String), getText(tasksViewModel.filterOption.value!!.titleResId))
        b.imgFilter.setImageResource(tasksViewModel.filterOption.value!!.iconResId)
        b.txtNoTasks.text = getText(tasksViewModel.filterOption.value!!.emptyMessageResId)
    }

    //Cambia el estado de una tarea.
    private fun changeStateTask(task: Task) {
        if(!task.completed){
            tasksViewModel.dataSource.markTaskAsCompleted(task.id)
        }else{
            tasksViewModel.dataSource.markTaskAsPending(task.id)
        }
    }

    private fun deleteTask(task: Task){
        tasksViewModel.deleteTask(task)
        showSnackBar(task)
    }

    private fun showSnackBar(task: Task) {
        Snackbar.make(b.root, String.format(getString(R.string.tasks_task_deleted), task.id),
            Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.tasks_recreate)) { tasksViewModel.insertTask(task) }
            .show()
    }

    private fun navigateToAddTaskActivity(){
        startActivity(Intent(this, AddTaskActivity::class.java))
    }

}
