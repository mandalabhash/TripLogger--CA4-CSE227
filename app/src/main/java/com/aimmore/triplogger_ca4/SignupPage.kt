package com.aimmore.triplogger_ca4

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupPage : AppCompatActivity() {

    private lateinit var signupBtn: Button
    private lateinit var loginLink: TextView
    private lateinit var emilTV: EditText
    private lateinit var passwordTV: EditText
    private lateinit var confirmpasswordTV: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        auth = FirebaseAuth.getInstance()
        emilTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        confirmpasswordTV = findViewById(R.id.confirmPassword)
        signupBtn = findViewById(R.id.signup)
        loginLink = findViewById(R.id.loginLink)

        signupBtn.setOnClickListener {
            val email = emilTV.text.toString().trim()
            val password = passwordTV.text.toString().trim()
            val confirmPassword = confirmpasswordTV.text.toString().trim()


            if (email.isEmpty()) {
                emilTV.error = "Email is required"
                emilTV.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emilTV.error = "Valid email required"
                emilTV.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                passwordTV.error = "6 char password required"
                passwordTV.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty() || confirmPassword != password) {
                confirmpasswordTV.error = "Password does not match"
                confirmpasswordTV.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginPage::class.java))
                        finish()
                    } else {

                        Toast.makeText(this, "Signup failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }


        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
            finish()
        }
    }
}
