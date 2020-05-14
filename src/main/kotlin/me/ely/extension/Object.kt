package me.ely.extension

import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import java.beans.Introspector
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-02
 */

val logger = LoggerFactory.getLogger("ObjectExtension")

/**
 * 创建对象
 */
@Throws(Throwable::class)
inline fun <reified T: Any> new(source: Any? = null): T {
    val obj = T::class.java.getDeclaredConstructor().newInstance()
    if (source == null) {
        return obj
    }
    // T::class.memberProperties.forEach {
    //     println("${it.name} isVar=${it is KMutableProperty<*>} isConst=${it.isConst}, isFinal=${it.isFinal}, isLateinit=${it.isLateinit}")
    // }
    // 如果 任意一个属性不是var修饰, 则使用自己实现的cp方法进行merge
    if (T::class.memberProperties.any { it !is KMutableProperty<*> }) {
        try {
            return obj.cp(source)
        } catch (e: Exception) {
            logger.error("尝试使用var定义", e)
            throw e
        }
    }

    // 如果target的属性是使用var定义, 则使用BeanUtils
    return obj.merge(source)
}

fun <T> T.merge(source: Any): T {
    BeanUtils.copyProperties(source, this as Any)
    return this
}


/**
 * 实现BeanUtils.copy
 */
@Throws(Throwable::class)
inline infix fun <reified T : Any, reified S : Any> T.cp(source: S): T {
    val beanInfo = Introspector.getBeanInfo(source.javaClass)
    val nameToPropertyOfSource = beanInfo.propertyDescriptors.associateBy { it.name }
    val nameToPropertyOfTarget = T::class.memberProperties.associateBy { it.name }

    // val fields = T::class.java.declaredFields
    // val thisBeanInfo = Introspector.getBeanInfo(T::class.java)
    // thisBeanInfo.propertyDescriptors.forEach {
    //     if (it.name != "class") {
    //         val value = nameToPropertyOfSource[it.name]?.readMethod?.invoke(source) ?: it.readMethod.invoke(it)
    //         it.writeMethod.invoke(this, value)
    //     }
    // }
    // println(this)
    // fields.forEach {
    //     it.isAccessible = true
    //     val name = it.name
    //     val readMethodOfSource = nameToPropertyOfSource[name]?.readMethod
    //     val value = readMethodOfSource?.invoke(source) ?: it.get(this)
    //     it.set(this, value)
    // }
    // val methods = T::class.java.methods
    // methods.forEach {
    //     if (it.name.startsWith("set")) {
    //         var name = it.name.substring(3, 4) + if (it.name.length > 4) it.name.substring(4) else ""
    //         val readMethodOfSource = nameToPropertyOfSource[name]?.readMethod
    //         val value = readMethodOfSource?.invoke(source) ?: it.invoke(this)
    //         it.set(this, value)
    //     }
    // }
    // println(this)

    // 根据目标类的主构造函数创建对象, 不在主构造函数中的var属性在下一步处理, 不在主构造函数中的val属性会略过.
    val primaryConstructor = T::class.primaryConstructor!!
    val primaryConstructorParams = mutableListOf<String>()
    val args = primaryConstructor.parameters.associate { parameter ->
        val name = parameter.name!!
        primaryConstructorParams.add(name)
        val readMethodOfSource = nameToPropertyOfSource[name]?.readMethod
        val propertyOfTarget = nameToPropertyOfTarget[name]
        parameter to (readMethodOfSource?.invoke(source) ?: propertyOfTarget?.get(this))
    }
    val obj = primaryConstructor.callBy(args)

    // 为不在主构造函数中的字段赋值(比如继承的字段)
    nameToPropertyOfTarget.forEach { (name, propertyOfTarget) ->
        // 只处理, 没有在构造函数中出现, 并且是var修饰的属性. 没有在构造中出现的val属性无法处理
        if (!primaryConstructorParams.contains(name) && propertyOfTarget is KMutableProperty1) {
            val mutableProperty = propertyOfTarget as KMutableProperty1<T, Any?>
            val readMethodSource = nameToPropertyOfSource[name]?.readMethod
            if (readMethodSource != null) {
                mutableProperty.set(obj, readMethodSource.invoke(source))
            } else {
                try {
                    mutableProperty.set(obj, propertyOfTarget.get(this))
                } catch (e: Exception) {
                    // lateinit 属性如果没有值, 在这里会报错, 目前还没有找到判断是否有值的方式
                    // 压制异常 e.printStackTrace()
                }
            }
        }
    }
    return obj
}

/**
 * 打印
 */
fun <T> T?.println() = println(this)

// public inline val <T : Any> T.javaClazz: Class<T>
//     get() = (this as java.lang.Object).getClass() as Class<T>