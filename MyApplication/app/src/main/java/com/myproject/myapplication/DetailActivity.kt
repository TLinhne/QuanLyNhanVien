package com.myproject.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myproject.myapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var user: User
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

// Nhận userId từ Intent
        val userId = intent.getIntExtra("userId", -1)

// Kiểm tra xem có userId được nhận hay không
        if (userId != -1) {
            // Truy vấn cơ sở dữ liệu để lấy thông tin chi tiết người dùng
            user = dbHelper.getUserById(userId)!!

            // Kiểm tra xem có lấy được thông tin người dùng hay không
            if (user != null) {
                // Hiển thị thông tin người dùng
                binding.textViewUsername.text = "Username: ${user.username}"
                binding.textViewEmail.text = "Email: ${user.email}"
                binding.textViewPhone.text = "Phone: ${user.phone}"
            } else {
                // Xử lý trường hợp không tìm thấy người dùng với id đã cho
                Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show()
                finish() // Kết thúc hoạt động nếu không tìm thấy người dùng
            }
        } else {
            // Xử lý trường hợp không nhận được userId từ Intent
            Toast.makeText(this, "Không nhận được thông tin người dùng", Toast.LENGTH_SHORT).show()
            finish() // Kết thúc hoạt động nếu không nhận được userId
        }
        // Xử lý sự kiện khi người dùng nhấn nút "Cập nhật"
        binding.buttonUpdate.setOnClickListener {
            val userBundle = Bundle()
            userBundle.putInt("id", user.id)
            userBundle.putString("username", user.username)
            userBundle.putString("password", user.password)
            userBundle.putString("email", user.email)
            userBundle.putString("phone", user.phone)

            val intent = Intent(this, UpdateUserActivity::class.java)
            intent.putExtra("userBundle", userBundle)
            startActivity(intent)
        }

        // Xử lý sự kiện khi người dùng nhấn nút "Thêm"
        binding.buttonAdd.setOnClickListener {
            // Chuyển đến màn hình thêm người dùng (có thể bạn cần tạo một màn hình riêng cho việc thêm người dùng)
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        // Xử lý sự kiện khi người dùng nhấn nút "Xóa"
        binding.buttonDelete.setOnClickListener {
            // Xóa người dùng từ cơ sở dữ liệu
            val deleted = dbHelper.deleteUser(user.id)

            if (deleted > 0) {
                Toast.makeText(this, "Người dùng đã bị xóa", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, DisplayDataActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Xóa người dùng không thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
