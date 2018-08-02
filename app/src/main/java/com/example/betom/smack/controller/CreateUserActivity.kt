package com.example.betom.smack.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createUserButtonClicked(view: View) {
        AuthService.registerUser(this,"asd@anda.com","anda123"){

        }

//        val generateAvatarIntent=Intent(this, GenerateAvatarActivity::class.java)
//        startActivity(generateAvatarIntent)
    }
}
