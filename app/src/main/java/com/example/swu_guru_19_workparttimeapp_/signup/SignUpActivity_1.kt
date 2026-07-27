package com.example.swu_guru_19_workparttimeapp_.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swu_guru_19_workparttimeapp_.R

class SignUpActivity_1 : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText

    private lateinit var btnRoleBoss: LinearLayout
    private lateinit var btnRoleStaff: LinearLayout
    private lateinit var ivRoleBoss: ImageView
    private lateinit var ivRoleStaff: ImageView
    private lateinit var btnComplete: Button

    var selectedRole = "" // 선택한 역할

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_1)

        // 뒤로가기 버튼
        val btnBack = findViewById<ImageView>(R.id.btn_back)

        btnBack.setOnClickListener {
            finish()
        }

        // 뷰 연결
        etName = findViewById(R.id.etName)   // 기존 R.id.etId 에서 수정됨
        etPhone = findViewById(R.id.etPhone) // 기존 R.id.etPassword 에서 수정됨
        etEmail = findViewById(R.id.etEmail) // 기존 R.id.etPasswordConfirm 에서 수정됨

        btnRoleBoss = findViewById(R.id.btnRoleBoss)
        btnRoleStaff = findViewById(R.id.btnRoleStaff)
        ivRoleBoss = findViewById(R.id.ivRoleBoss)
        ivRoleStaff = findViewById(R.id.ivRoleStaff)
        btnComplete = findViewById(R.id.btn_do_later)


        // 사장님 선택
        btnRoleBoss.setOnClickListener {
            btnRoleBoss.isSelected = true
            btnRoleStaff.isSelected = false
            selectedRole = "BOSS" // 누르면 바로 저장
            ivRoleBoss.setImageResource(R.drawable.ic_boss2)
            ivRoleStaff.setImageResource(R.drawable.ic_user1)
        }

        // 알바생 선택
        btnRoleStaff.setOnClickListener {
            btnRoleStaff.isSelected = true
            btnRoleBoss.isSelected = false
            selectedRole = "STAFF"
            ivRoleBoss.setImageResource(R.drawable.ic_boss1)
            ivRoleStaff.setImageResource(R.drawable.ic_user2)
        }

        btnComplete.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()

            // 빈칸 체크
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 역할 안 골랐으면 막기
            if (selectedRole.isEmpty()) {
                Toast.makeText(this, "사장님 또는 알바생 역할을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 통과했으면 다음 화면으로
            val intent = Intent(this, SignUpActivity_2::class.java)

            intent.putExtra("USER_NAME", name)
            intent.putExtra("USER_PHONE", phone)
            intent.putExtra("USER_EMAIL", email)
            intent.putExtra("USER_ROLE", selectedRole) // BOSS 또는 STAFF

            startActivity(intent)
        }
    }
}