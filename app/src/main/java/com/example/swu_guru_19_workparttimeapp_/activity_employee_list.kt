package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class activity_employee_list : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var rvEmployeeList: RecyclerView
    private lateinit var btnAddEmployee: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_employee_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()

        setupRecyclerView()

        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        rvEmployeeList = findViewById(R.id.rvEmployeeList)
        btnAddEmployee = findViewById(R.id.btnAddEmployee)
    }

    private fun setupRecyclerView() {
        val dummyList = listOf(
            // db 연결 전 임시 데이터
            Employee("김민수", "오픈 (09:00 ~ 15:00)"),
            Employee("이영희", "미들 (15:00 ~ 20:00)"),
            Employee("박철수", "마감 (20:00 ~ 02:00)")
        )

        val adapter = EmployeeAdapter(dummyList)
        rvEmployeeList.layoutManager = LinearLayoutManager(this)
        rvEmployeeList.adapter = adapter
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnAddEmployee.setOnClickListener {
            val intent = Intent(this, activity_employee_add::class.java)
            startActivity(intent)
        }
    }
}