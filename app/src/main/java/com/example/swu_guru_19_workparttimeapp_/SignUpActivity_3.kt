package com.example.swu_guru_19_workparttimeapp_ // ⬅️ 우리의 평화를 지켜줄 맨 윗줄!

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_3) // 3번 화면 XML 연결

        // 체크박스와 버튼 연결하기
        val cbAll = findViewById<CheckBox>(R.id.cbAll)       // 전체동의
        val cbTerm1 = findViewById<CheckBox>(R.id.cbTerms1)   // 필수 1
        val cbTerm2 = findViewById<CheckBox>(R.id.cbTerms2)   // 필수 2
        val cbTerm3 = findViewById<CheckBox>(R.id.cbTerms3)   // 필수 3
        val btnComplete = findViewById<Button>(R.id.btnComplete) // 완료 버튼

        // '전체동의' 체크박스 클릭 이벤트
        cbAll.setOnClickListener {
            val isChecked = cbAll.isChecked
            // 전체동의가 눌리면 나머지 3개도 똑같이 따라가게 만듦
            cbTerm1.isChecked = isChecked
            cbTerm2.isChecked = isChecked
            cbTerm3.isChecked = isChecked
        }

        // 개별 약관 체크박스 클릭 이벤트
        // 3개 중 하나라도 해제되면 전체동의 해제, 3개 다 체크되면 전체동의도 체크
        val updateAllCheckBox = {
            cbAll.isChecked = cbTerm1.isChecked && cbTerm2.isChecked && cbTerm3.isChecked
        }

        cbTerm1.setOnClickListener { updateAllCheckBox() }
        cbTerm2.setOnClickListener { updateAllCheckBox() }
        cbTerm3.setOnClickListener { updateAllCheckBox() }

        // 최종 '완료' 버튼 클릭 이벤트
        btnComplete.setOnClickListener {
            // 항목이 하나라도 체크 안 되어 있다면 돌아가기
            if (!cbTerm1.isChecked || !cbTerm2.isChecked || !cbTerm3.isChecked) {
                Toast.makeText(this, "필수 약관에 모두 동의해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 앞(1번, 2번 화면)에서 넘어온 데이터 꺼내기
            val name = intent.getStringExtra("USER_NAME")
            val id = intent.getStringExtra("USER_ID")
            // ... (DB 저장 로직) ...

            //토스트 메세지 출력
            Toast.makeText(this, "🎉 회원가입이 완벽하게 끝났습니다!", Toast.LENGTH_LONG).show()

            // 로그인 화면으로 이동
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)

            //1, 2, 3번 화면 닫기
            finishAffinity()
        }
    }
}