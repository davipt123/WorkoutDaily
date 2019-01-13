package com.example.davilindoso.personalapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ConsultarAlunosActivity : AppCompatActivity() {
    private lateinit var listaAlunos: MutableList<Aluno>
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var listaEmailAlunos: ArrayList<String>
    private lateinit var listaUidAlunos: ArrayList<String>
    private lateinit var infoUsuarioSel: Usuario
    private lateinit var listaUsuarios: ArrayList<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_alunos)
        setTitle(R.string.alunos)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        dbReference = database.reference.child("user").child(user!!.uid).child("alunos")
        listaAlunos = mutableListOf()
        val mListView: ListView = findViewById(R.id.listaAlunosCadastrados)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, retornarEmailAlunos())
        mListView.adapter = adapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            val it = Intent(this, PerfilAlunoActivity::class.java)
            val valorLinha = mListView.adapter.getItem(position)
            infoUsuarioSel = Usuario()
            listaUsuarios.forEach {
                if (it.email.equals(valorLinha.toString())) {
                    infoUsuarioSel.email = it.email
                    infoUsuarioSel.uid = it.uid
                }
            }
            it.putExtra("valorLinha", valorLinha.toString())
            it.putExtra("uidUsuario", infoUsuarioSel.uid)
            startActivity(it)


        }
    }

    private fun retornarEmailAlunos(): MutableList<String> {
        listaEmailAlunos = ArrayList()
        listaUidAlunos = ArrayList()
        listaUsuarios = ArrayList()
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        val aluno = a.getValue(Aluno::class.java)
                        val emailAluno = getEmailAlunos(aluno!!)
                        listaEmailAlunos.add(emailAluno)
                        listaUidAlunos.add(a.key.toString())
                        var infoUsuario = Usuario()
                        infoUsuario.email = emailAluno
                        infoUsuario.uid = a.key.toString()
                        listaUsuarios.add(infoUsuario)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
        return listaEmailAlunos
    }

    private fun getEmailAlunos(aluno: Aluno): String {
        return aluno.email
    }
}
