package com.example.cinema

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R.*
import com.example.cinema.R.id.*
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.Client
import com.example.cinema.ui.theme.UserAdapter
import com.example.cinema.ui.theme.UserViewModel
import com.example.cinema.ui.theme.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ClientEditor : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_client_editor)

        val userDao = AppDatabase.getDatabase(application).clientDao()
        val factory = UserViewModelFactory(userDao)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(userViewModel,mutableListOf(), ::editUser, ::deleteUser)
        recyclerView.adapter = userAdapter

        loadUsers()


    }


    private fun loadUsers() {
        CoroutineScope(Dispatchers.Main).launch {
            val users = userViewModel.getAllUsers()
            userAdapter.updateUsers(users)
        }
    }

    private fun deleteUser(user: Client) {
        CoroutineScope(Dispatchers.IO).launch {
            userViewModel.deleteUser(user)
            loadUsers() // Обновляем список после удаления
        }
    }

    private fun editUser(user: Client) {
        CoroutineScope(Dispatchers.IO).launch {
            val d=findViewById<EditText>(email)
            val d1=findViewById<EditText>(pass)
            val d2=findViewById<Spinner>(role)
            val d3=findViewById<EditText>(data)
            user.email=d.text.toString()
            user.password=d1.text.toString()
            user.role=d2.selectedItem.toString()
            user.date=d3.text.toString()
            userViewModel.editUser(user)
            loadUsers()
        }
    }
}