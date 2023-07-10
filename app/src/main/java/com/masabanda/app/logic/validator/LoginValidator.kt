package com.masabanda.app.logic.validator

import com.masabanda.app.data.user.LoginUser

class LoginValidator {

    fun checkLogin(name:String ,password:String):Boolean{
        val admin= LoginUser()
        return(admin.user==name && admin.key==password)
    }


}