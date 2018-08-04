package com.example.betom.smack.controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility=View.INVISIBLE
    }

    fun loginLoginButtonClicked(view: View){
        enableSpinner(true)
        val email=loginEmailTextEdit.text.toString().trim()
        val password=loginPasswordTextEdit.text.toString().trim()

        hideKeyboard()

        if(email.isNotEmpty() && password.isNotEmpty()){
            AuthService.loginUser(email,password){loginSuccess ->
                if(loginSuccess){
                    AuthService.findUserByEmail(this) {findSuccess ->
                        if(findSuccess){
                            enableSpinner(false)
                            finish()
                        } else {
                            Toast.makeText(this,"Something went wrong, please try again.", Toast.LENGTH_LONG).show()
                            enableSpinner(false)
                        }
                    }
                } else {
                    Toast.makeText(this,"Something went wrong, please try again.", Toast.LENGTH_LONG).show()
                    enableSpinner(false)
                }
            }
        } else {
            Toast.makeText(this,"Please fill both email and password",Toast.LENGTH_LONG).show()
        }
    }

    fun loginSignUpButtonClicked(view: View) {
        val signUpIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(signUpIntent)
        finish()
    }

    fun errorToast(){
        Toast.makeText(this,"Something went wrong, please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable:Boolean){
        if(enable)
            loginSpinner.visibility=View.VISIBLE
        else
            loginSpinner.visibility=View.INVISIBLE

        loginLoginButton.isEnabled=!enable
        loginSignUpButton.isEnabled=!enable
    }

    fun hideKeyboard() {
        val inputManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }
    }
}
