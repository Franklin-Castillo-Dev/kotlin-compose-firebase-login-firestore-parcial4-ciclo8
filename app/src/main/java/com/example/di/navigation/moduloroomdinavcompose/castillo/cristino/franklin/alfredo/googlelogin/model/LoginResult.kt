package com.example.di.navigation.moduloroomdinavcompose.castillo.cristino.franklin.alfredo.googlelogin.model


data class LoginResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
