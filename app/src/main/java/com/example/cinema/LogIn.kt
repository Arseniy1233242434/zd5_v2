package com.example.cinema

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.cinema.ui.theme.AppDatabase
import kotlinx.coroutines.launch

class LogIn : AppCompatActivity() {
    lateinit var Registr:TextView
    lateinit var  email:EditText
    lateinit var password: EditText
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)
        Registr=findViewById(R.id.registr)
        Registr.setOnClickListener {
            val Intent = Intent(this ,Registration::class.java)
            startActivity(Intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun login(view: View)
    {
        database = AppDatabase.getDatabase(this)
        email=findViewById(R.id.login)
        password=findViewById(R.id.password)
        lifecycleScope.launch {
            val user = database.clientDao().getUserByCredentials(email.text.toString(), password.text.toString())
            if (user != null)
            {
                if(user.role=="Клиент")
                {
                    val Intent1 = Intent(this@LogIn, KlientScreen::class.java)
                    startActivity(Intent1)
                }
                if(user.role=="Работник")
                {
                    val Intent1 = Intent(this@LogIn, WorkerScreen::class.java)
                    startActivity(Intent1)
                }
            }
        }
    }
}