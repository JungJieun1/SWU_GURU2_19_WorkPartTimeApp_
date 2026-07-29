package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.Boss.BossMainActivity
import com.example.swu_guru_19_workparttimeapp_.signup.SignUpActivity_1

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etId = findViewById<EditText>(R.id.etLoginId)
        val etPw = findViewById<EditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvGoToSignup = findViewById<TextView>(R.id.textView7)

        tvGoToSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity_1::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val id = etId.text.toString().trim()
            val pw = etPw.text.toString().trim()

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = UserDatabaseHelper(this)

            val userRole = dbHelper.loginAndGetRole(id, pw)

            if (userRole != null) {
                Toast.makeText(this, "환영합니다!", Toast.LENGTH_SHORT).show()

                getSharedPreferences(UserDatabaseHelper.PREFS_NAME, MODE_PRIVATE).edit()
                    .putString(UserDatabaseHelper.PREF_USER_ID, id)
                    .apply()

                val intent = if (userRole == "BOSS") {
                    Intent(this, BossMainActivity::class.java)
                } else {
                    Intent(this, StaffMainActivity::class.java)
                }
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}