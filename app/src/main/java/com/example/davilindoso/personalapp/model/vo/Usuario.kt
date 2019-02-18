package com.example.davilindoso.personalapp.model.vo

data class Usuario(
    var email: String = "",
    var uid: String = ""
) {
    constructor() : this("", "")
}