package com.manta.injector

import com.manta.injector.context.InstanceContext
import com.manta.injector.qualifier.Qualifier
import kotlin.reflect.KClass

typealias ScopeID = String

data class Scope(
    val scopeQualifier: Qualifier,
    val id: ScopeID,
    val isRoot: Boolean = false,
    val _injector: Injector
) {

    var _source: Any? = null

    private var _closed: Boolean = false
    val closed: Boolean
        get() = _closed

    private val linkedScopes: ArrayList<Scope> = arrayListOf()

    inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parametersDefinition: ParametersDefinition? = null
    ): T {
        return get(T::class, qualifier, parametersDefinition)
    }

    fun <T> get(
        clazz: KClass<*>,
        qualifier: Qualifier? = null,
        parameters: ParametersDefinition? = null
    ): T {
        return resolveInstance(qualifier, clazz, parameters)
    }

    private fun <T> resolveInstance(
        qualifier: Qualifier?,
        clazz: KClass<*>,
        parameterDef: ParametersDefinition?
    ): T {
        val parameters = parameterDef?.invoke()
        val instanceContext = InstanceContext(_injector, this, parameters)
        return resolveValue<T>(qualifier, clazz, instanceContext, parameterDef)
    }

    private fun <T> resolveValue(
        qualifier: Qualifier?,
        clazz: KClass<*>,
        instanceContext: InstanceContext,
        parameterDef: ParametersDefinition?
    ): T = _injector.instanceRegistry.resolveInstance(qualifier, clazz, this.scopeQualifier, instanceContext) ?: run {
        _source?.let {
            if (clazz.isInstance(it)) {
                _source as? T
            } else null
        }
    } ?: throw Throwable("can't find!")

    fun linkTo(vararg scopes: Scope) {
        if (!isRoot) {
            linkedScopes.addAll(scopes)
        } else {
            error("Can't add scope link to a root scope")
        }
    }

    fun close() = InjectorPlatformTools.synchronized(this) {
        _closed = true
        _source = null
        _injector.scopeRegistry.deleteScope(this)
    }


}