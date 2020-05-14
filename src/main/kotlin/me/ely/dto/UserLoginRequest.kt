package me.ely.dto

import me.ely.NoArgCons

@NoArgCons
data class UserLoginRequest (
    val mobile: String,
    val captcha: String
)