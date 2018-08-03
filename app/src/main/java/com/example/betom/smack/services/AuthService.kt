package com.example.betom.smack.services

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.example.betom.smack.controller.App
import com.example.betom.smack.utilities.*
import org.json.JSONException


object AuthService {

//    var isLoggedIn=false
//    var userEmail=""
//    var authToken=""

    fun registerUser(context: Context,email:String,password:String,complete: (Boolean) -> Unit) {

        val jsonBody= JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody=jsonBody.toString()

        val registerRequest= object  : StringRequest(Method.POST, URL_REGISTER,
                Response.Listener { response ->
                    println(response)
                    complete(true)
                },
                Response.ErrorListener { error ->
                    Log.d("ERROR","Could not register user $error")
                    complete(false)
                }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        registerRequest.setRetryPolicy(DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))
        App.sharePreferences.requestQueue.add(registerRequest)
    }

    fun loginUser(context: Context,email: String,password: String,complete: (Boolean) -> Unit) {
        val jsonBody= JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody=jsonBody.toString()

        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN,null,
                Response.Listener { response ->
                    try {
                        App.sharePreferences.userEmail =response.getString("user")
                        App.sharePreferences.authToken=response.getString("token")
                        App.sharePreferences.isLoggedIn=true
                        complete(true)
                    }catch (e: JSONException){
                        Log.d("JSON","EXC: ${e.localizedMessage}")
                        complete(false)
                    }

                },Response.ErrorListener { error ->

                    Log.d("ERROR","Could not log user $error")
                    complete(false)

                }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }
        App.sharePreferences.requestQueue.add(loginRequest)
    }

    fun createUser(context: Context,name: String,email: String,avatarName:String,avatarColor:String,complete: (Boolean) -> Unit) {
        val jsonBody= JSONObject()
        jsonBody.put("name",name)
        jsonBody.put("email",email)
        jsonBody.put("avatarName",avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody=jsonBody.toString()

        val createRequest= object : JsonObjectRequest(Method.POST, URL_CREATE_USER,null,
                Response.Listener { response ->
                    try {
                        UserDataService.name=response.getString("name")
                        UserDataService.email=response.getString("email")
                        UserDataService.avatarName=response.getString("avatarName")
                        UserDataService.avatarColor=response.getString("avatarColor")
                        UserDataService.id=response.getString("_id")
                        complete(true)
                    }catch (e: JSONException){
                        Log.d("JSON","EXC: ${e.localizedMessage}")
                        complete(false)
                    }

                },Response.ErrorListener { error ->
                    Log.d("ERROR","Could not add user: $error")
                    complete(false)
                }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers.put("Authorization","Bearer ${App.sharePreferences.authToken}")
                return headers
            }
        }

        App.sharePreferences.requestQueue.add(createRequest)
    }

    fun findUserByEmail(context: Context,complete: (Boolean) -> Unit){
        val findUserRequest= object : JsonObjectRequest(Method.GET, "$URL_GET_USER_BY_EMAIL${App.sharePreferences.userEmail}",null,
                Response.Listener { response ->
                    try {
                        UserDataService.name=response.getString("name")
                        UserDataService.email=response.getString("email")
                        UserDataService.avatarName=response.getString("avatarName")
                        UserDataService.avatarColor=response.getString("avatarColor")
                        UserDataService.id=response.getString("_id")

                        val userDataChange=Intent(BROADCAST_USER_DATA_CHANGE)
                        LocalBroadcastManager.getInstance(context).sendBroadcast(userDataChange)
                        complete(true)
                    }catch (e:JSONException){
                        Log.d("JSON","EXC: ${e.localizedMessage}")
                        complete(false)
                    }
                },Response.ErrorListener { error ->
                    Log.d("ERROR","Could not find user: $error")
                    complete(false)
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String,String>()
                headers.put("Authorization","Bearer ${App.sharePreferences.authToken}")
                return headers
            }
        }

        App.sharePreferences.requestQueue.add(findUserRequest)
    }




}