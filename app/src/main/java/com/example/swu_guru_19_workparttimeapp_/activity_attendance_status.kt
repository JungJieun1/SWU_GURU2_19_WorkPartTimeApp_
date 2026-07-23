package com.example.swu_guru_19_workparttimeapp_

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class activity_attendance_status : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var rvAttendanceList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_attendance_status)
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
        rvAttendanceList = findViewById(R.id.rvAttendanceList)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> { /* 전체 목록 필터링 */ }
                    1 -> { /* 오늘 목록 필터링 */ }
                    2 -> { /* 이번 주 목록 필터링 */ }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}