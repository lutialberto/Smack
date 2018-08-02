package com.example.betom.smack.services

import android.graphics.Color
import android.util.Log
import java.util.*

object UserDataService {

    var id=""
    var avatarColor=""
    var avatarName=""
    var name=""
    var email=""


    fun logout(){
        id=""
        avatarColor=""
        avatarName=""
        name=""
        email=""
        AuthService.userEmail=""
        AuthService.isLoggedIn=false
        AuthService.authToken=""
    }

    fun returnAvatarColor(components:String): Int {
        val strippedColor=components
                .replace("[","")
                .replace("]","")
                .replace(","," ")
        var r=0
        var g=0
        var b=0

        println(        strippedColor)
        val scanner1=Scanner(strippedColor)
        println("        |${scanner1.next()}|")
        println("        |${scanner1.next()}|")
        println("        |${scanner1.next()}|")

        val scanner=Scanner(strippedColor)
        if(scanner.hasNext()){
            r=(scanner.next().toDouble()*255).toInt()
            g=(scanner.next().toDouble()*255).toInt()
            b=(scanner.next().toDouble()*255).toInt()
        }

        return Color.rgb(r,g,b)
    }
}