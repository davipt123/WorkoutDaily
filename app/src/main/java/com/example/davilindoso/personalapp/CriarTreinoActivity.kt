package com.example.davilindoso.personalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.nio.file.Files

class CriarTreinoActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var param: String
    private lateinit var listaExercicio: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_treino)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        dbReference = database.reference.child("exercicio")
        param = intent.getStringExtra("emailAluno")

        val spExercicios: Spinner = findViewById(R.id.spinExercicios)
        listaExercicio = arrayListOf("Selecione...")
        consultarExercicios()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaExercicio)
        spExercicios.adapter = adapter

    }

    fun adicionarExercicio(view: View) {
       // montarStringExercicio("",)
    }

    private fun montarStringExercicio(nome: String, series: Int, repeticoes: Int) {

    }

    private fun consultarExercicios() {
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (a in snapshot.children) {
                        val exercicio = a.getValue(Exercicio::class.java)
                        listaExercicio.add(exercicio!!.nome)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }
}
