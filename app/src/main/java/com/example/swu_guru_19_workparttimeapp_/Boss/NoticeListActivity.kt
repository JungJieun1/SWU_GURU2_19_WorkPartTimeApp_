package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoticeListActivity : AppCompatActivity() {

    private lateinit var noticeListContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_notice_list)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { finish() }

        noticeListContainer = findViewById(R.id.layout_notice_list_container)

        val btnWriteNotice = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btn_write_notice)
        btnWriteNotice.setOnClickListener {
            val intent = Intent(this, NoticeWriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNoticesFromDB()
    }

    private fun loadNoticesFromDB() {
        noticeListContainer.removeAllViews()

        val dbHelper = UserDatabaseHelper(this)
        val noticeList = dbHelper.getAllNotices()

        for (notice in noticeList) {
            // item_notice.xml inflate
            val itemView = layoutInflater.inflate(R.layout.activity_boss_item_notice, noticeListContainer, false)

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

            noticeListContainer.addView(itemView)
        }
    }
}