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
        val employeeId = intent.getStringExtra("EMPLOYEE_ID") ?: "" // 직원ID받기

        // 출근 버튼
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

        // 전체보기 버튼 클릭 시 공지 목록으로
        val tvViewAllNotices = findViewById<TextView>(R.id.tv_view_all_notices)
        tvViewAllNotices.setOnClickListener {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        // 하단 네비 공지 버튼도 동일하게 이동
        val navNotice = findViewById<LinearLayout>(R.id.nav_notice)
        navNotice.setOnClickListener {
            // 공지 목록 액티비티 이름 바뀌면 여기도 수정
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        // 하단 네비 마이 -> 로그아웃/탈퇴 화면
        findViewById<LinearLayout>(R.id.nav_my).setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java)
            intent.putExtra("TITLE", "마이")
            startActivity(intent)
        }
    }

    // 화면 재진입할 때마다 최신 공지 2개 다시 로드
    override fun onResume() {
        super.onResume()
        loadRecentNotices()
    }

    private fun loadRecentNotices() {
        val container = findViewById<LinearLayout>(R.id.layout_recent_notices_container)
        container.removeAllViews()

        val dbHelper = UserDatabaseHelper(this)
        val recentNotices = dbHelper.getAllNotices().take(2) // 최신 2개만

        for (notice in recentNotices) {
            // item 레이아웃 이름 다르면 수정 (item_boss_notice 등)
            val itemView = layoutInflater.inflate(R.layout.activity_boss_item_notice, container, false)

            val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_title)
            val tvContent = itemView.findViewById<TextView>(R.id.tv_item_content)

            tvTitle.text = notice.title
            tvContent.text = notice.content

            // 클릭하면 상세로
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
}