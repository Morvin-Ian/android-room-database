package com.example.crud

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.db.Student
import com.example.crud.db.StudentDatabase
import com.example.crud.viewmodel.StudentViewModel
import com.example.crud.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var studentRecyclerView:RecyclerView
    private lateinit var adapter:StudentRecyclerViewer

    private var isItemClicked:Boolean = false
    private lateinit var selectedStudent:Student

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
        studentRecyclerView = binding.recyclerView


        binding.apply {
            btnSave.setOnClickListener{
                if(isItemClicked){
                   updateStudent()
                }else{
                    saveDetails()
                }
                clearInput()
            }

            btnClear.setOnClickListener{
                if(isItemClicked) {
                    deleteStudent()
                    clearInput()
                }else{
                    clearInput()
                }
            }
            initRecyclerView()
        }
    }

    private fun initRecyclerView(){
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentRecyclerViewer { selectedItem: Student -> trackClickedItem(selectedItem) }
        studentRecyclerView.adapter = adapter
        displayStudents()
    }

    private fun displayStudents(){
        viewModel.students.observe(this, {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private  fun updateStudent(){
        binding.apply {
            viewModel.updateStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etMail.text.toString()
                )
            )
        }
    }

    private fun deleteStudent(){
        binding.apply {
            viewModel.deleteStudent(
                Student(
                    selectedStudent.id,
                    etName.text.toString(),
                    etMail.text.toString()
                )
            )

            btnSave.text = "Save"
            btnClear.text = "Clear"
            isItemClicked = false
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

    private fun trackClickedItem(student: Student){
        selectedStudent = student
        binding.apply {
            btnSave.text = "Update"
            btnClear.text = "Delete"
            isItemClicked = true

            etName.setText(selectedStudent.name)
            etMail.setText(selectedStudent.email)
        }

    }

}