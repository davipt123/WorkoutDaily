package com.example.davilindoso.personalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class CadastrarExercicioActivity : AppCompatActivity() {

    var exercicio: Exercicio = Exercicio()
    lateinit var v: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_exercicio)
        val context = this
        onSelectParteCorpo()
        v = findViewById(com.example.davilindoso.personalapp.R.id.testView)


    }

    private fun onSelectParteCorpo() {
        val values = arrayListOf("Selecione...", "Braço", "Perna", "Peito", "Costas", "Ombro")
        val spParteCorpo: Spinner = findViewById(R.id.spinParteCorpo)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values)
        spParteCorpo.adapter = adapter

        spParteCorpo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                exercicio.parteDoCorpo = ""
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        exercicio.parteDoCorpo = ""
                        v.text = exercicio.parteDoCorpo
                    }
                    1 -> {
                        exercicio.parteDoCorpo = "Braço"
                        v.text = exercicio.parteDoCorpo
                    }
                    2 -> {
                        exercicio.parteDoCorpo = "Perna"
                        v.text = exercicio.parteDoCorpo
                    }
                    3 -> {
                        exercicio.parteDoCorpo = "Peito"
                        v.text = exercicio.parteDoCorpo
                    }
                    4 -> {
                        exercicio.parteDoCorpo = "Costas"
                        v.text = exercicio.parteDoCorpo
                    }
                    5 -> {
                        exercicio.parteDoCorpo = "Ombro"
                        v.text = exercicio.parteDoCorpo
                    }
                }
            }
        }
    }
}
