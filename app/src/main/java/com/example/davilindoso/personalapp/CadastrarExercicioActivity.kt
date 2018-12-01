package com.example.davilindoso.personalapp

import android.graphics.Region
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


        var values = arrayListOf("Selecione...")
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
                v.text = exercicio.parteDoCorpo
            }
            1 -> {
                exercicio.parteDoCorpo = Corpo.BRACO.nome
                v.text = Corpo.BRACO.nome
            }
            2 -> {
                exercicio.parteDoCorpo = Corpo.COSTAS.nome
                v.text = Corpo.COSTAS.nome
            }
            3 -> {
                exercicio.parteDoCorpo = Corpo.OMBRO.nome
                v.text = Corpo.OMBRO.nome
            }
            4 -> {
                exercicio.parteDoCorpo = Corpo.PEITO.nome
                v.text = Corpo.PEITO.nome
            }
            5 -> {
                exercicio.parteDoCorpo = Corpo.PERNA.nome
                v.text = Corpo.PERNA.nome
            }
        }
    }
}
