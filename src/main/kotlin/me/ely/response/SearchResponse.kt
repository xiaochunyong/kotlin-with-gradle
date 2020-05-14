package me.ely.response

data class SearchResponse<T> (
    val code: Int,
    val message: String,
    val data: List<T>?,
    val pageIndex: Int,
    val pageSize: Int,
    val totalCount: Long
) {

    constructor(data: List<T>?, pageIndex: Int, pageSize: Int, totalCount: Long) : this(0, "success", data, pageIndex, pageSize, totalCount)

    /**
     * for ResponseCode
     */
    constructor(code: Int, message: String) : this(code, message, null, 0, 20, 0)

}