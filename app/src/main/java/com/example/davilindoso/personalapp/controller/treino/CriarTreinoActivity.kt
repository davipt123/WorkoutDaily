package com.example.davilindoso.personalapp.controller.treino

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.davilindoso.personalapp.MainActivity
import com.example.davilindoso.personalapp.R
import com.example.davilindoso.personalapp.controller.auth.LoginActivity
import com.example.davilindoso.personalapp.model.vo.Exercicio
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class CriarTreinoActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var param: String
    private lateinit var listaExercicio: MutableList<String>
    private lateinit var listaConsultaExercicios: ArrayList<Exercicio>
    private lateinit var etSeries: EditText
    private lateinit var etRepeticoes: EditText
    private lateinit var spExercicios: Spinner
    private lateinit var exercicioSelecionado: Exercicio
    private lateinit var resumoTreino: TextView
    private lateinit var uidUsuario: String
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_treino)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        dbReference = database.reference.child("user").child(user!!.uid).child("exercicios")
        param = intent.getStringExtra("emailAluno")
        uidUsuario = intent.getStringExtra("uidAluno")
        spExercicios = findViewById(R.id.spinExercicios)
        listaExercicio = arrayListOf("Selecione...")
        consultarExercicios()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaExercicio)
        spExercicios.adapter = adapter

        resumoTreino = findViewById(R.id.resumoTreino)

        etSeries = findViewById(R.id.numeroSerie)
        etRepeticoes = findViewById(R.id.numeroRepeticao)
        onSelecionarExercicio()
    }

    private fun onSelecionarExercicio() {
        spExercicios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selecionado = spExercicios.selectedItem
                exercicioSelecionado = Exercicio()
                exercicioSelecionado.nome = selecionado.toString()
            }
        }
    }

    fun adicionarExercicio(view: View) {
        val nomeExercicio = exercicioSelecionado.nome
        val numeroSeries = etSeries.text.toString()
        val numeroRepeticoes = etRepeticoes.text.toString()
        if (validarCamposObrigatorios()) {
            resumoTreino.text = String.format(
                "%s%s",
                resumoTreino.text,
                montarStringExercicio(nomeExercicio, numeroSeries, numeroRepeticoes)
            )
            limparCampos()
        } else {
            Toast.makeText(this, "Preencher todos os campos", Toast.LENGTH_LONG).show()
        }

    }

    fun concluirTreino(view: View) {
        dbReference =
            database.reference.child("user").child(user!!.uid).child("alunos").child(uidUsuario).child("treino")
        dbReference.child("exercicios").setValue(resumoTreino.text)
        Toast.makeText(this,"Treino diário atualizado",Toast.LENGTH_LONG).show()
        //startActivity(Intent(this, MainActivity::class.java))
    }

    fun limparExercicio(view: View) {
        limparCampos()
        resumoTreino.text = ""
    }

    private fun limparCampos() {
        val charSequence:CharSequence = ""

        spExercicios.setSelection(0)
        etSeries.setText(charSequence)
        etRepeticoes.setText(charSequence)
    }

    private fun montarStringExercicio(nome: String, series: String, repeticoes: String): String {
        val descricaoExercicio =
            String.format("Exercício: %s / Séries: %s / Repetições: %s %n", nome, series, repeticoes)

        return descricaoExercicio
    }

    private fun consultarExercicios() {
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listaConsultaExercicios = arrayListOf()
                    for (a in snapshot.children) {
                        val exercicio = a.getValue(Exercicio::class.java)
                        listaConsultaExercicios.add(exercicio!!)
                    }

                    for (a in listaConsultaExercicios) {
                        listaExercicio.add(a.nome)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

    private fun validarCamposObrigatorios(): Boolean {
        val exercicioSelecionado = !spExercicios.selectedItemPosition.equals(0)
        val numeroSeriesInformado = !etSeries.text.isEmpty()
        val numeroRepeticoesInformado = !etRepeticoes.text.isEmpty()
        if (exercicioSelecionado && numeroSeriesInformado && numeroRepeticoesInformado) {
            return true
        } else {
            return false
        }
    }

}
