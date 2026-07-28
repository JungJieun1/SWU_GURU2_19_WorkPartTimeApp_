package com.example.swu_guru_19_workparttimeapp_

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 데이터 클래스 정의
data class Notice(val id: Int, val title: String, val content: String, val date: String)
data class Workplace(val id: Int, val name: String, val address: String, val category: String, val phone: String)
data class Employee(val id: Int, val name: String, val phone: String, val hourlyWage: String, val role: String)
data class Task(val id: Int, val title: String, val content: String, val taskType: String, val assignee: String)
data class AttendanceData(val id: Int = 0, val name: String, val date: String, val checkInTime: String, val checkOutTime: String, val status: String
)

class UserDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 5

        // 1. 유저 테이블
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ROLE = "role"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_PASSWORD = "password"

        // 공지사항
        const val TABLE_NOTICES = "notices"
        const val COLUMN_NOTICE_ID = "noticeId"
        const val COLUMN_NOTICE_TITLE = "title"
        const val COLUMN_NOTICE_CONTENT = "content"
        const val COLUMN_NOTICE_DATE = "date"

        // 업장
        const val TABLE_WORKPLACES = "workplaces"
        const val COLUMN_WORKPLACE_ID = "id"
        const val COLUMN_WORKPLACE_NAME = "name"
        const val COLUMN_WORKPLACE_ADDRESS = "address"
        const val COLUMN_WORKPLACE_CATEGORY = "category"

        const val COLUMN_WORKPLACE_PHONE = "phone"

        // 직원
        const val TABLE_EMPLOYEES = "employees"
        const val COLUMN_EMP_ID = "id"
        const val COLUMN_EMP_NAME = "name"
        const val COLUMN_EMP_PHONE = "phone"
        const val COLUMN_EMP_WAGE = "hourly_wage"
        const val COLUMN_EMP_ROLE = "role"

        // 업무
        const val TABLE_TASKS = "tasks"
        const val COLUMN_TASK_ID = "id"
        const val COLUMN_TASK_TITLE = "title"
        const val COLUMN_TASK_CONTENT = "content"
        const val COLUMN_TASK_TYPE = "task_type" // '공동' 또는 '개인'
        const val COLUMN_TASK_ASSIGNEE = "assignee" // 담당자 이름

        // 5. 근태(Attendance) 테이블
        const val TABLE_ATTENDANCE = "attendance"
        const val COLUMN_ATT_ID = "id"
        const val COLUMN_ATT_NAME = "name"
        const val COLUMN_ATT_DATE = "date"
        const val COLUMN_ATT_CHECK_IN = "check_in_time"
        const val COLUMN_ATT_CHECK_OUT = "check_out_time"
        const val COLUMN_ATT_STATUS = "status"

        const val PREFS_NAME = "app_prefs"
        const val PREF_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // users 테이블 생성
        val createTableQuery = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_PHONE TEXT, "
                + "$COLUMN_EMAIL TEXT, "
                + "$COLUMN_ROLE TEXT, "
                + "$COLUMN_USER_ID TEXT, "
                + "$COLUMN_PASSWORD TEXT)")
        db.execSQL(createTableQuery)

        // notices 테이블 생성
        val createNoticeTable = ("CREATE TABLE $TABLE_NOTICES ("
                + "$COLUMN_NOTICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NOTICE_TITLE TEXT, "
                + "$COLUMN_NOTICE_CONTENT TEXT, "
                + "$COLUMN_NOTICE_DATE TEXT)")
        db.execSQL(createNoticeTable)

        // 기본 공지사항 2개 추가
        db.execSQL("INSERT INTO $TABLE_NOTICES ($COLUMN_NOTICE_TITLE, $COLUMN_NOTICE_CONTENT, $COLUMN_NOTICE_DATE) VALUES ('7/20 휴무 신청 안내', '다음 달 휴무 신청은 이번 주 금요일까지 사장님께 직접 문자 메시지로 전달 바랍니다.\n\n기한을 꼭 지켜주시기 바라며, 주말 근무자의 경우 금요일 저녁 마감 전까지 알려주시면 감사하겠습니다.', '2026-07-20')")
        db.execSQL("INSERT INTO $TABLE_NOTICES ($COLUMN_NOTICE_TITLE, $COLUMN_NOTICE_CONTENT, $COLUMN_NOTICE_DATE) VALUES ('7/18 매장 청소 담당 안내', '미들 타임 청소 구역이 일부 변경되었습니다.\n\n기존 창가 쪽 테이블 구역에서 카운터 앞 대기석까지 포함하여 정리해 주시면 됩니다. 변경된 매뉴얼은 포스기 옆에 붙여두었으니 출근 시 꼭 확인해 주세요.', '2026-07-18')")

        // workplaces (업장) 테이블 생성
        val createWorkplacesTable = ("CREATE TABLE $TABLE_WORKPLACES ("
                + "$COLUMN_WORKPLACE_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_WORKPLACE_NAME TEXT, "
                + "$COLUMN_WORKPLACE_ADDRESS TEXT, "
                + "$COLUMN_WORKPLACE_CATEGORY TEXT, "
                + "$COLUMN_WORKPLACE_PHONE TEXT)")
        db.execSQL(createWorkplacesTable)

        // employees (직원) 테이블 생성
        val createEmployeesTable = ("CREATE TABLE $TABLE_EMPLOYEES ("
                + "$COLUMN_EMP_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_EMP_NAME TEXT, "
                + "$COLUMN_EMP_PHONE TEXT, "
                + "$COLUMN_EMP_WAGE TEXT, "
                + "$COLUMN_EMP_ROLE TEXT)")
        db.execSQL(createEmployeesTable)

        // tasks (업무) 테이블 생성
        val createTasksTable = ("CREATE TABLE $TABLE_TASKS ("
                + "$COLUMN_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_TASK_TITLE TEXT, "
                + "$COLUMN_TASK_CONTENT TEXT, "
                + "$COLUMN_TASK_TYPE TEXT, "
                + "$COLUMN_TASK_ASSIGNEE TEXT)")
        db.execSQL(createTasksTable)

        // attendance (근태) 테이블 생성
        val createAttendanceTable = ("CREATE TABLE $TABLE_ATTENDANCE ("
                + "$COLUMN_ATT_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_ATT_NAME TEXT, "
                + "$COLUMN_ATT_DATE TEXT, "
                + "$COLUMN_ATT_CHECK_IN TEXT, "
                + "$COLUMN_ATT_CHECK_OUT TEXT, "
                + "$COLUMN_ATT_STATUS TEXT)")
        db.execSQL(createAttendanceTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTICES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKPLACES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ATTENDANCE")
        onCreate(db)
    }

    // ==========================================
    // 0. 기존 팀원 기능 (회원가입, 로그인, 공지사항)
    // ==========================================

    fun insertUser(name: String, phone: String, email: String, role: String, userId: String, userPw: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
            put(COLUMN_EMAIL, email)
            put(COLUMN_ROLE, role)
            put(COLUMN_USER_ID, userId)
            put(COLUMN_PASSWORD, userPw)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun checkIdExist(userId: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ?", arrayOf(userId))
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun insertNotice(title: String, content: String, date: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTICE_TITLE, title)
            put(COLUMN_NOTICE_CONTENT, content)
            put(COLUMN_NOTICE_DATE, date)
        }
        val result = db.insert(TABLE_NOTICES, null, values)
        db.close()
        return result != -1L
    }

    fun getAllNotices(): List<Notice> {
        val noticeList = mutableListOf<Notice>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NOTICES ORDER BY $COLUMN_NOTICE_ID DESC", null)
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

    fun updateNotice(id: Int, title: String, content: String, date: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOTICE_TITLE, title)
            put(COLUMN_NOTICE_CONTENT, content)
            put(COLUMN_NOTICE_DATE, date)
        }
        val result = db.update(TABLE_NOTICES, values, "$COLUMN_NOTICE_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun deleteNotice(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NOTICES, "$COLUMN_NOTICE_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    fun loginAndGetRole(userId: String, userPw: String): String? {
        val db = this.readableDatabase
        var role: String? = null
        val cursor = db.rawQuery("SELECT $COLUMN_ROLE FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ? AND $COLUMN_PASSWORD = ?", arrayOf(userId, userPw))
        if (cursor.moveToFirst()) {
            role = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return role
    }

    fun checkPassword(userId: String, userPw: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $COLUMN_USER_ID = ? AND $COLUMN_PASSWORD = ?", arrayOf(userId, userPw))
        val matched = cursor.count > 0
        cursor.close()
        db.close()
        return matched
    }

    fun deleteUser(userId: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_USERS, "$COLUMN_USER_ID = ?", arrayOf(userId))
        db.close()
        return result > 0
    }

    // ==========================================
    // 1. 업장(Workplace) 관련 기능
    // ==========================================

    fun insertWorkplace(name: String, address: String, category: String, phone: String
    ): Boolean {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_WORKPLACE_NAME, name)
            put(COLUMN_WORKPLACE_ADDRESS, address)
            put(COLUMN_WORKPLACE_CATEGORY, category)
            put(COLUMN_WORKPLACE_PHONE, phone)
        }

        val result = db.insert(TABLE_WORKPLACES, null, values)
        db.close()

        return result != -1L
    }

    fun updateWorkplace(id: Int, name: String, address: String, category: String, phone: String
    ): Boolean {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_WORKPLACE_NAME, name)
            put(COLUMN_WORKPLACE_ADDRESS, address)
            put(COLUMN_WORKPLACE_CATEGORY, category)
            put(COLUMN_WORKPLACE_PHONE, phone)
        }

        val result = db.update(
            TABLE_WORKPLACES,
            values,
            "$COLUMN_WORKPLACE_ID = ?",
            arrayOf(id.toString())
        )

        db.close()
        return result > 0
    }
    fun getAllWorkplaces(): List<Workplace> {
        val list = mutableListOf<Workplace>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_WORKPLACES ORDER BY $COLUMN_WORKPLACE_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_NAME))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_ADDRESS))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_CATEGORY))
                val phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_WORKPLACE_PHONE)
                )
                list.add(Workplace(id, name, address, category, phone))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun deleteWorkplace(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_WORKPLACES, "$COLUMN_WORKPLACE_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // ==========================================
    // 2. 직원(Employee) 관련 기능
    // ==========================================

    fun insertEmployee(name: String, phone: String, hourlyWage: String, role: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMP_NAME, name)
            put(COLUMN_EMP_PHONE, phone)
            put(COLUMN_EMP_WAGE, hourlyWage)
            put(COLUMN_EMP_ROLE, role)
        }
        val result = db.insert(TABLE_EMPLOYEES, null, values)
        db.close()
        return result != -1L
    }

    fun updateEmployee(
        id: Int,
        name: String,
        phone: String,
        hourlyWage: String,
        role: String
    ): Boolean {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_EMP_NAME, name)
            put(COLUMN_EMP_PHONE, phone)
            put(COLUMN_EMP_WAGE, hourlyWage)
            put(COLUMN_EMP_ROLE, role)
        }

        val result = db.update(
            TABLE_EMPLOYEES,
            values,
            "$COLUMN_EMP_ID = ?",
            arrayOf(id.toString())
        )

        db.close()
        return result > 0
    }

    fun getAllEmployees(): List<Employee> {
        val list = mutableListOf<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EMPLOYEES ORDER BY $COLUMN_EMP_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EMP_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMP_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMP_PHONE))
                val wage = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMP_WAGE))
                val role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMP_ROLE))
                list.add(Employee(id, name, phone, wage, role))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun deleteEmployee(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_EMPLOYEES, "$COLUMN_EMP_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // ==========================================
    // 3. 업무(Task) 관련 기능
    // ==========================================

    fun insertTask(title: String, content: String, taskType: String, assignee: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_TITLE, title)
            put(COLUMN_TASK_CONTENT, content)
            put(COLUMN_TASK_TYPE, taskType)
            put(COLUMN_TASK_ASSIGNEE, assignee)
        }
        val result = db.insert(TABLE_TASKS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllTasks(): List<Task> {
        val list = mutableListOf<Task>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS ORDER BY $COLUMN_TASK_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TITLE))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_CONTENT))
                val taskType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TYPE))
                val assignee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_ASSIGNEE))
                list.add(Task(id, title, content, taskType, assignee))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun deleteTask(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_TASKS, "$COLUMN_TASK_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // ==========================================
    // 4. 근태(Attendance) 관련 기능 (신규 추가)
    // ==========================================

    // 근태 기록 추가
    fun insertAttendance(name: String, date: String, checkIn: String, checkOut: String, status: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ATT_NAME, name)
            put(COLUMN_ATT_DATE, date)
            put(COLUMN_ATT_CHECK_IN, checkIn)
            put(COLUMN_ATT_CHECK_OUT, checkOut)
            put(COLUMN_ATT_STATUS, status)
        }
        val result = db.insert(TABLE_ATTENDANCE, null, values)
        db.close()
        return result != -1L
    }

    // 전체 근태 목록 조회
    fun getAllAttendance(): List<AttendanceData> {
        val list = mutableListOf<AttendanceData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_ATTENDANCE ORDER BY $COLUMN_ATT_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ATT_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATT_NAME))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATT_DATE))
                val checkIn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATT_CHECK_IN))
                val checkOut = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATT_CHECK_OUT))
                val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ATT_STATUS))
                list.add(AttendanceData(id, name, date, checkIn, checkOut, status))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    // 근태 기록 삭제
    fun deleteAttendance(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_ATTENDANCE, "$COLUMN_ATT_ID = ?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}