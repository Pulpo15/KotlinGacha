package com.example.kotlingacha.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlingacha.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()

        //Instance to the Database
        val db = Firebase.firestore

        binding.nameTextInput.setOnFocusChangeListener{v, hasFocus ->
            if (!hasFocus && binding.nameTextInput.text.isNullOrEmpty())
                binding.nameTextInput.error = "Error"
        }
        binding.registerButton.setOnClickListener{
            val email = binding.emailTextInput.text.toString()
            val password = binding.passwordRegisterTextInput.text.toString()
            val name = binding.nameTextInput.text.toString()

            if (email.isEmpty() || password.isEmpty() || password != binding.repeatPasswordTextInput.text.toString())
                return@setOnClickListener Toast.makeText(this, "Check data", Toast.LENGTH_SHORT).show()
            binding.registerProgressBar.visibility = View.VISIBLE

            //Prepare data for database
            val user = hashMapOf(
                "mail" to email,
                "name" to name,
                "card" to false,
                "cardName" to ""
            )

            //Add data to database
            db.collection("users")
                .document(name)
                .set(user)
                .addOnSuccessListener { Toast.makeText(this, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { e -> Toast.makeText(this, "Error writing document", Toast.LENGTH_SHORT).show() }

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener{
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    //Modify firebase user to add the username
                    val user = firebaseAuth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Added username ${name} to your account", Toast.LENGTH_SHORT).show()
                            }
                        }

                    setResult(Activity.RESULT_OK)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Error on login $it", Toast.LENGTH_SHORT).show()
                    binding.registerProgressBar.visibility = View.GONE
                }
            }.addOnFailureListener{
                Toast.makeText(this, "Error on register $it", Toast.LENGTH_SHORT).show()
                binding.registerProgressBar.visibility = View.GONE
            }
        }
    }
}