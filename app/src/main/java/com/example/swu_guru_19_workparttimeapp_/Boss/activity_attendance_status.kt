package com.example.swu_guru_19_workparttimeapp_.Boss

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swu_guru_19_workparttimeapp_.AttendanceData
import com.example.swu_guru_19_workparttimeapp_.R
import com.google.android.material.tabs.TabLayout
import com.example.swu_guru_19_workparttimeapp_.AttendanceDBHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class activity_attendance_status : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var tabLayout: TabLayout
    private lateinit var rvAttendanceList: RecyclerView
    private lateinit var attendanceAdapter: AttendanceAdapter
    private val attendanceList = mutableListOf<AttendanceData>()

    private lateinit var dbHelper: AttendanceDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_attendance_status)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = AttendanceDBHelper(this)

        initViews()
        setupRecyclerView()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadAttendanceData()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tabLayout = findViewById(R.id.tabLayout)
        rvAttendanceList = findViewById(R.id.rvAttendanceList)
    }

    private fun setupRecyclerView() {
        attendanceAdapter = AttendanceAdapter(attendanceList)
        rvAttendanceList.layoutManager = LinearLayoutManager(this)
        rvAttendanceList.adapter = attendanceAdapter
    }

    private fun loadAttendanceData(tabPosition: Int = 0) {
        val listFromDb = dbHelper.getAllAttendanceList()

        val filteredList = when (tabPosition) {
            1 -> {
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date())
                listFromDb.filter { it.date == today }
            }
            else -> listFromDb
        }

        attendanceList.clear()
        attendanceList.addAll(filteredList)
        attendanceAdapter.updateData(attendanceList)
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadAttendanceData(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}