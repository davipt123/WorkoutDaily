package com.example.davilindoso.personalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.w3c.dom.Text
import java.nio.file.Files

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
    private lateinit var emails: ArrayList<String>
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
                var selecionado = spExercicios.selectedItem
                exercicioSelecionado = Exercicio()
                exercicioSelecionado.nome = selecionado.toString()
            }
        }
    }


    fun adicionarExercicio(view: View) {
        val nomeExercicio = exercicioSelecionado.nome
        val numeroSeries = etSeries.text.toString()
        val numeroRepeticoes = etRepeticoes.text.toString()

        resumoTreino.text = String.format("%s%s",resumoTreino.text,montarStringExercicio(nomeExercicio, numeroSeries, numeroRepeticoes))
    }

    fun concluirTreino(view: View){
        dbReference = database.reference.child("user").child(user!!.uid).child("alunos").child(uidUsuario).child("treino")
        dbReference.child("exercicios").setValue(resumoTreino.text)
    }

    fun limparExercicio(view: View){
        resumoTreino.text = ""
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

                    for (a in listaConsultaExercicios){
                        listaExercicio.add(a.nome)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        })
    }

}
