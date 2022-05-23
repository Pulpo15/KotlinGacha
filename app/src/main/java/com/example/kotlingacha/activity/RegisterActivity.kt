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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

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
        binding.registerButton.setOnClickListener {
            val email = binding.emailTextInput.text.toString()
            val password = binding.passwordRegisterTextInput.text.toString()
            val name = binding.nameTextInput.text.toString().lowercase()

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
//                .addOnSuccessListener { Toast.makeText(this, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show() }
//                .addOnFailureListener { e -> Toast.makeText(this, "Error writing document", Toast.LENGTH_SHORT).show() }

            val pkmn = hashMapOf(
                "image" to "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
                "name" to "Pikachu",
                "height" to "4",
                "pokedexid" to "25",
                "weight" to "60",
                "type1" to "electric",
                "type2" to ""
            )

            addPokemon(name, pkmn){
                registerUser(email, password, name)
            }
        }
    }

    private fun addPokemon(name: String, pkmn: Any, onLoaded : () -> Unit){
        val db = Firebase.firestore
        db.collection("users")
            .document(name)
            .collection("Pokemon")
            .document("Pikachu")
            .set(pkmn)
            .addOnSuccessListener { onLoaded() }
    }

    private fun registerUser(email: String,password: String, name: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener{
            loginUser(email, password, name)
        }.addOnFailureListener{
            Toast.makeText(this, "Error on register $it", Toast.LENGTH_SHORT).show()
            binding.registerProgressBar.visibility = View.GONE
        }
    }

    private fun loginUser(email: String, password: String, name: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

            //Modify firebase user to add the username
            val user = firebaseAuth.currentUser
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user?.updateProfile(profileUpdates)
            setResult(Activity.RESULT_OK)
            Toast.makeText(this, "Logged with user " +
                    (Firebase.auth.currentUser?.displayName ?: name), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.NAME, name)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Error on login $it", Toast.LENGTH_SHORT).show()
            binding.registerProgressBar.visibility = View.GONE
        }
    }
}