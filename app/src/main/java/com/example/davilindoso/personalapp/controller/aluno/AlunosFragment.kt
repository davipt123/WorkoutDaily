package com.example.davilindoso.personalapp.controller.aluno

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.davilindoso.personalapp.R
import com.example.davilindoso.personalapp.model.vo.Aluno
import com.example.davilindoso.personalapp.model.vo.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class AlunosFragment : Fragment() {

    private lateinit var listaEmailAlunos: ArrayList<String>
    private lateinit var listaUidAlunos: ArrayList<String>
    private lateinit var progressBar: ProgressBar
    private lateinit var listaUsuarios: ArrayList<Usuario>
    private lateinit var listaUidUsuarios: ArrayList<String>
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var viewMain: View
    private lateinit var it: Intent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMain = LayoutInflater.from(container?.context).inflate(R.layout.alunos_layout, container, false)
        val mListView: ListView = viewMain.findViewById(R.id.listaMenuAluno)

        val values = arrayListOf("Cadastrar Aluno", "Alunos")
        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, values)
        mListView.adapter = adapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> startActivity(Intent(context, CadastrarAlunoActivity::class.java))
                1 -> iniciarAtividadeListaAlunos()
                2 -> Toast.makeText(context, "excluindo", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, "ta me zoando?", Toast.LENGTH_SHORT).show()
            }
        }

        return viewMain
    }

    private fun iniciarAtividadeListaAlunos() {
        it = Intent(context, ConsultarAlunosActivity::class.java)
        retornarEmailAlunos()
    }

    private fun retornarEmailAlunos(): ArrayList<String> {
        listaEmailAlunos = ArrayList()
        listaUidAlunos = ArrayList()
        listaUsuarios = ArrayList()
        listaUidUsuarios = ArrayList()
        var count = 0

        progressBar = viewMain.findViewById(R.id.progressBarAlunos)
        progressBar.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        database = FirebaseDatabase.getInstance()
        dbReference = database.reference.child("user").child(user!!.uid).child("alunos")

        dbReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listaEmailAlunos.clear()
                    listaUidAlunos.clear()
                    for (a in snapshot.children) {
                        count++;
                        val aluno = a.getValue(Aluno::class.java)
                        val emailAluno = getEmailAlunos(aluno!!)
                        listaEmailAlunos.add(emailAluno)
                        listaUidAlunos.add(a.key.toString())
                        val infoUsuario = Usuario()
                        infoUsuario.email = emailAluno
                        infoUsuario.uid = a.key.toString()
                        listaUsuarios.add(infoUsuario)
                        listaUidUsuarios.add(infoUsuario.uid)

                        if (count >= snapshot.childrenCount) {
                            //stop progress bar here
                            progressBar.visibility = View.INVISIBLE
                            it.putStringArrayListExtra("listaAlunos", listaEmailAlunos)
                            it.putStringArrayListExtra("listaUid", listaUidUsuarios)
                            startActivity(it)
                        }
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
