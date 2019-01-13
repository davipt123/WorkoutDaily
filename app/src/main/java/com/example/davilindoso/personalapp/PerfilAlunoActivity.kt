package com.example.davilindoso.personalapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

private lateinit var altura: TextView
private lateinit var cpf: TextView
private lateinit var email: TextView
private lateinit var idade: TextView
private lateinit var nome: TextView
private lateinit var peso: TextView
private lateinit var telefone: TextView
private lateinit var param: String
private lateinit var uid: String


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
                        if (a.getValue(Aluno::class.java)!!.email == (param))
                            dadosAlunoDTO(a)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    fun abrirActivityTreino(view: View) {
        val it = Intent(this, CriarTreinoActivity::class.java)
        it.putExtra("emailAluno", param)
        it.putExtra("uidAluno", uid)
        startActivity(it);
    }

    private fun inicializarComponentes() {
        param = intent.getStringExtra("valorLinha")
        uid = intent.getStringExtra("uidUsuario")
        altura = findViewById(R.id.alturaAluno)
        cpf = findViewById(R.id.cpfAluno)
        email = findViewById(R.id.emailAluno)
        nome = findViewById(R.id.nomeAluno)
        idade = findViewById(R.id.idadeAluno)
        peso = findViewById(R.id.pesoAluno)
        telefone = findViewById(R.id.telefoneAluno)
        recuperarPictureUser()

    }

    private fun recuperarPictureUser() {
        val myImageView: ImageView = findViewById(R.id.pictureUsuario)
        myImageView.setImageResource(R.drawable.picture_user)
    }

    private fun dadosAlunoDTO(snapshot: DataSnapshot) {
        val aluno = snapshot.getValue(Aluno::class.java)
        if (aluno != null) {
            altura.text = aluno.altura
            cpf.text = aluno.cpf
            email.text = aluno.email
            nome.text = aluno.name
            idade.text = aluno.idade
            peso.text = aluno.peso
            telefone.text = aluno.telefone
        }

    }


}
