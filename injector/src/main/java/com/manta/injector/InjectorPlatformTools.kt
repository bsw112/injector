package com.manta.injector

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

object InjectorPlatformTools {
    fun getClassName(kClass: KClass<*>): String = kClass.java.name
    fun <K, V> safeHashMap(): MutableMap<K, V> = ConcurrentHashMap<K, V>()
    fun <R> synchronized(lock: Any, block: () -> R) = kotlin.synchronized(lock, block)
}