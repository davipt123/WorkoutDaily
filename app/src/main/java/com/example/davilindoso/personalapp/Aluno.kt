package com.example.davilindoso.personalapp

data class Aluno(
    var name: String = "",
    var idade: String = "",
    var altura: String = "",
    var peso: String = "",
    var cpf: String = "",
    var telefone: String = "",
    var email: String = ""
) {
    constructor() : this("", "", "", "", "", "", "")
}