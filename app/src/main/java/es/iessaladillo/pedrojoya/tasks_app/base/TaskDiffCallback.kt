package es.iessaladillo.pedrojoya.tasks_app.base

import androidx.recyclerview.widget.DiffUtil
import es.iessaladillo.pedrojoya.tasks_app.data.entity.Task

object TaskDiffCallback : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.concept == newItem.concept &&
        oldItem.createdAt == newItem.createdAt &&
        oldItem.completed == newItem.completed &&
        oldItem.completedAt == newItem.completedAt &&
        oldItem.category == newItem.category

}