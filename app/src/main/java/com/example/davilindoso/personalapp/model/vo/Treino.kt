package com.example.davilindoso.personalapp.model.vo

import com.example.davilindoso.personalapp.model.vo.Exercicio

data class Treino(
    var listaExercicios: List<Exercicio>
) {
    constructor() : this(mutableListOf())
}