package com.example.betom.smack

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createUserButtonClicked(view: View) {
        val generateAvatarIntent=Intent(this,GenerateAvatarActivity::class.java)
        
        startActivity(generateAvatarIntent)
    }
}
