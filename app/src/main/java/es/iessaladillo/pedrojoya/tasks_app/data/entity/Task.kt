package es.iessaladillo.pedrojoya.tasks_app.data.entity

// NO TOCAR
data class Task(
    val id: Long,
    val concept: String,
    val createdAt: String,
    var completed: Boolean,
    var completedAt: String,
    val category: Category
)
