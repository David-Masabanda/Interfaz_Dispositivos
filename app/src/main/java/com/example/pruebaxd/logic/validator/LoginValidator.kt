package com.example.pruebaxd.logic.validator


import com.example.pruebaxd.data.entities.LoginUser

class LoginValidator {
    fun checkLogin(name:String ,password:String):Boolean{
        val admin= LoginUser()
        return(admin.name==name
                && admin.pass==password)
    }

}