package com.example.swu_guru_19_workparttimeapp_

import android.os.Bundle
import android.widget.Toast // ⬅️ Toast를 쓰기 위해 필요합니다 (자동 추가 안 되면 Alt+Enter)
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_2)

        // 앞 화면에서 보낸 Intent 데이터 꺼내기
        val receivedName = intent.getStringExtra("USER_NAME")
        val receivedPhone = intent.getStringExtra("USER_PHONE")
        val receivedEmail = intent.getStringExtra("USER_EMAIL")
        val receivedRole = intent.getStringExtra("USER_ROLE")

        // 데이터가 무사히 도착했는지 팝업(Toast)으로 테스트
        Toast.makeText(this, "배달 완료! 이름: $receivedName, 역할: $receivedRole", Toast.LENGTH_LONG)
            .show()

        // 뷰(입력창, 버튼)들 연결하기
        val etId = findViewById<android.widget.EditText>(R.id.etName)
        val etPassword = findViewById<android.widget.EditText>(R.id.etPhone)
        val etPasswordConfirm = findViewById<android.widget.EditText>(R.id.etEmail)
        val btnFinalSignUp = findViewById<android.widget.Button>(R.id.btnComplete)

        // 4. 최종 가입 완료 버튼 클릭 이벤트
        btnFinalSignUp.setOnClickListener {
            // 입력된 글자 가져와서 양옆 공백 자르기(trim)
            val id = etId.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passwordConfirm = etPasswordConfirm.text.toString().trim()

            // 유효성 검사 1: 아이디 빈칸 확인
            if (id.isEmpty()) {
                Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 여기서 멈추고 아래 코드는 실행 안 함
            }

            // 유효성 검사 2: 비밀번호 빈칸 확인
            if (password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "비밀번호를 모두 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 유효성 검사 3: 비밀번호 일치 확인
            if (password != passwordConfirm) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1번 화면에서 받은 데이터 다시 꺼내기
            val receivedName = intent.getStringExtra("USER_NAME")
            val receivedPhone = intent.getStringExtra("USER_PHONE")
            val receivedEmail = intent.getStringExtra("USER_EMAIL")
            val receivedRole = intent.getStringExtra("USER_ROLE")

            // 목적지를 SignUpActivity_3으로 변경
            val nextIntent = android.content.Intent(this, SignUpActivity_3::class.java)

            // 1번에서 온 데이터 + 방금 2번에서 적은 데이터를 intent에 담기
            nextIntent.putExtra("USER_NAME", receivedName)
            nextIntent.putExtra("USER_PHONE", receivedPhone)
            nextIntent.putExtra("USER_EMAIL", receivedEmail)
            nextIntent.putExtra("USER_ROLE", receivedRole)
            nextIntent.putExtra("USER_ID", id) // 새로 추가된 아이디
            nextIntent.putExtra("USER_PASSWORD", password) // 새로 추가된 비밀번호

            //3번 화면으로 이동
            startActivity(nextIntent)

        }

    }
}
