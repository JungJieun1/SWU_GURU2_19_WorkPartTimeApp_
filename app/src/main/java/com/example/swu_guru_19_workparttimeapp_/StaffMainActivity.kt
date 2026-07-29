package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StaffMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_main)
        val employeeId = intent.getStringExtra("EMPLOYEE_ID") ?: ""

        val btnClockIn = findViewById<Button>(R.id.btn_clock_in)

        btnClockIn.setOnClickListener {

            val intent = Intent(
                this,
                AttendanceActivity::class.java
            )

            intent.putExtra(
                "EMPLOYEE_ID",
                employeeId
            )

            startActivity(intent)
        }

        val tvViewAllNotices = findViewById<TextView>(R.id.tv_view_all_notices)
        tvViewAllNotices.setOnClickListener {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        val navNotice = findViewById<LinearLayout>(R.id.nav_notice)
        navNotice.setOnClickListener {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.nav_my).setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java)
            intent.putExtra("TITLE", "마이")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadRecentNotices()
        loadRecentTasks()
    }

    private fun loadRecentNotices() {
        val container = findViewById<LinearLayout>(R.id.layout_recent_notices_container)
        container.removeAllViews()

        val dbHelper = UserDatabaseHelper(this)
        val recentNotices = dbHelper.getAllNotices().take(2)

        for (notice in recentNotices) {
            val itemView = layoutInflater.inflate(R.layout.activity_boss_item_notice, container, false)

            val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_title)
            val tvContent = itemView.findViewById<TextView>(R.id.tv_item_content)

            tvTitle.text = notice.title
            tvContent.text = notice.content

            itemView.setOnClickListener {
                val intent = Intent(this, NoticeDetailActivity::class.java)
                intent.putExtra("NOTICE_ID", notice.id)
                intent.putExtra("NOTICE_TITLE", notice.title)
                intent.putExtra("NOTICE_CONTENT", notice.content)
                startActivity(intent)
            }

            container.addView(itemView)
        }
    }

    private fun loadRecentTasks() {
        val container = findViewById<LinearLayout>(R.id.layout_recent_tasks_container)
        container.removeAllViews()

        val dbHelper = UserDatabaseHelper(this)
        val tasks = dbHelper.getAllTasks()

        for (task in tasks) {
            val itemView = layoutInflater.inflate(R.layout.item_task_management, container, false)

            val tvTaskTitle = itemView.findViewById<TextView>(R.id.tvTaskTitle)
            val tvTaskDate = itemView.findViewById<TextView>(R.id.tvTaskDate)
            val btnDeleteTask = itemView.findViewById<TextView>(R.id.btnDeleteTask)

            tvTaskTitle.text = task.title
            tvTaskDate.text = task.content
            btnDeleteTask.visibility = android.view.View.GONE

            container.addView(itemView)
        }
    }
}