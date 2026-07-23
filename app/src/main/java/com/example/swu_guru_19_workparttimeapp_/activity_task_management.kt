package com.example.swu_guru_19_workparttimeapp_

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import java.util.Calendar

class activity_task_management : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var rvTaskList: RecyclerView
    private lateinit var btnAddTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_management)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tabLayout = findViewById(R.id.tabLayout)
        rvTaskList = findViewById(R.id.rvTaskList)
        btnAddTask = findViewById(R.id.btnAddTask)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> { /* 전체 업무 목록 필터링 */ }
                    1 -> { /* 공동 업무 목록 필터링 */ }
                    2 -> { /* 개인 업무 목록 필터링 */ }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        btnAddTask.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val etTaskTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etDueDate = dialogView.findViewById<EditText>(R.id.etDueDate)

        etDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedMonth + 1}/${selectedDay} 까지"
                    etDueDate.setText(formattedDate)
                }, year, month, day)

            datePickerDialog.show()
        }

        AlertDialog.Builder(this)
            .setTitle("새 업무 추가")
            .setView(dialogView)
            .setPositiveButton("추가") { dialog, _ ->
                val title = etTaskTitle.text.toString().trim()
                val dueDate = etDueDate.text.toString().trim()

                if (title.isNotEmpty()) {
                    // DB 또는 리사이클러뷰 리스트에 새 업무(title, dueDate) 추가
                    Toast.makeText(this, "'$title' 업무가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "업무 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}