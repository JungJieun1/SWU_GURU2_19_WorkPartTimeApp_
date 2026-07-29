package com.example.swu_guru_19_workparttimeapp_

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : AppCompatActivity() {

    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private lateinit var txtCheckIn: TextView
    private lateinit var txtCheckOut: TextView

    private lateinit var btnCheckIn: Button
    private lateinit var btnCheckOut: Button

    private lateinit var db: AttendanceDBHelper

    // 로그인한 직원 ID (임시)
    private var employeeId = "staff"

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        db = AttendanceDBHelper(this)

        txtDate = findViewById(R.id.txtDate)
        txtTime = findViewById(R.id.txtTime)
        txtCheckIn = findViewById(R.id.txtCheckIn)
        txtCheckOut = findViewById(R.id.txtCheckOut)

        btnCheckIn = findViewById(R.id.btnCheckIn)
        btnCheckOut = findViewById(R.id.btnCheckOut)

        employeeId = intent.getStringExtra("EMPLOYEE_ID") ?: "staff"

        showToday()
        startClock()
        loadTodayAttendance()

        btnCheckIn.setOnClickListener {

            val today = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.KOREA
            ).format(Date())

            if (db.hasAttendance(employeeId, today)) {
                Toast.makeText(this, "이미 출근했습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val now = SimpleDateFormat(
                "HH:mm:ss",
                Locale.KOREA
            ).format(Date())

            if (db.insertAttendance(employeeId, today, now)) {
                Toast.makeText(this, "출근 완료", Toast.LENGTH_SHORT).show()
                loadTodayAttendance()
            }
        }

        btnCheckOut.setOnClickListener {

            val today = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.KOREA
            ).format(Date())

            val now = SimpleDateFormat(
                "HH:mm:ss",
                Locale.KOREA
            ).format(Date())

            if (db.updateCheckOut(employeeId, today, now)) {
                Toast.makeText(this, "퇴근 완료", Toast.LENGTH_SHORT).show()
                loadTodayAttendance()
            }
        }
    }

    private fun showToday() {

        txtDate.text =
            SimpleDateFormat(
                "yyyy년 MM월 dd일",
                Locale.KOREA
            ).format(Date())
    }

    private fun startClock() {

        handler.post(object : Runnable {
            override fun run() {

                txtTime.text =
                    SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.KOREA
                    ).format(Date())

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun loadTodayAttendance() {

        val today =
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.KOREA
            ).format(Date())

        val attendance =
            db.getAttendance(employeeId, today)

        if (attendance != null) {

            txtCheckIn.text = attendance.first

            if (attendance.second.isNotEmpty()) {

                txtCheckOut.text = attendance.second

                btnCheckIn.isEnabled = false
                btnCheckOut.isEnabled = false

            } else {

                txtCheckOut.text = "-"

                btnCheckIn.isEnabled = false
                btnCheckOut.isEnabled = true
            }

        } else {

            txtCheckIn.text = "-"
            txtCheckOut.text = "-"

            btnCheckIn.isEnabled = true
            btnCheckOut.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}