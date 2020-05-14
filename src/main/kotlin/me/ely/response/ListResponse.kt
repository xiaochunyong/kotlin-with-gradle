package me.ely.response

@Deprecated("see ItemResponse")
data class ListResponse<T> (
    val code: Int,
    val message: String,
    val data: List<T>?
) {
    constructor(data: List<T>?) : this(0, "success", data)

    /**
     * for ResponseCode
     */
    constructor(code: Int, message: String) : this(code, message, null)
}