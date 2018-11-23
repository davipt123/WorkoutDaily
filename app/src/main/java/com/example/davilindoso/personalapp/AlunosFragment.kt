package com.example.davilindoso.personalapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class AlunosFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.alunos_layout, container, false)
        val mListView: ListView = view.findViewById(R.id.listaMenuAluno)

        val values = arrayListOf("Cadastrar Aluno")
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, values)
        mListView.adapter = adapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0 ->  startActivity(Intent(context, CadastrarAlunoActivity::class.java))
                1 -> Toast.makeText(context,"editando",Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(context,"excluindo",Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context,"ta me zoando?",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}