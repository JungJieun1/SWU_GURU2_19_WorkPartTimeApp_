package com.example.swu_guru_19_workparttimeapp_.Boss

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swu_guru_19_workparttimeapp_.AttendanceData
import com.example.swu_guru_19_workparttimeapp_.R

class AttendanceAdapter(private var attendanceList: List<AttendanceData>) :
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmployeeName: TextView = itemView.findViewById(R.id.tvEmployeeName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attendance, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val attendance = attendanceList[position]

        holder.tvEmployeeName.text = attendance.name
        holder.tvStatus.text = attendance.status
        holder.tvTime.text = "${attendance.date} (${attendance.checkInTime} ~ ${attendance.checkOutTime})"
    }

    override fun getItemCount(): Int = attendanceList.size

    fun updateData(newList: List<AttendanceData>) {
        this.attendanceList = newList
        notifyDataSetChanged()
    }
}