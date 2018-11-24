package com.example.davilindoso.personalapp

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar_alunos)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        var user: FirebaseUser? = auth.currentUser
        dbReference = database.reference.child("user").child(user!!.uid).child("alunos")
        listaAlunos = mutableListOf()
        val mListView: ListView = findViewById(R.id.listaAlunosCadastrados)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, retornarEmailAlunos())
        mListView.adapter = adapter
    }

    private fun retornarEmailAlunos(): MutableList<String> {
        var listaEmailAlunos: MutableList<String> = mutableListOf()
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        val aluno = a.getValue(Aluno::class.java)
//                        listaAlunos.add(aluno!!)
                        var emailAluno = getEmailAlunos(aluno!!)
                        listaEmailAlunos.add(emailAluno)
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
