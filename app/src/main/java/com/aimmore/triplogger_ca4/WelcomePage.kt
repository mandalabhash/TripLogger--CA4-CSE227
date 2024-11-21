package com.aimmore.triplogger_ca4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomePage : AppCompatActivity() {

    private lateinit var loginBtn: Button
    private lateinit var signupBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        sharedPreferences = getSharedPreferences("TripLoggerPrefs", MODE_PRIVATE)

        val savedUID = sharedPreferences.getString("UID", null)
        if (savedUID != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        loginBtn = findViewById(R.id.login)
        signupBtn = findViewById(R.id.signup)

        loginBtn.setOnClickListener {
            val Intent = Intent(this, LoginPage::class.java)
            startActivity(Intent)
        }

        signupBtn.setOnClickListener {
            val Intent = Intent(this, SignupPage::class.java)
            startActivity(Intent)
        }
    }
}