package com.example.davilindoso.personalapp

data class Treino(
    var listaExercicios: List<Exercicio>
) {
    constructor() : this(mutableListOf())
}