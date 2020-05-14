package me.ely.response

enum class ResponseCode(val value: Int, val label: String) {
    // 系统
    SUCCESS(0, "Success"),
    ERROR(1, "Error"),
    REDIRECCT(302, "ReDirect"),
    BAD_REQUEST(400, "Bad Request"),
    UnAuthorized(403, "UnAuthorized"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    TIME_OUT(502, "Time Out"),

    // 权限
    INVALID_APPID_OR_SECRET(10001, "invalid appid or secret!"),
    TIMESTAMP_INVALID(11001, "timestamp is empty or invalid"),
    ACCESS_TOKEN_INVALID(11002, "access token is empty or invalid"),
    NONCE_INVALID(11003, "nonce is empty or invalid"),
    SIGNATURE_ERROR(11004, "signature invalid"),

    // 业务


    ;

    inline operator fun <reified T> invoke(message: String = this.label): T {
        return T::class.java.getConstructor(Int::class.java, String::class.java).newInstance(this.value, message)
    }

    companion object {

        private val map = mutableMapOf<Int, ResponseCode>()

        init {
            values().forEach { map[it.value] = it }
        }

        fun parse(value: Int): ResponseCode? {
            return map[value]
        }
    }

}