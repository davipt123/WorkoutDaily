package com.example.davilindoso.personalapp.controller.exercicio

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.davilindoso.personalapp.MainActivity
import com.example.davilindoso.personalapp.R
import com.example.davilindoso.personalapp.model.enums.Corpo
import com.example.davilindoso.personalapp.model.enums.Dificuldade
import com.example.davilindoso.personalapp.model.vo.Exercicio
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CadastrarExercicioActivity : AppCompatActivity() {

    var exercicio: Exercicio =
        Exercicio()
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_exercicio)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        onSelectParteCorpo()
        onSelectNivelExercicio()
    }

    fun registerExercicio(view: View) {
        cadastrarNovoExercicio()
    }

    private fun cadastrarNovoExercicio(){
        val txtNome: EditText = findViewById(R.id.txtNomeExercicio)
        exercicio.nome = txtNome.text.toString()
        val nomeExercicio = exercicio.nome
        val dificuldadeExercicio = exercicio.dificuldade
        val parteCorpoExercicio = exercicio.parteDoCorpo

        val user: FirebaseUser? = auth.currentUser
        dbReference = database.reference.child("user").child(user!!.uid).child("exercicios").child(nomeExercicio)

        dbReference.child("nome").setValue(nomeExercicio)
        dbReference.child("dificuldade").setValue(dificuldadeExercicio)
        dbReference.child("parte").setValue(parteCorpoExercicio)
        Toast.makeText(this,"Exercício Cadastrado",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))

    }

    private fun onSelectNivelExercicio() {
        val values = arrayListOf("Selecione...", "Fácil", "Moderado", "Difícil")
        val spDificuldade: Spinner = findViewById(R.id.spinNivelExercicio)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values)
        spDificuldade.adapter = adapter
        spDificuldade.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                exercicio.dificuldade = ""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setDificuldadeExercicio(position)
            }
        }
    }

    private fun onSelectParteCorpo() {
        val values = arrayListOf("Selecione...")
        Corpo.values().forEach {
            values.add(it.nome)
        }

        val spParteCorpo: Spinner = findViewById(R.id.spinParteCorpo)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values)
        spParteCorpo.adapter = adapter
        spParteCorpo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                exercicio.parteDoCorpo = ""
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setParteDoCorpo(position)
            }
        }
    }

    private fun setParteDoCorpo(selecionado: Int) {
        when (selecionado) {
            0 -> {
                exercicio.parteDoCorpo = ""
            }
            1 -> {
                exercicio.parteDoCorpo = Corpo.BRACO.nome
            }
            2 -> {
                exercicio.parteDoCorpo = Corpo.COSTAS.nome
            }
            3 -> {
                exercicio.parteDoCorpo = Corpo.OMBRO.nome
            }
            4 -> {
                exercicio.parteDoCorpo = Corpo.PEITO.nome
            }
            5 -> {
                exercicio.parteDoCorpo = Corpo.PERNA.nome
            }
        }
    }


    private fun setDificuldadeExercicio(selecionado: Int) {
        when (selecionado) {
            0 -> {
                exercicio.dificuldade = ""
            }
            1 -> {
                exercicio.dificuldade = Dificuldade.FACIL.nome
            }
            2 -> {
                exercicio.dificuldade = Dificuldade.MODERADO.nome
            }
            3 -> {
                exercicio.dificuldade = Dificuldade.DIFICIL.nome
            }
        }
    }
}
