package es.iessaladillo.pedrojoya.tasks_app.data.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import es.iessaladillo.pedrojoya.tasks_app.R

enum class Category(
    @StringRes val title: Int,
    @DrawableRes val iconResId: Int
) {
    Other(R.string.category_other, R.drawable.ic_category_other),
    Home(R.string.category_home, R.drawable.ic_category_home),
    Work(R.string.category_work, R.drawable.ic_category_work),
    Leisure(R.string.category_leisure, R.drawable.ic_category_leisure),
    Family(R.string.category_family, R.drawable.ic_category_family);

    companion object {
        val Default: Category = Other
    }
}