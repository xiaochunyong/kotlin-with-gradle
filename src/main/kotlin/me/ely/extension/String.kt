package me.ely.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-02
 */

/**
 * commons-lang3 StringUtils.substringBetween
 */
fun String.substringBetween(open: String, close: String): String {
    val start = this.indexOf(open)
    if (start != -1) {
        val end = this.indexOf(close, start + open.length)
        if (end != -1) {
            return this.substring(start + open.length, end)
        }
    }
    return ""
}

fun String.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date {
    val sdf = SimpleDateFormat(pattern)
    return sdf.parse(this)
}