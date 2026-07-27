package com.example.swu_guru_19_workparttimeapp_

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Notice(val id: Int, val title: String, val content: String, val date: String)
class UserDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 2

        // 테이블 및 컬럼 이름 정의
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ROLE = "role" // BOSS 또는 STAFF
        const val COLUMN_USER_ID = "user_id" // SignUpActivity_2에서 전달받는 아이디
        const val COLUMN_PASSWORD = "password" // SignUpActivity_2에서 전달받는 비밀번호

        const val TABLE_NOTICES = "notices"
        const val COLUMN_NOTICE_ID = "noticeId"
        const val COLUMN_NOTICE_TITLE = "title"
        const val COLUMN_NOTICE_CONTENT = "content"
        const val COLUMN_NOTICE_DATE = "date"

        // 로그인 세션 저장용
        const val PREFS_NAME = "app_prefs"
        const val PREF_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // users 테이블 생성 쿼리
        val createTableQuery = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_PHONE TEXT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_ROLE TEXT, "
                + "$COLUMN_USER_ID TEXT, "
                + "$COLUMN_PASSWORD TEXT)")
        db.execSQL(createTableQuery)

        //공지사항 테이블 생성
        val createNoticeTable = ("CREATE TABLE $TABLE_NOTICES ("
                + "$COLUMN_NOTICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " // ID는 자동으로 1씩 증가
                + "$COLUMN_NOTICE_TITLE TEXT, "
                + "$COLUMN_NOTICE_CONTENT TEXT, "
                + "$COLUMN_NOTICE_DATE TEXT"
                + ")")
        db?.execSQL(createNoticeTable)

        // 앱 처음 설치할 때 기본 공지 2개 넣어두기
        db?.execSQL("INSERT INTO $TABLE_NOTICES ($COLUMN_NOTICE_TITLE, $COLUMN_NOTICE_CONTENT, $COLUMN_NOTICE_DATE) VALUES ('7/20 휴무 신청 안내', '다음 달 휴무 신청은 이번 주 금요일까지 사장님께 직접 문자 메시지로 전달 바랍니다.\n\n기한을 꼭 지켜주시기 바라며, 주말 근무자의 경우 금요일 저녁 마감 전까지 알려주시면 감사하겠습니다.', '2026-07-20')")
        db?.execSQL("INSERT INTO $TABLE_NOTICES ($COLUMN_NOTICE_TITLE, $COLUMN_NOTICE_CONTENT, $COLUMN_NOTICE_DATE) VALUES ('7/18 매장 청소 담당 안내', '미들 타임 청소 구역이 일부 변경되었습니다.\n\n기존 창가 쪽 테이블 구역에서 카운터 앞 대기석까지 포함하여 정리해 주시면 됩니다. 변경된 매뉴얼은 포스기 옆에 붙여두었으니 출근 시 꼭 확인해 주세요.', '2026-07-18')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTICES") // 추가
        onCreate(db)
    }

    // 회원 정보 저장
    fun insertUser(
        name: String,
        phone: String,
        email: String,
        role: String,
        userId: String,
        userPw: String
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
            put(COLUMN_EMAIL, email)
            put(COLUMN_ROLE, role)
            put(COLUMN_USER_ID, userId)
            put(COLUMN_PASSWORD, userPw)
        }

        // -1이면 실패, 아니면 row id
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    // 아이디 중복 확인
    fun checkIdExist(userId: String): Boolean {
        val db = this.readableDatabase

        // 같은 아이디 있는지 검색
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ?",
            arrayOf(userId)
        )

        val count = cursor.count
        cursor.close()
        db.close()

        // 0보다 크면 이미 사용중
        return count > 0
    }

    // 공지사항 저장
    fun insertNotice(title: String, content: String, date: String): Boolean {
        val db = this.writableDatabase
        val values = android.content.ContentValues()

        values.put(COLUMN_NOTICE_TITLE, title)
        values.put(COLUMN_NOTICE_CONTENT, content)
        values.put(COLUMN_NOTICE_DATE, date)

        val result = db.insert(TABLE_NOTICES, null, values)
        db.close()

        return result != -1L
    }

    // 공지사항 전체 조회, 최신순
    fun getAllNotices(): List<Notice> {
        val noticeList = mutableListOf<Notice>()
        val db = this.readableDatabase

        // id 큰 것부터 내림차순
        val cursor =
            db.rawQuery("SELECT * FROM $TABLE_NOTICES ORDER BY $COLUMN_NOTICE_ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTICE_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTICE_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTICE_CONTENT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTICE_DATE))

                noticeList.add(Notice(id, title, content, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return noticeList
    }

    // 공지사항 수정
    fun updateNotice(id: Int, title: String, content: String, date: String): Boolean {
        val db = this.writableDatabase
        val values = android.content.ContentValues()
        values.put(COLUMN_NOTICE_TITLE, title)
        values.put(COLUMN_NOTICE_CONTENT, content)
        values.put(COLUMN_NOTICE_DATE, date)

        val result =
            db.update(TABLE_NOTICES, values, "$COLUMN_NOTICE_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // 공지사항 삭제
    fun deleteNotice(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NOTICES, "$COLUMN_NOTICE_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // 로그인 확인하고 role 반환
    fun loginAndGetRole(userId: String, userPw: String): String? {
        val db = this.readableDatabase
        var role: String? = null

        val query =
            "SELECT $COLUMN_ROLE FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(userId, userPw))

        if (cursor.moveToFirst()) {
            // 일치하면 role 값 가져오기
            role = cursor.getString(0)
        }

        cursor.close()
        db.close()

        return role // 로그인 실패 시 null 반환
    }

    // 비밀번호 맞는지 확인 (회원탈퇴용)
    fun checkPassword(userId: String, userPw: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(userId, userPw)
        )
        val matched = cursor.count > 0
        cursor.close()
        db.close()
        return matched
    }

    // 회원 탈퇴
    fun deleteUser(userId: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_USERS, "$COLUMN_USER_ID = ?", arrayOf(userId))
        db.close()
        return result > 0
    }
}