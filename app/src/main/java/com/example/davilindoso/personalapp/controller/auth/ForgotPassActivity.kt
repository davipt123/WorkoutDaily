package com.example.davilindoso.personalapp.controller.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.davilindoso.personalapp.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        setTitle(R.string.redefinir_senha)
        txtEmail = findViewById(R.id.txtEmail)
        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
    }

    fun send(view: View) {
        val email = txtEmail.text.toString()

        if (!TextUtils.isEmpty(email)) {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            progressBar.visibility = View.VISIBLE
                            startActivity(Intent(this, LoginActivity::class.java))
                        } else {
                            Toast.makeText(this, "Erro ao enviar o email", Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
}
