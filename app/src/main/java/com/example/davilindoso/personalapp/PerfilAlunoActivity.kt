package com.example.davilindoso.personalapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*


private lateinit var altura: TextView
private lateinit var cpf: TextView
private lateinit var email: TextView
private lateinit var idade: TextView
private lateinit var nome: TextView
private lateinit var peso: TextView
private lateinit var telefone: TextView


class PerfilAlunoActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_aluno)
        inicializarComponentes()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        dbReference = database.reference.child("user").child(user!!.uid).child("alunos")
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        dadosAlunoDTO(a)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    private fun inicializarComponentes() {
        var param = intent.getStringExtra("valorLinha")
        altura = findViewById(R.id.alturaAluno)
        cpf = findViewById(R.id.cpfAluno)
        email = findViewById(R.id.emailAluno)
        nome = findViewById(R.id.nomeAluno)
        idade = findViewById(R.id.idadeAluno)
        peso = findViewById(R.id.pesoAluno)
        telefone = findViewById(R.id.telefoneAluno)

    }

    private fun dadosAlunoDTO(snapshot: DataSnapshot) {
        val aluno = snapshot.getValue(Aluno::class.java)
        altura.setText(aluno!!.altura)
        cpf.setText(aluno!!.cpf)
        email.setText(aluno!!.email)
        nome.setText(aluno!!.name)
        idade.setText(aluno!!.idade)
        peso.setText(aluno!!.peso)
        telefone.setText(aluno!!.telefone)

    }


}
