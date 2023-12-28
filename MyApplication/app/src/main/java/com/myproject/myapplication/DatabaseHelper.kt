package com.myproject.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val TABLE_USERS = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS $TABLE_USERS " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "password TEXT, " +
                "email TEXT, " +
                "phone TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Thêm một người dùng mới
    fun addUser(username: String, password: String, email: String, phone: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        values.put("email", email)
        values.put("phone", phone)
        val newRowId = db.insert(TABLE_USERS, null, values)
        db.close()
        return newRowId
    }

    // Kiểm tra thông tin đăng nhập
    fun checkLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE username=? AND password=?", arrayOf(username, password))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }

    // Lấy danh sách tất cả người dùng
    @SuppressLint("Range")
    fun getAllUsers(): ArrayList<User> {
        val userList = ArrayList<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)
        while (cursor.moveToNext()) {
            val user = User(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("username")),
                cursor.getString(cursor.getColumnIndex("password")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("phone"))
            )
            userList.add(user)
        }
        cursor.close()
        db.close()
        return userList
    }


    @SuppressLint("Range")
// Lấy thông tin chi tiết người dùng dựa trên ID
    fun getUserById(userId: Int): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE id = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        return if (cursor.moveToFirst()) {
            val user = User(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("username")),
                cursor.getString(cursor.getColumnIndex("password")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("phone"))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    // Sửa thông tin người dùng
    fun updateUser(id: Int, username: String, password: String, email: String, phone: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password) // Thêm trường mật khẩu vào dữ liệu cần cập nhật
        values.put("email", email)
        values.put("phone", phone)
        return db.update("users", values, "id = ?", arrayOf(id.toString()))
    }


    // Xóa người dùng
    fun deleteUser(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_USERS, "id=?", arrayOf(id.toString()))
    }


    // Kiểm tra sự tồn tại của tên người dùng
    fun isUsernameExists(username: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        val count = cursor.count
        cursor.close()
        return count > 0
    }

    // Thêm tài khoản người dùng mới vào cơ sở dữ liệu
    fun insertUser(username: String, password: String, email: String, phone: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        values.put("email", email)
        values.put("phone", phone)
        return db.insert("users", null, values)
    }


}
