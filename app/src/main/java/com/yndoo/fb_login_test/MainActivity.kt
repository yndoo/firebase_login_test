package com.yndoo.fb_login_test

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val joinBtn = findViewById<Button>(R.id.JoinBtn)
        //회원가입
        joinBtn.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_et)
            val password = findViewById<EditText>(R.id.pw_et)

            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext,"회원가입 성공!",Toast.LENGTH_LONG)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        val login = findViewById<Button>(R.id.EmailLoginBtn)
        login.setOnClickListener {
            val email = findViewById<EditText>(R.id.email_et)
            val password = findViewById<EditText>(R.id.pw_et)
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext,"로그인 성공!",Toast.LENGTH_LONG).show()
                    } else {
                        Log.d(TAG, "login:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        val logout = findViewById<Button>(R.id.LogoutBtn)
        logout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(baseContext,"로그아웃 완료!",Toast.LENGTH_LONG).show()
        }



        //익명로그인
        val noemailbtn = findViewById<Button>(R.id.noEmailLoginBtn)
        noemailbtn.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.d("MainActivity", user!!.uid)

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}