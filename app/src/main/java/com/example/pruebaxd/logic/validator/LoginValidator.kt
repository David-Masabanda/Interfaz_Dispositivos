package com.example.pruebaxd.logic.validator

import com.example.pruebaxd.logic.entities.LoginUser

class LoginValidator {

    fun checkLogin(name:String, password:String): Boolean {
        val admin = LoginUser()
        return (admin.name == name
                && admin.pass == password)
    }
}