package com.example.swu_guru_19_workparttimeapp_.Boss

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swu_guru_19_workparttimeapp_.Employee
import com.example.swu_guru_19_workparttimeapp_.R

class EmployeeAdapter(
    private val employeeList: MutableList<Employee>
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmployeeName: TextView = itemView.findViewById(R.id.tvEmployeeName)
        val tvWorkType: TextView = itemView.findViewById(R.id.tvWorkType)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee, parent, false)

        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]

        holder.tvEmployeeName.text = employee.name
        holder.tvWorkType.text = employee.role

        holder.btnEdit.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, activity_employee_add::class.java).apply {
                putExtra("EMPLOYEE_ID", employee.id)
                putExtra("EMPLOYEE_NAME", employee.name)
                putExtra("EMPLOYEE_PHONE", employee.phone)
                putExtra("EMPLOYEE_ROLE", employee.role)
            }

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = employeeList.size
}