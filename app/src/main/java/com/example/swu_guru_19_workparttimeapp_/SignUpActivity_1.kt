package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity_1 : AppCompatActivity() {

    // 변수 선언 (selectedRole을 전역 변수로 설정)
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText

    private lateinit var btnRoleBoss: Button
    private lateinit var btnRoleStaff: Button
    private lateinit var btnComplete: Button

    var selectedRole = "" // 역할 저장용 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_1)

        // ID 연결하기
        etName = findViewById(R.id.etName)   // 기존 R.id.etId 에서 수정됨
        etPhone = findViewById(R.id.etPhone) // 기존 R.id.etPassword 에서 수정됨
        etEmail = findViewById(R.id.etEmail) // 기존 R.id.etPasswordConfirm 에서 수정됨

        btnRoleBoss = findViewById(R.id.btnRoleBoss)
        btnRoleStaff = findViewById(R.id.btnRoleStaff)
        btnComplete = findViewById(R.id.btnComplete)


        // 역할 선택 버튼 클릭 이벤트
        // 사장님 버튼을 누를 때
        btnRoleBoss.setOnClickListener {
            btnRoleBoss.isSelected = true
            btnRoleStaff.isSelected = false
            selectedRole = "BOSS" // ⭐ 누르는 즉시 변수에 값을 저장합니다!
        }

        // 알바생 버튼을 누를 때
        btnRoleStaff.setOnClickListener {
            btnRoleStaff.isSelected = true
            btnRoleBoss.isSelected = false
            selectedRole = "STAFF" // ⭐ 누르는 즉시 변수에 값을 저장합니다!
        }

        // 완료 버튼 클릭 이벤트
        btnComplete.setOnClickListener {
            // 사용자가 입력한 값 가져오기 (공백 제거)
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()

            // 유효성 검사 1: 빈칸 확인
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "모든 정보를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 유효성 검사 2: 역할 선택 확인 (selectedRole 변수 확인)
            if (selectedRole.isEmpty()) {
                Toast.makeText(this, "사장님 또는 알바생 역할을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 모든 검사 통과 시 다음 화면으로 이동 및 데이터 넘기기
            val intent = Intent(this, SignUpActivity_2::class.java)

            intent.putExtra("USER_NAME", name)
            intent.putExtra("USER_PHONE", phone)
            intent.putExtra("USER_EMAIL", email)
            intent.putExtra("USER_ROLE", selectedRole) // 정상적으로 저장된 BOSS 또는 STAFF 값이 넘어감

            startActivity(intent)
        }
    }
}