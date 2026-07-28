package com.example.swu_guru_19_workparttimeapp_

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MoreActivity : AppCompatActivity() {

    private val dbHelper by lazy { UserDatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        val title = intent.getStringExtra("TITLE") ?: "더보기"
        findViewById<TextView>(R.id.tv_more_title).text = title

        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }

        val logoutClick = { showLogoutConfirm() }
        findViewById<LinearLayout>(R.id.row_logout).setOnClickListener { logoutClick() }
        findViewById<android.widget.Button>(R.id.btn_logout_bottom).setOnClickListener { logoutClick() }

        findViewById<LinearLayout>(R.id.row_withdraw).setOnClickListener { showWithdrawDialog() }
    }

    private fun showLogoutConfirm() {
        AlertDialog.Builder(this)
            .setTitle("로그아웃")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("확인") { _, _ -> logout() }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun logout() {
        getSharedPreferences(UserDatabaseHelper.PREFS_NAME, MODE_PRIVATE).edit()
            .remove(UserDatabaseHelper.PREF_USER_ID)
            .apply()
        goToLogin()
    }

    private fun showWithdrawDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_withdraw_password, null)
        val etPassword = dialogView.findViewById<EditText>(R.id.et_withdraw_password)

        AlertDialog.Builder(this)
            .setTitle("회원탈퇴")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ -> tryWithdraw(etPassword.text.toString()) }
            .setNegativeButton("취소", null)
            .show()
    }

    private fun tryWithdraw(password: String) {
        val userId = getSharedPreferences(UserDatabaseHelper.PREFS_NAME, MODE_PRIVATE)
            .getString(UserDatabaseHelper.PREF_USER_ID, null)

        if (userId == null) {
            Toast.makeText(this, "로그인 정보를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!dbHelper.checkPassword(userId, password)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        dbHelper.deleteUser(userId)
        getSharedPreferences(UserDatabaseHelper.PREFS_NAME, MODE_PRIVATE).edit()
            .remove(UserDatabaseHelper.PREF_USER_ID)
            .apply()

        Toast.makeText(this, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        goToLogin()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
