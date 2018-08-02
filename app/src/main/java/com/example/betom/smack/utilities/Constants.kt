package com.example.betom.smack.utilities

/*url for DB interaction*/
//const val BASE_URL = "http://10.0.2.2:3005/v1/" //mongod and robo mongo
const val BASE_URL = "https://chattycahtb.herokuapp.com/v1/" // heroku and mlab

const val SOCKET_URL = "https://chattycahtb.herokuapp.com/"

const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_GET_USER_BY_EMAIL = "${BASE_URL}/user/byEmail/"

/*information from Create User to GenerateAvatar*/
const val EXTRA_NAME = "name"
const val EXTRA_EMAIL = "email"
const val EXTRA_PASSWORD = "password"

/*broadcasting*/
const val BROADCAST_USER_DATA_CHANGE= "BROADCAST_USER_DATA_CHANGE"