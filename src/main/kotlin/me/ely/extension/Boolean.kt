package me.ely.extension

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019/9/6
 */
inline fun <reified T> Boolean.then(trueValue: T, falseValue: T): T {
    if (this) {
        return trueValue
    }
    return falseValue
}


/**
 * 三元表达式 (ternary)
 */
fun <T> ter(condition: Boolean, trueReturn: T, falseReturn: T): T {
    return if (condition) {
        trueReturn
    } else {
        falseReturn
    }
}


infix fun Boolean.then(trueValue: Any) = TernaryExpression(this, trueValue)

class TernaryExpression(val bool: Boolean, val trueValue: Any?)

infix fun <T> TernaryExpression.otherwise(falseValue: Any?): T? {
    if (bool) return trueValue as T

    return falseValue as T
}
