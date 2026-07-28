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

        // 뷰 연결
        val cvTodayWorkers = findViewById<CardView>(R.id.cv_today_workers)
        val cvTodayTasks = findViewById<CardView>(R.id.cv_today_tasks)
        val cvRecentNotices = findViewById<CardView>(R.id.cv_recent_notices)
        val tvNoticeMore = findViewById<TextView>(R.id.tv_notice_more)

        // 오늘 근무자 -> 근무자 목록으로
        cvTodayWorkers.setOnClickListener {
            val intent = Intent(this, WorkerListActivity::class.java)
            startActivity(intent)
        }

        // 오늘 업무 -> 업무 목록으로
        cvTodayTasks.setOnClickListener {
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        }

        // 최근 공지 상자/전체보기 둘 다 같은 동작이라 함수로 묶음
        val moveToNoticeList = {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        cvRecentNotices.setOnClickListener { moveToNoticeList() }
        tvNoticeMore.setOnClickListener { moveToNoticeList() }

        // 하단 네비 더보기 -> 로그아웃/탈퇴 화면
        findViewById<LinearLayout>(R.id.nav_more).setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java)
            intent.putExtra("TITLE", "더보기")
            startActivity(intent)
        }
    }
    // 화면 다시 보일 때마다 새로고침
    override fun onResume() {
        super.onResume()
        loadRecentNotices()
    }

    // 최신 공지 2개만 메인에 띄움
    private fun loadRecentNotices() {
        val container = findViewById<LinearLayout>(R.id.layout_recent_notices_container)
        container.removeAllViews() // 남은 거 있으면 지우기

        val dbHelper = UserDatabaseHelper(this)

        // 최신순으로 받아서 2개만 자름
        val recentNotices = dbHelper.getAllNotices().take(2)

        for (notice in recentNotices) {
            val itemView =
                layoutInflater.inflate(R.layout.activity_boss_item_notice, container, false)

            val tvTitle = itemView.findViewById<TextView>(R.id.tv_item_title)
            val tvContent = itemView.findViewById<TextView>(R.id.tv_item_content)

            tvTitle.text = notice.title
            tvContent.text = notice.content

            // 클릭하면 상세로 이동
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