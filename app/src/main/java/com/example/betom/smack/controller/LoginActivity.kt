package com.example.betom.smack.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginButtonClicked(view: View){
        val email=loginEmailText.text.toString()
        val password=loginPasswordText.text.toString()

        AuthService.loginUser(this,email,password){loginSuccess ->
            if(loginSuccess){
                AuthService.findUserByEmail(this) {findSuccess ->
                    if(findSuccess){
                        finish()
                    }
                }
            }
        }
    }

    fun loginSignUpButtonClicked(view: View) {
        val signUpIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(signUpIntent)
        finish()
    }
}
