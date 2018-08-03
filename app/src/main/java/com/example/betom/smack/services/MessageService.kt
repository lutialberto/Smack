package com.example.betom.smack.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.betom.smack.controller.App
import com.example.betom.smack.model.Channel
import com.example.betom.smack.model.Message
import com.example.betom.smack.utilities.URL_GET_CHANNELS
import org.json.JSONException

object MessageService {
    val channels=ArrayList<Channel>()
    val messages=ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit){
        val channelsRequest=object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS,null,
                Response.Listener { response ->
                    try {
                        for (x in 0 until response.length()){
                            val channel=response.getJSONObject(x)
                            val name=channel.getString("name")
                            val description=channel.getString("description")
                            val id=channel.getString("_id")

                            val newChannel=Channel(name,description, id)
                            this.channels.add(newChannel)
                        }
                        complete(true)
                    }catch (e:JSONException){
                        Log.d("JSON","EXC:${e.localizedMessage}")
                        complete(false)
                    }
                },Response.ErrorListener { error ->
                    Log.d("ERROR","Could not retrieve channels")
                    complete(false)
                }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers.put("Authorization","Bearer ${App.sharePreferences.authToken}")
                return headers
            }
        }
        App.sharePreferences.requestQueue.add(channelsRequest)
    }
}