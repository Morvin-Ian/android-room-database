package com.example.crud

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.db.Student
import com.example.crud.db.StudentDatabase
import com.example.crud.viewmodel.StudentViewModel
import com.example.crud.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dao = StudentDatabase.getInstance(application).studentDao()
        val factory = StudentViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(StudentViewModel::class.java)

        binding.btnSave.setOnClickListener{
            saveDetails()
            clearInput()
        }

        binding.btnClear.setOnClickListener{
            clearInput()
        }
    }

    private fun saveDetails(){
        val name = binding.etName.text.toString()
        val email = binding.etMail.text.toString()

        viewModel.insertStudent(
            Student(0, name, email)
        )
    }

    private fun clearInput(){
        binding.apply {
            etName.setText("")
            etMail.setText("")
        }
    }
}