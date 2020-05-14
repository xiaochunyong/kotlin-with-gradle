package me.ely.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(
        var username: String,
        var password: String,
        var nickname: String,
        var mobile: String,
        var status: String
) : BaseEntity()