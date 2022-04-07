package com.example.kotlingacha.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlingacha.R
import com.example.kotlingacha.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    lateinit var binding: ActivityLoginBinding

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK)
            finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        usernameInput = findViewById<TextInputLayout>(R.id.emailInput).editText ?: return
        passwordInput = findViewById<TextInputLayout>(R.id.passwordInput).editText ?: return

        // Check username
        usernameInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
                usernameInput.error =
                    if (usernameInput.text.toString().isValidEmail())
                        null
                    else
                        "Invalid username"
            }
        }

        // Check password
        passwordInput.setOnFocusChangeListener { v, hasFocus ->
            showKeyboard(v)
            if (!hasFocus) {
                hideKeyboard(v)
                passwordInput.error =
                    if (passwordInput.text.toString().isValidPassword())
                        null
                    else
                        "Invalid password"
            }
        }

        binding.loginButton.setOnClickListener {

            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            // Check form data
            if (checkLogin(user, pass)) {
                doLogin(user, pass)
            } else {
                Toast.makeText(this, "Check the data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.goToRegister.setOnClickListener{
            launcher.launch(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun checkLogin(user: String?, pass: String?): Boolean {
        // TODO: Parse user and pass with DB
        return user.isValidEmail() && pass.isValidPassword()
    }

    private fun doLogin(user: String, pass: String) {
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Start new activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    // Kill LoginActivity to keep mem
                    finish()
                    Toast.makeText(this, "Logged with user ${auth.currentUser?.displayName}", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun String?.isValidEmail(): Boolean {
        return if (this.isNullOrEmpty()) false
        else Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun String?.isValidPassword(): Boolean {
        return !this.isNullOrEmpty() && this.length >= 5
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }
}