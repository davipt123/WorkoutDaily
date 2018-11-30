package com.example.davilindoso.personalapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class ExerciciosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.exercicios_layout, container, false)
        val mListView: ListView = view.findViewById(R.id.listaMenuExercicio)
        val values = arrayListOf("Cadastrar Exercício", "Exercícios")
        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, values)
        mListView.adapter = adapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> startActivity(Intent(context, CadastrarExercicioActivity::class.java))
                else -> Toast.makeText(context, "ta me zoando?", Toast.LENGTH_SHORT).show()
            }


        }
        return view
    }

}