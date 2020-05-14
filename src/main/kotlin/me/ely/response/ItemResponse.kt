package me.ely.response

data class ItemResponse<T>(
        val code: Int = 0,
        val message: String = "",
        val data: T? = null
) {

    constructor(data: T?) : this(0, "success", data)

    /**
     * for ResponseCode
     */
    constructor(code: Int, message: String) : this(code, message, null)

}