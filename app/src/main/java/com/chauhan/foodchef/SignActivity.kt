package com.chauhan.foodchef

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.chauhan.foodchef.databinding.ActivitySignBinding
import com.chauhan.foodchef.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username:String
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var googleSignInClient:GoogleSignInClient
    private val binding:ActivitySignBinding  by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignInOption=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

//        initialize firebase auth
        auth=Firebase.auth

//        initialize database
        database=Firebase.database.reference

//        initialize email authentication
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOption)


        binding.alreadyHaveAccount.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.createAccountButton.setOnClickListener {

           username=binding.userName.text.toString()
            email=binding.emailAddress.text.toString().trim()
            password=binding.password.text.toString().trim()

            if(username.isBlank()||password.isBlank()||email.isBlank()){
                Toast.makeText(this,"Please Fill All The Details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }
        }
        binding.googleButton.setOnClickListener {
            val signIntent=googleSignInClient.signInIntent
            launcher.launch(signIntent)
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
                        Toast.makeText(this,"Sign-in Successful",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
//                        sign in failed
                        Toast.makeText(this,"Google Sign In Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
            else{
//                        sign in failed
                Toast.makeText(this,"Google Sign In Failed",Toast.LENGTH_SHORT).show()
            }
        }


    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"Account Not Created",Toast.LENGTH_SHORT).show()
                Log.d("Account","createAccount: Failure",task.exception)
            }
        }
    }

    private fun saveUserData() {
        username=binding.userName.text.toString()
        email=binding.emailAddress.text.toString().trim()
        password=binding.password.text.toString().trim()

        val user=UserModel(username,email,password)
        val userId=FirebaseAuth.getInstance().currentUser!!.uid
//        save data in firebase
        database.child("user").child(userId).setValue(user)
    }
}