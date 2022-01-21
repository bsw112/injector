package com.manta.injector

import com.manta.injector.InjectorPlatformTools.safeHashMap
import com.manta.injector.instance.InstanceFactory
import com.manta.injector.instance.ScopedInstanceFactory
import com.manta.injector.instance.SingleInstanceFactory
import kotlin.reflect.KClass

// 인스턴스 생성, 찾기 담당
class InstanceRegistry(val _injector: Injector) {


    private val _instances = safeHashMap<IndexKey, InstanceFactory<*>>()
    val instances: Map<IndexKey, InstanceFactory<*>>
        get() = _instances

    private val eagerInstance = hashSetOf<SingleInstanceFactory<*>>()

    fun loadModules(modules: List<Module>){
        modules.forEach { module ->
            loadModule(module)
            eagerInstance.addAll(module.eagerInstance)
        }
    }

    fun loadModule(module : Module){
        module.mappings.forEach { (mapping, factory) ->
            saveMapping(mapping, factory)
        }
    }

    fun saveMapping(mapping : IndexKey, factory : InstanceFactory<*>){
        _instances[mapping] = factory
    }

    fun createAllEagerInstances(){
        createEagerInstance(eagerInstance)
        eagerInstance.clear()
    }

    private fun createEagerInstance(eagerInstances : HashSet<SingleInstanceFactory<*>>){
        if(eagerInstance.isNotEmpty()){
           val defaultContext = InstanceContext(_injector, _injector.scopeRegistry.rootScope)
            eagerInstances.forEach{ factory ->
                factory.get(defaultContext)
            }
        }
    }

    internal fun <T> resolveInstance(
        qualifier: Qualifier?,
        clazz: KClass<*>,
        scopeQualifier: Qualifier,
        instanceContext: InstanceContext
    ): T? {
        return resolveDefinition(clazz, qualifier, scopeQualifier)?.get(instanceContext) as? T
    }

    internal fun resolveDefinition(
        clazz: KClass<*>,
        qualifier: Qualifier?,
        scopeQualifier: Qualifier
    ): InstanceFactory<*>? {
        val indexKey = indexKey(clazz, qualifier, scopeQualifier)
        return _instances[indexKey]
    }

    internal fun dropScopeInstances(scope: Scope) {
        _instances.values.filterIsInstance<ScopedInstanceFactory<*>>().forEach { factory -> factory.drop(scope) }
    }

}


