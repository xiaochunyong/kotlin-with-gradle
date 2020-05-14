package me.ely.dto

import me.ely.NoArgCons

@NoArgCons
data class UserSearchRequestModel (
        val username: String?
) : BaseSearchRequestModel()