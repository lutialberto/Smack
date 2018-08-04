package com.example.betom.smack.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.betom.smack.R
import com.example.betom.smack.utilities.EXTRA_EMAIL
import com.example.betom.smack.utilities.EXTRA_NAME
import com.example.betom.smack.utilities.EXTRA_PASSWORD
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createUserButtonClicked(view: View) {
        val userName=userNameTextEdit.text.toString()
        val email=emailTextEdit.text.toString()
        val password=passwordTextEdit.text.toString()
        val rePassword=rePasswordTextEdit.text.toString()
        if(userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
            if(password.count()>=8)
                if(password==rePassword) {

                    //go to the next view
                    val generateAvatarIntent = Intent(this, GenerateAvatarActivity::class.java)
                    generateAvatarIntent.putExtra(EXTRA_NAME,userName)
                    generateAvatarIntent.putExtra(EXTRA_EMAIL,email)
                    generateAvatarIntent.putExtra(EXTRA_PASSWORD,password)
                    startActivity(generateAvatarIntent)
                    finish()
                } else
                    Toast.makeText(this,"the passwords are not the same!!",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"the password must be at least 8 characters long",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this,"Make sure user name, email and password are filled in",Toast.LENGTH_LONG).show()
    }

    fun isValidEmail(email:String):Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidUserName(name:String):Boolean{
        return name.isNotEmpty()
    }

    fun isValidPassword(password:String,rePassword:String):Boolean{
        return password.isNotEmpty() && password==rePassword && password.length>=8 && password.length<=20
    }
}
