package com.manta.injector

import kotlin.reflect.KClass

typealias ParametersDefinition = () -> ParametersHolder

class ParametersHolder(
    val _values: MutableList<Any?> = mutableListOf()
) {
    val values : List<Any?> get() = _values

    open fun <T> elementAt(i: Int, clazz: KClass<*>): T =
        if (_values.size > i) _values[i] as T else throw Throwable(
            "Can't get injected parameter #$i from $this for type '${clazz.getFullName()}'")


    inline operator fun <reified T> component1(): T = elementAt(0, T::class)

}

fun emptyParametersHolder() = ParametersHolder()