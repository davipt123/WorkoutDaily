package com.example.davilindoso.personalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class CadastrarExercicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_exercicio)
        val context = this
        val values = arrayListOf("Braço", "Perna")
        val spParteCorpo: Spinner = findViewById(R.id.spinParteCorpo)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values)
        spParteCorpo.adapter = adapter

        spParteCorpo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> Toast.makeText(context, "selecionou braço",Toast.LENGTH_LONG).show()
                }

            }

        }


    }
}
