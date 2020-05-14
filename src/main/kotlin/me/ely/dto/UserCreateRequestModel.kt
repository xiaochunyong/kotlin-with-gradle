package me.ely.dto

import me.ely.NoArgCons

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-01
 */
@NoArgCons
data class UserCreateRequestModel(
        val username: String,
        val password: String,
        val nickname: String,
        val mobile: String
)