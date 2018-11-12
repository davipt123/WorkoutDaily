package com.example.davilindoso.personalapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()

    }

    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }

    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun login(view: View) {
        loginUser()
    }

    private fun loginUser() {
        val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    action()
                } else {
                    Toast.makeText(this, "Falha de autenticação", Toast.LENGTH_LONG)
                }
            }
        }
    }

    private fun action() {
        startActivity(Intent(this, MainActivity::class.java))
        recreate()
    }

    override fun recreate() {
        super.recreate()
        txtPassword.setText("")
    }
}
