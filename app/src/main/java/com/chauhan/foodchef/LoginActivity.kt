package com.chauhan.foodchef

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.chauhan.foodchef.databinding.ActivityLoginBinding
import com.chauhan.foodchef.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private var name:String?=null
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val googleSignInOption= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

//        initialize firebase auth
        auth= Firebase.auth

//        initialize database
        database= Firebase.database.reference

//        initialize email authentication
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOption)


        binding.loginButton.setOnClickListener {
//          get data from text field

            email=binding.email.text.toString().trim()
            password=binding.passwordd.text.toString().trim()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please Fill All The Details", Toast.LENGTH_SHORT).show()
            }else{
                createUserAccount()
                Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()

            }


        }
        binding.dontHaveAccount.setOnClickListener {
            val intent= Intent(this,SignActivity::class.java)
            startActivity(intent)
        }

        binding.googleeButton.setOnClickListener {
            val signInIntent=googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    //    launcher for google sign in
    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if(result.resultCode == Activity.RESULT_OK){

            val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account: GoogleSignInAccount? = task.result
                val credential= GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask->
                    if(authTask.isSuccessful){
//                        successfully sign in with google
                        Toast.makeText(this,"Log-in Successful",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
//                        sign in failed
                        Toast.makeText(this,"Log-in Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else{
//                        sign in failed
            Toast.makeText(this,"Log-in Failed",Toast.LENGTH_SHORT).show()
        }
    }


    private fun createUserAccount() {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                val user = auth.currentUser
                updateUi(user)
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                    if(task.isSuccessful){
                        saveUserData()
                        val user=auth.currentUser
                        updateUi(user)
                    }else{
                        Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email=binding.email.text.toString().trim()
        password=binding.passwordd.toString().trim()

        val user =UserModel(name,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid

//        save data to database
        database.child("user").child(userId).setValue(user)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}