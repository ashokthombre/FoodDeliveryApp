package com.example.foodorderapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderapp.databinding.ActivitySigninBinding
import com.example.foodorderapp.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: ProgressDialog

    private val binding: ActivitySigninBinding by lazy {
        ActivitySigninBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference
        val googleSignInOptions: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating User.")
        progressDialog.setMessage("loading....")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.alreadyaccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java);
            startActivity(intent);
            finish()

        }

        binding.createaccountBtn.setOnClickListener {
            userName = binding.userName.text.toString().trim()
            email = binding.userEmail.text.toString().trim()
            password = binding.password.text.toString().trim()
            progressDialog.show()
            if (userName.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            } else {
                createAccount(email, password, userName)
            }
        }

        binding.googleBtn.setOnClickListener {

         val signIntent:Intent=googleSignInClient.signInIntent
            launcher.launch(signIntent)

        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential: AuthCredential =
                        GoogleAuthProvider.getCredential(account?.idToken, null)

                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Account Creation Failed..", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("SiginError", task.exception.toString())
                        }
                    }
                } else {
                    Toast.makeText(this, "Account Creation Failed..", Toast.LENGTH_SHORT).show()
                    Log.d("SiginError", task.exception.toString())
                }
            }

        }

    private fun createAccount(email: String, password: String, userName: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully !", Toast.LENGTH_SHORT).show()
                saveUserData(email, password, userName)
                startActivity(Intent(this, LoginActivity::class.java))
                progressDialog.dismiss()
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("LoginError", task.exception.toString())
                progressDialog.dismiss()
            }
        }

    }

    private fun saveUserData(email: String, password: String, userName: String) {

        val user = UserModel(userName, email, password,"Enter valid phone number","Enter valid Address")
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)


    }


}