package com.example.betom.smack.controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService
import com.example.betom.smack.services.UserDataService
import com.example.betom.smack.utilities.BROADCAST_USER_DATA_CHANGE
import com.example.betom.smack.utilities.EXTRA_EMAIL
import com.example.betom.smack.utilities.EXTRA_NAME
import com.example.betom.smack.utilities.EXTRA_PASSWORD
import kotlinx.android.synthetic.main.activity_generate_avatar.*
import java.util.*

class GenerateAvatarActivity : AppCompatActivity() {

    var userAvatar= "profileDefault"
    var avatarColor="[0.5,0.5,0.5,1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_avatar)
        createSpinner.visibility=View.INVISIBLE
    }

    fun generateBackButtonClicked(view: View){
        val random=Random()
        val r=random.nextInt(255)
        val g=random.nextInt(255)
        val b=random.nextInt(255)

        avatarUserImage.setBackgroundColor(Color.rgb(r,g,b))

        val savedR=r.toDouble()/255
        val savedG=g.toDouble()/255
        val savedB=b.toDouble()/255

        avatarColor="[$savedR,$savedG,$savedB,1]"
    }

    fun saveAvatarButtonClicked(view: View) {
        //send data to the DB
        enableSpinner(true)

        val email=intent.getStringExtra(EXTRA_EMAIL)
        val userName=intent.getStringExtra(EXTRA_NAME)
        val password=intent.getStringExtra(EXTRA_PASSWORD)

        AuthService.registerUser(email, password) { registerSuccess ->
            if(registerSuccess){
                AuthService.loginUser(email,password){ loginSuccess ->
                    if(loginSuccess){
                        AuthService.createUser(userName,email,userAvatar,avatarColor){ createSuccess ->
                            if(createSuccess) {

                                val userDataChange=Intent(BROADCAST_USER_DATA_CHANGE)
                                LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)

                                enableSpinner(false)
                                finish()
                            } else {
                                errorToast()
                            }
                        }
                    } else {
                        errorToast()
                    }
                }
            } else {
                errorToast()
            }

        }
    }

    fun errorToast(){
        Toast.makeText(this,"Something went wrong, please try again.",Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun avartarUserImageClicked(view: View) {
        val random=Random()
        val color=random.nextInt(2)
        val avatar=random.nextInt(28)

        if(color==0){
            userAvatar="light$avatar"
        } else {
            userAvatar="dark$avatar"
        }
        val resourceId=resources.getIdentifier(userAvatar,"drawable",packageName)
        avatarUserImage.setImageResource(resourceId)
    }

    fun enableSpinner(enable:Boolean){
        if(enable)
            createSpinner.visibility=View.VISIBLE
        else
            createSpinner.visibility=View.INVISIBLE

        generateBackgroundColorButton.isEnabled=!enable
        saveAvatarButton.isEnabled=!enable
        avatarUserImage.isEnabled=!enable
    }
}
