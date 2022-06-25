package es.iessaladillo.pedrojoya.tasks_app.ui.addTask

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.tasks_app.R
import es.iessaladillo.pedrojoya.tasks_app.data.Database
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Category
import es.iessaladillo.pedrojoya.tasks_app.databinding.AddTaskActivityBinding
import es.iessaladillo.pedrojoya.tasks_app.utils.addMenuProvider
import es.iessaladillo.pedrojoya.tasks_app.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.tasks_app.ui.tasks.TasksActivity

private const val STATE_CATEGORY: String = "STATE_CATEGORY"

class AddTaskActivity : AppCompatActivity() {

    //Implementación de un adaptador para la selección de categorías.
    private val listAdapter: ArrayAdapter<Category> by lazy {
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Category.values()
        )
    }
    private val addTasksViewModel: AddTaskViewModel by viewModels {
        AddTaskViewModelFactory(Database, application, this )
    }
    private val b: AddTaskActivityBinding by lazy { AddTaskActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        setupMenu()
        setupViews()
    }

    //Adaptador también en el onResume()
    override fun onResume() {
        super.onResume()
        b.actSystem.setAdapter(listAdapter)
    }

    private fun setupViews() {
        //SetUp
        b.filledTextField.hint = getText(R.string.add_task_lblConcept)
        b.tilSystem.hint = getText(R.string.add_task_lblCategory)
        b.actSystem.setText(addTasksViewModel.selectedCategory.title)
        b.tilSystem.setStartIconDrawable(addTasksViewModel.selectedCategory.iconResId)

        //Functionality
        b.actSystem.setOnItemClickListener { _, _, position, _ ->
            addTasksViewModel.selectedCategory = listAdapter.getItem(position)!!
            b.tilSystem.setStartIconDrawable(addTasksViewModel.selectedCategory.iconResId)
        }
    }

    private fun setupMenu() {
        addMenuProvider(R.menu.add_task_activity) { menuItem ->
            when (menuItem.itemId) {
                R.id.mnuSave -> {
                    if(checkErrors()){
                        b.edtConcept.hideKeyboard()
                        addTasksViewModel.createTask(b.edtConcept.text.toString(), addTasksViewModel.selectedCategory)
                        navigateToTaskActivity()
                    }

                    true
                }
                else -> false
            }
        }
    }

    //Cuando valido los campos obligatorio muestro o no un SnackBar de aviso
    private fun checkErrors(): Boolean{
        val check: Boolean
        if(b.edtConcept.text.toString().isNotEmpty()){
            b.filledTextField.error = null
            check = true
        }else{
            showSnackBar()
            check = false
        }
        return check
    }

    private fun showSnackBar() {
        Snackbar.make(b.root, getString(R.string.add_task_data_required),
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun navigateToTaskActivity(){
        startActivity(Intent(this, TasksActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}