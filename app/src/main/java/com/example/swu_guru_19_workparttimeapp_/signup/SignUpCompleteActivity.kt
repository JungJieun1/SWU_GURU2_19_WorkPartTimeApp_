package com.example.swu_guru_19_workparttimeapp_.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.Boss.BossMainActivity
import com.example.swu_guru_19_workparttimeapp_.ProfileActivity
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.StaffMainActivity
import kotlin.jvm.java

class SignUpCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_complete)

        // 3번 화면에서 넘어온 역할, 없으면 STAFF
        val userRole = intent.getStringExtra("USER_ROLE") ?: "STAFF"

        val ivBack = findViewById<ImageView>(R.id.btn_back)
        val btnSetProfile = findViewById<Button>(R.id.btn_set_profile)
        val btnDoLater = findViewById<Button>(R.id.btn_do_later)

        // 여기선 뒤로가기도 그냥 메인으로 보냄
        ivBack.setOnClickListener {
            moveToMain(userRole)
        }

        btnSetProfile.setOnClickListener {
            // 지금은 일단 ProfileActivity로 고정
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnDoLater.setOnClickListener {
            moveToMain(userRole)
        }
    }

    // 역할별로 메인 화면 갈라주는 함수
    private fun moveToMain(role: String) {
        val intent: Intent = if (role == "BOSS") {
            Intent(this, BossMainActivity::class.java)
        } else {
            Intent(this, StaffMainActivity::class.java)
        }

        // 스택 비우고 새로 시작
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}