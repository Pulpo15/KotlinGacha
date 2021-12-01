package com.example.kotlingacha.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlingacha.R
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        usernameInput = findViewById<TextInputLayout>(R.id.usernameInput).editText ?: return
        passwordInput = findViewById<TextInputLayout>(R.id.passwordInput).editText ?: return

        // Check username
        usernameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                usernameInput.error =
                    if (usernameInput.text.toString().isValidEmail())
                        null
                    else
                        "Invalid username"
            }
        }

        // Check password
        passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                passwordInput.error =
                    if (passwordInput.text.toString().isValidPassword())
                        null
                    else
                        "Invalid password"
            }
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {

            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            // Check form data
            if (checkLogin(user, pass)) {
                doLogin(user, pass)

                // Start new activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // Kill LoginActivity to keep mem
                finish()
            } else {
                Toast.makeText(this, "Check the data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLogin(user: String?, pass: String?): Boolean {
        // TODO: Parse user and pass with DB
        return user.isValidEmail() && pass.isValidPassword()
    }

    private fun doLogin(user: String, pass: String) {
        // TODO: Aquí l'app hauria de memoritzar les dades per tal que el login fos efectiu
    }

    private fun String?.isValidEmail(): Boolean {
        return if (this.isNullOrEmpty()) false
        else Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun String?.isValidPassword(): Boolean {
        return !this.isNullOrEmpty() && this.length >= 5
    }
}