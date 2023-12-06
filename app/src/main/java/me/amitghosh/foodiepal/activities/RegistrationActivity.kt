package me.amitghosh.foodiepal.activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast
import me.amitghosh.foodiepal.databinding.ActivityRegistrationBinding
import me.amitghosh.foodiepal.model.UserDto

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnRigis.setOnClickListener {
            val name=binding.ed1.text.toString()
            val email=binding.ed2.text.toString()
            val password=binding.ed3.text.toString()
            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                register(name, email, password);
            }else{
                FancyToast.makeText(this, "All fields required!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.loginLink.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    createUserInDb(name, user);
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    FancyToast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun createUserInDb(name: String, user: FirebaseUser?) {
        if(user !== null) {
            db.collection("users")
                .add(UserDto(name, user.email))
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(applicationContext, "Registration Completed! Logging In...", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
                    Log.w(TAG, "Error adding document", e)
                }
        } else {
            Toast.makeText(applicationContext, "Failed to create user !", Toast.LENGTH_LONG).show()

        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user !== null) {
            FancyToast.makeText(
                this,
                "Welcome ${user.displayName}!",
                FancyToast.LENGTH_LONG,
                FancyToast.INFO,
                true
            );
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            FancyToast.makeText(this,"Hello World !",FancyToast.LENGTH_LONG,FancyToast.ERROR,true);
        }
    }
}