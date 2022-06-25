package es.iessaladillo.pedrojoya.tasks_app.utils

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

// NO TOCAR

fun ComponentActivity.addMenuProvider(@MenuRes menuResId: Int, onMenuItemClick: (menuItem: MenuItem) -> Boolean) {
    addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuResId, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            onMenuItemClick(menuItem)
    })
}

fun ComponentActivity.addMenuProvider(@MenuRes menuResId: Int, owner: LifecycleOwner, onMenuItemClick: (menuItem: MenuItem) -> Boolean) {
    addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuResId, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            onMenuItemClick(menuItem)
    }, owner)
}

fun ComponentActivity.addMenuProvider(@MenuRes menuResId: Int, owner: LifecycleOwner, state: Lifecycle.State, onMenuItemClick: (menuItem: MenuItem) -> Boolean) {
    addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuResId, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
            onMenuItemClick(menuItem)
    }, owner, state)
}