package com.example.davilindoso.personalapp.model.vo

data class Aluno(
    var name: String = "",
    var idade: String = "",
    var altura: String = "",
    var peso: String = "",
    var cpf: String = "",
    var telefone: String = "",
    var email: String = "",
    var perfilProfessor: String = ""

)
{
    constructor() : this("", "", "", "", "", "", "", "")
}