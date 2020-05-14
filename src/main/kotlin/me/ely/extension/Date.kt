package me.ely.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-01
 */

fun Date.toString(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}