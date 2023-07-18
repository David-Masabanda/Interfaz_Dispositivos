package com.example.pruebas.logic

import com.example.pruebas.data.LoginUser


class LoginValidator {
    fun checkLogin(name: String, password: String): Boolean {
        val admin = LoginUser()
        return (admin.name == name && admin.pass == password)
    }
}