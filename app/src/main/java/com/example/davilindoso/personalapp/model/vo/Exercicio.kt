package com.example.davilindoso.personalapp.model.vo

data class Exercicio(
    var parteDoCorpo: String = "",
    var nome: String = "",
    var dificuldade: String = ""

) {
    constructor() : this("", "", "")
}