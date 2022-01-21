package com.manta.injector

import com.manta.injector.qualifier.Qualifier
import kotlin.reflect.KClass

typealias IndexKey = String
typealias Definition<T> = Scope.(ParametersHolder) -> T

data class BeanDefinition<T>(
    val scopeQualifier : Qualifier,
    val primaryType : KClass<*>,
    val qualifier : Qualifier? =null,
    val definition: Definition<T>,
    val kind : Kind,
    var secondaryType: List<KClass<*>> = listOf()
)

enum class Kind {
    Singleton, Factory, Scoped
}

fun indexKey(clazz: KClass<*>, typeQualifier: Qualifier?, scopeQualifier: Qualifier): String {
    val tq = typeQualifier?.value ?: ""
    return "${clazz.getFullName()}:$tq:$scopeQualifier"
}

inline fun <reified  T> createDefinition(
    kind: Kind = Kind.Singleton,
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
    secondaryTypes : List<KClass<*>> = emptyList(),
    scopeQualifier: Qualifier
) : BeanDefinition<T> {
    return BeanDefinition(
        scopeQualifier,
        T::class,
        qualifier,
        definition,
        kind,
        secondaryTypes
    )
}