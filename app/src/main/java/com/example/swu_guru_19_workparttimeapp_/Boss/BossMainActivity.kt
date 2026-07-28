package com.example.swu_guru_19_workparttimeapp_.Boss

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.swu_guru_19_workparttimeapp_.MoreActivity
import com.example.swu_guru_19_workparttimeapp_.NoticeDetailActivity
import com.example.swu_guru_19_workparttimeapp_.NoticeListActivity
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper

class BossMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_main)

        findViewById<LinearLayout>(R.id.layout_select_store).setOnClickListener {
            val intent = Intent(this, activity_workplace_list::class.java)
            startActivity(intent)
        }

        val moveToEmployeeList = {
            val intent = Intent(this, activity_employee_list::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.cv_today_workers).setOnClickListener { moveToEmployeeList() }
        findViewById<LinearLayout>(R.id.nav_employee).setOnClickListener { moveToEmployeeList() }

        val moveToTaskManager = {
            val intent = Intent(this, activity_task_management::class.java)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.cv_today_tasks).setOnClickListener { moveToTaskManager() }
        findViewById<LinearLayout>(R.id.nav_task).setOnClickListener { moveToTaskManager() }

        findViewById<LinearLayout>(R.id.nav_attendance).setOnClickListener {
            val intent = Intent(this, activity_attendance_status::class.java)
            startActivity(intent)
        }

        val cvRecentNotices = findViewById<CardView>(R.id.cv_recent_notices)
        val tvNoticeMore = findViewById<TextView>(R.id.tv_notice_more)
        val moveToNoticeList = {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }
        cvRecentNotices.setOnClickListener { moveToNoticeList() }
        tvNoticeMore.setOnClickListener { moveToNoticeList() }

        findViewById<LinearLayout>(R.id.nav_more).setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java)
            intent.putExtra("TITLE", "더보기")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadRecentNotices()
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
}