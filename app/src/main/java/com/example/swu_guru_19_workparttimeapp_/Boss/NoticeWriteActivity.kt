package com.example.swu_guru_19_workparttimeapp_

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NoticeWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 파일명 맞춰서 연결
        setContentView(R.layout.activity_boss_notice_write)

        val ivBack = findViewById<ImageView>(R.id.iv_back)
        val etTitle = findViewById<EditText>(R.id.et_notice_title)
        val etContent = findViewById<EditText>(R.id.et_notice_content)
        val btnRegister = findViewById<Button>(R.id.btn_register_notice)

        // 뒤로가기
        ivBack.setOnClickListener { finish() }

        // 수정 모드인지 체크 (상세에서 넘어온 ID 있으면 수정)
        val editNoticeId = intent.getIntExtra("EDIT_NOTICE_ID", -1)

        if (editNoticeId != -1) {
            // 수정 모드면 기존 내용 채우고 버튼 텍스트도 변경
            etTitle.setText(intent.getStringExtra("EDIT_NOTICE_TITLE"))
            etContent.setText(intent.getStringExtra("EDIT_NOTICE_CONTENT"))
            btnRegister.text = "공지 수정하기"
        }

        // 등록/수정 버튼
        btnRegister.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val content = etContent.text.toString().trim()

            // 빈칸 검사
            if (title.isEmpty()) {
                Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (content.isEmpty()) {
                Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 오늘 날짜 구하기
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val currentDate = dateFormat.format(java.util.Date())

            val dbHelper = UserDatabaseHelper(this)

            // 수정이냐 새 글이냐에 따라 분기
            if (editNoticeId != -1) {
                // [수정 모드]
                val isUpdated = dbHelper.updateNotice(editNoticeId, title, content, currentDate)
                if (isUpdated) {
                    Toast.makeText(this, "공지가 수정되었습니다!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // [새 글 작성 모드]
                val isInserted = dbHelper.insertNotice(title, content, currentDate)
                if (isInserted) {
                    Toast.makeText(this, "공지사항이 성공적으로 등록되었습니다!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "공지 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}