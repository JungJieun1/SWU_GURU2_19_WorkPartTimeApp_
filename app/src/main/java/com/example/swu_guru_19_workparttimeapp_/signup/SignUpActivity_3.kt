package com.example.swu_guru_19_workparttimeapp_.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.R
import com.example.swu_guru_19_workparttimeapp_.UserDatabaseHelper

class SignUpActivity_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_3)

        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish()
        }

        val cbAll = findViewById<CheckBox>(R.id.cbAll)
        val cbTerm1 = findViewById<CheckBox>(R.id.cbTerms1)
        val cbTerm2 = findViewById<CheckBox>(R.id.cbTerms2)
        val cbTerm3 = findViewById<CheckBox>(R.id.cbTerms3)
        val btnComplete = findViewById<Button>(R.id.btn_do_later)

        cbAll.setOnClickListener {
            val isChecked = cbAll.isChecked
            cbTerm1.isChecked = isChecked
            cbTerm2.isChecked = isChecked
            cbTerm3.isChecked = isChecked
        }

        val updateAllCheckBox = {
            cbAll.isChecked = cbTerm1.isChecked && cbTerm2.isChecked && cbTerm3.isChecked
        }

        cbTerm1.setOnClickListener { updateAllCheckBox() }
        cbTerm2.setOnClickListener { updateAllCheckBox() }
        cbTerm3.setOnClickListener { updateAllCheckBox() }

        btnComplete.setOnClickListener {
            if (!cbTerm1.isChecked || !cbTerm2.isChecked || !cbTerm3.isChecked) {
                Toast.makeText(this, "필수 약관에 모두 동의해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = intent.getStringExtra("USER_NAME") ?: ""
            val phone = intent.getStringExtra("USER_PHONE") ?: ""
            val email = intent.getStringExtra("USER_EMAIL") ?: ""
            val role = intent.getStringExtra("USER_ROLE") ?: ""
            val userId = intent.getStringExtra("USER_ID") ?: ""
            val userPw = intent.getStringExtra("USER_PASSWORD") ?: ""

            val dbHelper = UserDatabaseHelper(this)
            val isSuccess = dbHelper.insertUser(name, phone, email, role, userId, userPw)

            if (isSuccess) {
                Toast.makeText(this, "회원가입 정보가 안전하게 저장되었습니다!", Toast.LENGTH_SHORT).show()

                val completeIntent = Intent(this, SignUpCompleteActivity::class.java)

                completeIntent.putExtra("USER_ROLE", role)

                completeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(completeIntent)
            } else {
                Toast.makeText(this, "저장 중 오류가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}