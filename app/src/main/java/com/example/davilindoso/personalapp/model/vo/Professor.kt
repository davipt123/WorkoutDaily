package com.example.davilindoso.personalapp.model.vo

data class Professor(
    var nome: String,
    var lastName: String,
    var email: String,
    var alunos: HashMap<String, HashMap<String, String>>
)
