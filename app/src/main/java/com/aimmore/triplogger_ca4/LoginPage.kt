package com.aimmore.triplogger_ca4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var loginBtn: Button
    private lateinit var emailTV: EditText
    private lateinit var passwordTV: EditText
    private lateinit var signupLink: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("TripLoggerPrefs", MODE_PRIVATE)

        val savedUID = sharedPreferences.getString("UID", null)
        if (savedUID != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_login_page)

        auth = FirebaseAuth.getInstance()

        loginBtn = findViewById(R.id.login)
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        signupLink = findViewById(R.id.signupLink)

        loginBtn.setOnClickListener {
            val email = emailTV.text.toString().trim()
            val password = passwordTV.text.toString().trim()

            if (email.isEmpty()) {
                emailTV.error = "Email is required"
                emailTV.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordTV.error = "Password is required"
                passwordTV.requestFocus()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            val uid = user.uid

                            val editor = sharedPreferences.edit()
                            editor.putString("UID", uid)
                            editor.apply()

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Login failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        signupLink.setOnClickListener {
            startActivity(Intent(this, SignupPage::class.java))
        }
    }
}
