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
import com.google.firebase.ktx.Firebase
import com.shashank.sony.fancytoastlib.FancyToast
import me.amitghosh.foodiepal.databinding.ActivityLoginFormBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginFormBinding
    private lateinit var auth: FirebaseAuth
    var email:String=""
    var password:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        binding.regisLink.setOnClickListener {
            val intent= Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
       binding.btnLogin.setOnClickListener{
           email=binding.logtxt.text.toString();
           password=binding.ed3.text.toString()
           if(email.isNotEmpty() && password.isNotEmpty()){
               login(email, password)
           }else{
               FancyToast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
           }
       }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //updateUI(currentUser);
        }
    }

    fun updateUI(user: FirebaseUser?) {
        if(user !== null) {
            Toast.makeText(applicationContext, "Welcome Back ${user.displayName ?: ""}", Toast.LENGTH_LONG).show()

            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, "LoginFailed", Toast.LENGTH_SHORT).show()
        }

    }

    fun reload() {

    }
}