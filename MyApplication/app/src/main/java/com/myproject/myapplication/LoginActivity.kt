package com.myproject.myapplication

import com.myproject.myapplication.MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)

        // Xử lý sự kiện khi người dùng nhấn nút Đăng nhập
        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (login(username, password)) {
                // Đăng nhập thành công, chuyển hướng đến màn hình DisplayDataActivity
                val intent = Intent(this, DisplayDataActivity::class.java)
                startActivity(intent)
            } else {
                // Đăng nhập thất bại, hiển thị thông báo hoặc thực hiện xử lý khác
                // Ví dụ: hiển thị lỗi
                 Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
        }


        binding.buttonRegister.setOnClickListener {
            // Chuyển màn hình
             val intent = Intent(this, RegisterActivity::class.java)
             startActivity(intent)
        }
    }

    private fun login(username: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }
}
