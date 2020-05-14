package me.ely.dto

import me.ely.NoArgCons
import java.util.*

@NoArgCons
data class UserRegisterRequest (
    val mobile: String,
    val name: String,
    val age: Int,
    val height: Double,
    val birthday: Date?,
    val captcha: String
)