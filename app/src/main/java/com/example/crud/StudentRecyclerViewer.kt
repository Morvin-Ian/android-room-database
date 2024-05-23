package com.example.crud


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.databinding.StudentItemBinding
import com.example.crud.db.Student

class StudentRecyclerViewer(
    private val clickListener: (Student)->Unit
): RecyclerView.Adapter<ViewHolder>() {
    private val students = ArrayList<Student>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return students.size
    }
    fun setList(data:List<Student>){
        students.clear()
        students.addAll(data)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(students[position], clickListener)
    }
}

class ViewHolder(private val binding: StudentItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(student: Student, clickListener: (Student)->Unit){
        binding.apply{
            name.text = student.name
            email.text = student.email
            root.setOnClickListener{
                clickListener(student)
            }
        }
    }
}