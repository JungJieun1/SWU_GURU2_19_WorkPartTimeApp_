package com.example.swu_guru_19_workparttimeapp_

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AttendanceDBHelper(context: Context)
    : SQLiteOpenHelper(
    context,
    "attendance.db",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE attendance(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                employeeName TEXT,
                date TEXT,
                checkin TEXT,
                checkout TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS attendance")
        onCreate(db)
    }

    fun insertAttendance(
        employeeName:String,
        date:String,
        checkIn:String
    ):Boolean{

        val db=writableDatabase

        val values=ContentValues()

        values.put("employeeName",employeeName)
        values.put("date",date)
        values.put("checkin",checkIn)
        values.put("checkout","")

        return db.insert(
            "attendance",
            null,
            values
        )!=-1L
    }

    fun hasAttendance(
        employeeName:String,
        date:String
    ):Boolean{

        val db=readableDatabase

        val cursor=db.rawQuery(

            """
            SELECT *
            FROM attendance
            WHERE employeeName=?
            AND date=?
            """.trimIndent(),

            arrayOf(employeeName,date)

        )

        val result=cursor.count>0

        cursor.close()

        return result
    }

    fun updateCheckOut(
        employeeName:String,
        date:String,
        checkOut:String
    ):Boolean{

        val db=writableDatabase

        val values=ContentValues()

        values.put("checkout",checkOut)

        return db.update(

            "attendance",

            values,

            "employeeName=? AND date=?",

            arrayOf(employeeName,date)

        )>0
    }

    fun getAttendance(
        employeeName:String,
        date:String
    ):Pair<String,String>?{

        val db=readableDatabase

        val cursor=db.rawQuery(

            """
            SELECT checkin,checkout
            FROM attendance
            WHERE employeeName=?
            AND date=?
            """.trimIndent(),

            arrayOf(employeeName,date)

        )

        var result:Pair<String,String>?=null

        if(cursor.moveToFirst()){

            result=Pair(

                cursor.getString(0),

                cursor.getString(1)

            )
        }

        cursor.close()

        return result
    }

    fun getAllAttendance():Cursor{

        return readableDatabase.rawQuery(

            """
            SELECT
            id as _id,
            employeeName,
            date,
            checkin,
            checkout
            FROM attendance
            ORDER BY date DESC
            """.trimIndent(),

            null

        )
    }

    fun getAllAttendanceList(): List<AttendanceData> {

        val list = mutableListOf<AttendanceData>()

        val db = readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT employeeName,
               date,
               checkin,
               checkout
        FROM attendance
        ORDER BY date DESC
        """.trimIndent(),
            null
        )

        while (cursor.moveToNext()) {

            val employee = cursor.getString(0)
            val date = cursor.getString(1)
            val checkIn = cursor.getString(2)
            val checkOut = cursor.getString(3)

            val status =
                if (checkOut.isNullOrEmpty())
                    "근무중"
                else
                    "퇴근"

            list.add(
                AttendanceData(
                    employee,
                    status,
                    date,
                    checkIn,
                    if (checkOut.isEmpty()) "-" else checkOut
                )
            )
        }

        cursor.close()

        return list
    }

}