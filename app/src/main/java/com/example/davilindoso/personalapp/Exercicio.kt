package com.example.davilindoso.personalapp

data class Exercicio(
    var parteDoCorpo: String = "",
    var nome: String = ""

) {
    constructor() : this("", "")
}