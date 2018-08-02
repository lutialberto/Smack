package com.example.betom.smack.controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.betom.smack.R
import com.example.betom.smack.services.AuthService
import com.example.betom.smack.services.UserDataService
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
    }

    fun generateBackButtonClicked(view: View){
        val random=Random()
        val r=random.nextInt(255)
        val g=random.nextInt(255)
        val b=random.nextInt(255)

        avatarUserImageClicked.setBackgroundColor(Color.rgb(r,g,b))

        val savedR=r.toDouble()/255
        val savedG=g.toDouble()/255
        val savedB=b.toDouble()/255

        avatarColor="[$savedR,$savedG,$savedB,1]"
    }

    fun saveAvatarButtonClicked(view: View) {
        //send data to the DB
        val email=intent.getStringExtra(EXTRA_EMAIL)
        val userName=intent.getStringExtra(EXTRA_NAME)
        val password=intent.getStringExtra(EXTRA_PASSWORD)

        AuthService.registerUser(this, email, password) { registerSuccess ->
            if(registerSuccess){
                AuthService.loginUser(this,email,password){ loginSuccess ->
                    if(loginSuccess){
                        AuthService.createUser(this,userName,email,userAvatar,avatarColor){ createSuccess ->
                            println(UserDataService.avatarColor)
                            println(UserDataService.avatarName)
                            println(UserDataService.name)
                            finish()
                        }
                    }
                }
            }

        }
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
        avatarUserImageClicked.setImageResource(resourceId)

    }
}
