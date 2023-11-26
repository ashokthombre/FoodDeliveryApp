package com.example.foodorderapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodorderapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var progressDialog:ProgressDialog

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth=Firebase.auth
        database= FirebaseDatabase.getInstance()

        if (auth.currentUser!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Loging In.")
        progressDialog.setMessage("please wait.")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.donthaveaccount.setOnClickListener {
            val intent=Intent(this,SigninActivity::class.java);
            startActivity(intent)
            finish()
        }

        binding.createaccountBtn.setOnClickListener {
            email=binding.userEmail.text.toString().trim()
            password=binding.password.text.toString().trim()
             progressDialog.show()
            if(email.isBlank() || password.isBlank())
            {
                Toast.makeText(this,"Fill All Field",Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
            else
            {
                loginUser(email,password)
                progressDialog.dismiss()
            }


        }
    }

    private fun loginUser(email: String, password: String) {

       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this)
       {
           if (it.isSuccessful)
           {
               Toast.makeText(this,"Login Successfully !",Toast.LENGTH_SHORT).show()
               startActivity(Intent(this,MainActivity::class.java))
               finish()
           }
           else
           {
               Toast.makeText(this,"Something Went wrong !",Toast.LENGTH_SHORT).show()
               Log.d("LoginError",it.exception.toString())
           }
       }

    }
}