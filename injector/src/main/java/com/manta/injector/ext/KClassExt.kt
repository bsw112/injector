package com.manta.injector

import kotlin.reflect.KClass

private val classNames: MutableMap<KClass<*>, String> = InjectorPlatformTools.safeHashMap()

fun KClass<*>.getFullName(): String {
    return classNames[this] ?: saveCache()
}

fun KClass<*>.saveCache(): String {
    val name = InjectorPlatformTools.getClassName(this)
    classNames[this] = name
    return name
}