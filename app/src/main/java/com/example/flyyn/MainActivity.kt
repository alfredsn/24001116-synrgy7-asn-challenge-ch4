package com.example.flyyn

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = applicationContext.getSharedPreferences("login_data", Context.MODE_PRIVATE) // Initialize sharedPreferences

        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        if (username!!.isNotEmpty() && password!!.isNotEmpty()) {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.notesFragment)
        } else {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.loginFragment)
        }

        navController = findNavController(R.id.nav_host_fragment)

        setupToolbarMenu(navController)

    }

    private fun setupToolbarMenu(navController: NavController) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.favoritesFragment -> {
                    toolbar.menu.clear()
                }
                else -> {
                    toolbar.inflateMenu(R.menu.menu_main)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                navController.navigate(R.id.action_notesFragment_to_favoritesFragment)
                true
            }
            R.id.menu_logout -> {
                clearLoginData()
                navController.navigate(R.id.action_notesFragment_to_loginFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearLoginData() {
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    @Deprecated("This method has been deprecated in favor of using the" +
            "\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}." +
            "\n      The OnBackPressedDispatcher controls how back button events are dispatched" +
            "\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.notesFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}