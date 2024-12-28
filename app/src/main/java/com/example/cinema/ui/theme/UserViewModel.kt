package com.example.cinema.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: ClientDao) : ViewModel() {

    suspend fun getAllUsers() = userDao.getAllClients()

    fun deleteUser(user: Client) {
        viewModelScope.launch {
            userDao.delete(user)
        }
    }
    fun editUser(user: Client) {
        viewModelScope.launch {
            userDao.update(user)
        }
    }
}