package com.example.swu_guru_19_workparttimeapp_.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper

class SignUpActivity_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_2)

        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val receivedName = intent.getStringExtra("USER_NAME")
        val receivedPhone = intent.getStringExtra("USER_PHONE")
        val receivedEmail = intent.getStringExtra("USER_EMAIL")
        val receivedRole = intent.getStringExtra("USER_ROLE")

        val etId = findViewById<EditText>(R.id.etName)
        val etPassword = findViewById<EditText>(R.id.etPhone)
        val etPasswordConfirm = findViewById<EditText>(R.id.etEmail)
        val btnFinalSignUp = findViewById<Button>(R.id.btn_do_later)

        val btnCheckId = findViewById<Button>(R.id.btnCheckId) // XML 쪽 중복확인 버튼 ID랑 맞춰야 함

        var isIdChecked = false

        btnCheckId.setOnClickListener {
            val id = etId.text.toString().trim()

            if (id.isEmpty()) {
                Toast.makeText(this, "아이디를 먼저 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = UserDatabaseHelper(this)
            val isExist = dbHelper.checkIdExist(id)

            if (isExist) {
                Toast.makeText(this, "이미 사용 중인 아이디입니다. 다른 아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                isIdChecked = false
            } else {
                Toast.makeText(this, "사용 가능한 아이디입니다!", Toast.LENGTH_SHORT).show()
                isIdChecked = true
            }
        }

        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isIdChecked = false
            }
        })

        btnFinalSignUp.setOnClickListener {
            val id = etId.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passwordConfirm = etPasswordConfirm.text.toString().trim()

            if (id.isEmpty()) {
                Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isIdChecked) {
                Toast.makeText(this, "아이디 중복 확인을 완료해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "비밀번호를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nextIntent = Intent(this, SignUpActivity_3::class.java)

            nextIntent.putExtra("USER_NAME", receivedName)
            nextIntent.putExtra("USER_PHONE", receivedPhone)
            nextIntent.putExtra("USER_EMAIL", receivedEmail)
            nextIntent.putExtra("USER_ROLE", receivedRole)
            nextIntent.putExtra("USER_ID", id)
            nextIntent.putExtra("USER_PASSWORD", password)

            startActivity(nextIntent)
        }
    }
}