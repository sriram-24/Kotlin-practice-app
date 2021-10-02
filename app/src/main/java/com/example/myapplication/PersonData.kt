package com.example.myapplication

import java.io.Serializable

data class PersonData (
    val firstName : String,
    val lastName : String,
    val birthDate : Int,
    val country : String
    ):Serializable