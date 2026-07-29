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

        val userRole = intent.getStringExtra("USER_ROLE") ?: "STAFF"

        val ivBack = findViewById<ImageView>(R.id.btn_back)
        val btnDoLater = findViewById<Button>(R.id.btn_do_later)

        ivBack.setOnClickListener {
            moveToMain(userRole)
        }

        btnDoLater.setOnClickListener {
            moveToMain(userRole)
        }
    }

    private fun moveToMain(role: String) {
        val intent: Intent = if (role == "BOSS") {
            Intent(this, BossMainActivity::class.java)
        } else {
            Intent(this, StaffMainActivity::class.java)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}