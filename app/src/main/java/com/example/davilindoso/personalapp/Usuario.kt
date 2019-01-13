package com.example.davilindoso.personalapp

data class Usuario(
    var email: String = "",
    var uid: String = ""
) {
    constructor() : this("", "")
}