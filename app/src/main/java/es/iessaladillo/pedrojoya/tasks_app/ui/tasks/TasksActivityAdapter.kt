package es.iessaladillo.pedrojoya.tasks_app.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.tasks_app.R
import es.iessaladillo.pedrojoya.tasks_app.base.TaskDiffCallback
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task
import es.iessaladillo.pedrojoya.tasks_app.databinding.TasksActivityItemBinding
import es.iessaladillo.pedrojoya.tasks_app.utils.strikeThrough

typealias OnItemClickListener = (position: Int) -> Unit

class TasksActivityAdapter: RecyclerView.Adapter<TasksActivityAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var data: List<Task> = emptyList()

    fun getItem(position: Int) = data[position]

    fun submitList(newData: List<Task>) {
        data = newData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = data.size

    //Creo el viewHolder de acuerdo al layout asociado a los elementos de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TasksActivityItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(private val b: TasksActivityItemBinding) : RecyclerView.ViewHolder(b.root) {
        private val viewBar: View = b.viewBar
        private val iconTask: ImageView = b.iconTask
        private val txtConcept: TextView = b.txtConcept
        private val txtInfo: TextView = b.txtInfo
        private val chkCompleted: CheckBox = b.chkCompleted

        //Asocio un listener al usar el checkbox de un elemento.
        init {
            chkCompleted.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }
        }

        //Configura cada elemento de la lista correspondiéndose a los atributos de cada tarea.
        fun bind(task: Task){
            task.run{
                iconTask.setImageResource(category.iconResId)
                txtConcept.text = concept
                chkCompleted.isChecked = completed
                if(completed){
                    viewBar.setBackgroundColor(itemView.context.resources.getColor(R.color.completedTask))
                    txtInfo.text = String.format(itemView.context.getText(R.string.tasks_item_completedAt).toString(), completedAt)
                    txtConcept.strikeThrough(true)
                }else{
                    viewBar.setBackgroundColor(itemView.context.resources.getColor(R.color.pendingTask))
                    txtInfo.text = String.format(itemView.context.getText(R.string.tasks_item_createdAt).toString(), createdAt)
                    txtConcept.strikeThrough(false)
                }
            }
        }
    }

}
