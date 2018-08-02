package com.example.betom.smack.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createUserButtonClicked(view: View) {
        val userName=userNameText.text.toString()
        val email=emailText.text.toString()
        val password=passwordText.text.toString()
        val rePassword=rePasswordText.text.toString()
/*
        if(password.count()>=8)
            if(password==rePassword) {
*/                //send data to the DB
                AuthService.registerUser(this, email, password) {registerSuccess ->
                    if(registerSuccess){
                        AuthService.loginUser(this,email,password){loginSuccess ->
                            if(loginSuccess){
                                println(AuthService.authToken)
                                println(AuthService.userEmail)
                            }
                        }
                    }

                }

                //go to the next view
/*                val generateAvatarIntent = Intent(this, GenerateAvatarActivity::class.java)
                startActivity(generateAvatarIntent)
            } else
                Toast.makeText(this,"the passwords are not the same!!",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,"the password must be at least 8 characters long",Toast.LENGTH_SHORT).show()
*/    }
}
