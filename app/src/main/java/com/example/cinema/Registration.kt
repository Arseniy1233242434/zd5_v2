package com.example.cinema

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class Registration : AppCompatActivity() {
    lateinit var DateButton:Button
    lateinit var Email:EditText
    lateinit var Password:EditText
lateinit var spinner: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        val items = arrayOf("Работник", "Клиент")
       spinner = findViewById(R.id.my_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    fun datepicker(view: View)
    {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DateButton=findViewById(R.id.datebutton)
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            DateButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day)
        datePickerDialog.show()
    }

    fun save(view: View)
    {
        Email=findViewById(R.id.email)
        Password=findViewById(R.id.password)
        DateButton=findViewById(R.id.datebutton)
        val password = Email.text.toString()
        val email = Password.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {
        val client = Client(password = password, email = email, date = DateButton.text.toString(), role = spinner.selectedItem.toString())
            val clientDao = AppDatabase.getDatabase(applicationContext).clientDao()
            clientDao.insert(client)}
        val Intent = Intent(this ,LogIn::class.java)
        startActivity(Intent)
    }

    fun back(view: View)
    {
        val Intent = Intent(this ,LogIn::class.java)
        startActivity(Intent)
    }

}