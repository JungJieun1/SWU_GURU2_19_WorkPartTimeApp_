package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoticeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss_notice_detail)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val tvTitle = findViewById<TextView>(R.id.tv_detail_title)
        val tvContent = findViewById<TextView>(R.id.tv_detail_content)
        val btnEdit = findViewById<Button>(R.id.btn_edit_notice)
        val btnDelete = findViewById<Button>(R.id.btn_delete_notice)

        ivBack.setOnClickListener { finish() }

        val noticeId = intent.getIntExtra("NOTICE_ID", -1)
        val title = intent.getStringExtra("NOTICE_TITLE")
        val content = intent.getStringExtra("NOTICE_CONTENT")

        tvTitle.text = title
        tvContent.text = content

        btnDelete.setOnClickListener {
            if (noticeId != -1) {
                val dbHelper = UserDatabaseHelper(this)
                val isDeleted = dbHelper.deleteNotice(noticeId)
                if (isDeleted) {
                    Toast.makeText(this, "공지가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnEdit.setOnClickListener {
            val intent = Intent(this, NoticeWriteActivity::class.java)
            intent.putExtra("EDIT_NOTICE_ID", noticeId)
            intent.putExtra("EDIT_NOTICE_TITLE", title)
            intent.putExtra("EDIT_NOTICE_CONTENT", content)
            startActivity(intent)
            finish() // 상세 화면 닫기
        }
    }
}