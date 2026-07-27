package com.example.swu_guru_19_workparttimeapp_

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 출퇴근 현황 데이터 모델 (이름, 출퇴근 상태, 시간)
data class Attendance(
    val employeeName: String,
    val status: String,
    val time: String
)

class AttendanceAdapter(private val attendanceList: List<Attendance>) :
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
        holder.tvEmployeeName.text = attendance.employeeName
        holder.tvStatus.text = attendance.status
        holder.tvTime.text = attendance.time
    }

    override fun getItemCount(): Int = attendanceList.size
}